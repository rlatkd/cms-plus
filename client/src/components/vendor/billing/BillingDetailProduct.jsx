import { getAllProductList } from '@/apis/product';
import InputWeb from '@/components/common/inputs/InputWeb';
import { ProductSelectField2 } from '@/components/common/selects/ProductSelectField';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import BillingDetailProductTable from './BillingDetailProductTable';

const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'productName', label: '상품', width: 'w-2/12' },
  { key: 'productPrice', label: '금액', width: 'w-2/12' },
  { key: 'productQuantity', label: '수량', width: 'w-2/12' },
  { key: 'totalPrice', label: '총 금액', width: 'w-2/12' },
  { key: 'removeProduct', label: '', width: 'w-1/12' },
];

const BillingDetailProduct = ({ billingData, editable, onChange: onBillingProductsChange }) => {
  const [prodcuts, setProducts] = useState([]);

  let billingProducts = billingData.billingProducts;
  const billingId = useParams();

  // select field option에는
  // 청구상품 형태로 상품들이 저장된다.
  // 상품 -> 청구상품으로 변환 후 option에 저장

  // 상품 데이터 -> select field option 변환
  const transformProductToOption = products => {
    return products.map(item => {
      return {
        value: {
          productId: item.productId,
          productName: item.productName,
          productPrice: item.productPrice,
          quantity: 1,
        },
        label: `${item.productName}(${item.productPrice.toLocaleString()}원)`,
      };
    });
  };

  // 청구상품 데이터 -> select field option 변환
  const transformBillingProductToOption = billingProducts => {
    return billingProducts.map(item => {
      return {
        value: {
          productId: item.productId,
          productName: item.name,
          productPrice: item.price,
          quantity: item.quantity,
        },
        label: `${item.name} (${item.price.toLocaleString()}원)`,
      };
    });
  };

  // 전체 상품 목록
  const axiosAllProductList = async () => {
    try {
      const res = await getAllProductList();
      console.log('!----청구 상세 - 상품리스트 조회 성공----!'); // 삭제예정
      console.log(res.data);
      setProducts(res.data);
    } catch (err) {
      console.error('axiosContractDetailBillingList => ', err);
    }
  };

  const calcBillingPrice = mBillingProducts => {
    return mBillingProducts.reduce((sum, bp) => {
      return sum + bp.price * bp.quantity;
    }, 0);
  };

  const handleBillingProductListChange = items => {
    console.log('handlechange', items);
    const newBillingProducts = items.map(item => {
      return {
        billingId: billingId.id,
        productId: item.productId,
        name: item.productName,
        price: item.productPrice,
        quantity: item.quantity,
      };
    });
    onBillingProductsChange(newBillingProducts);
  };

  const handleSelectedProductListChange = newOptions => {
    handleBillingProductListChange(newOptions.map(option => option.value));
  };

  const handleInputChange = (idx, column, to) => {
    const newBillingProducts = [...billingProducts];
    if (column === 'price') {
      newBillingProducts[idx].price = to;
    } else if (column === 'quantity') {
      newBillingProducts[idx].quantity = to;
    }
    onBillingProductsChange(newBillingProducts);
  };

  const handleRemove = idx => {
    if (billingProducts.length === 1) {
      alert('최소 1개 이상의 상품이 필요합니다!');
      return;
    }
    onBillingProductsChange([
      ...billingProducts.slice(0, idx),
      ...billingProducts.slice(idx + 1, billingProducts.length),
    ]);
  };

  useEffect(() => {
    axiosAllProductList();
  }, []);

  return (
    <>
      <div className='flex justify-between mb-5'>
        <div className='w-2/6 flex-row'>
          <label className={`block text-text_black text-15 font-700 mb-2 ml-2 `}>상품 추가</label>
          <ProductSelectField2
            label={
              prodcuts[0] &&
              `${prodcuts[0].productName} (${prodcuts[0].productPrice.toLocaleString()}원)`
            }
            options={transformProductToOption(prodcuts)}
            selectedOptions={transformBillingProductToOption(billingProducts)}
            onChange={handleSelectedProductListChange}
            disabled={!editable}
          />
        </div>

        <div className='flex items-center bg-ipt_disa border border-ipt_border rounded-lg px-3'>
            <span className='mr-2 font-bold text-lg'>합계:</span>
            <InputWeb
              id='billingTotal'
              label=''
              value={`${calcBillingPrice(billingProducts).toLocaleString()}원`}
              type='text'
              disabled={true}
              classInput='text-right font-bold text-lg border-none'
            />
        </div>
      </div>

      <div className='flex flex-col h-full justify-between'>
        <BillingDetailProductTable
          cols={cols}
          data={billingProducts}
          editable={editable}
          handleInputChange={handleInputChange}
          handleRemove={handleRemove}
        />
      </div>
    </>
  );
};

export default BillingDetailProduct;
