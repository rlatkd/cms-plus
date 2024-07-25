import { useEffect, useState } from 'react';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import ProductSelectFieldcopy from '@/components/common/selects/ProductSelectFieldcopy';
import Remove from '@/assets/Remove';
import { getProductListTmp } from '@/apis/product';

// formType : CREATE, UPDATE
const ContractInfoForm = ({ formType }) => {
  const { contractInfo, setContractInfoItem, setContractProducts } = useMemberContractStore(); // 상품 정보 zustand
  const [productList, setProductList] = useState([]); // 상품 목록

  // <--------Options생성-------->
  const createOptions = (itemList, valueKey) => {
    return itemList.map(item => ({
      value: item[valueKey],
      label: item[valueKey],
      name: item[valueKey],
      price: item.productPrice,
      productId: item.productId,
    }));
  };

  const options = createOptions(productList, 'productName');

  // <--------상품 추가-------->
  const handleProductChange = newSelectedOptions => {
    setContractProducts(newSelectedOptions);
  };

  // <--------상품 제거-------->
  const handleRemoveProduct = product => {
    const tmp = contractInfo.contractProducts;
    const newSelectedProducts = tmp.filter(p => p.productId !== product.productId);
    setContractProducts(newSelectedProducts);
  };

  // <--------상품정보, 계약명 수정-------->
  const handleChangeValue = (e, index = null) => {
    const { id, value } = e.target;

    if (id === 'contractName') {
      setContractInfoItem({ [id]: value });
    } else if (index !== null) {
      const updatedSelectedProducts = contractInfo.contractProducts.map((product, idx) =>
        idx === index ? { ...product, [id]: value } : product
      );
      setContractProducts(updatedSelectedProducts);
    }
  };

  // TODO
  // <------ 정규표현식 예외처리 ------>

  // TODO
  // 민석이 API로 교체
  // <------ 전체 상품 목록 조회 ------>
  const axiosProductList = async () => {
    try {
      const res = await getProductListTmp();
      console.log('!----전체 상품 목록 조회----!'); // 삭제예정
      setProductList(res.data);
    } catch (err) {
      console.error('axiosProductList => ', err.response.data);
    }
  };

  const calcContractPrice = mContractProducts => {
    return mContractProducts.reduce((sum, cp) => {
      return sum + cp.price * cp.quantity;
    }, 0);
  };

  useEffect(() => {
    axiosProductList();
  }, []);

  return (
    <div className='flex flex-col pt-5 px-2 h-[calc(100%-120px)] '>
      <div className='flex justify-between items-end mb-5'>
        <ProductSelectFieldcopy
          label='상품추가'
          placeholder='상품을 선택해주세요.'
          required={true}
          options={options}
          onChange={handleProductChange}
          selectedOptions={contractInfo.contractProducts}
          classContainer='w-1/3 mr-8'
          classButton='w-full'
        />
        <InputWeb
          id='contractName'
          label='계약정보명'
          placeholder='계약정보명( 최대 20자 )'
          type='text'
          required
          classContainer='w-1/3'
          value={contractInfo.contractName}
          onChange={handleChangeValue}
        />
        <div className='flex items-center ml-auto bg-background border border-ipt_border rounded-lg p-3 mr-2'>
          <p className='font-bold text-lg mr-2 text-text_black'>합계:</p>
          <p className='text-right font-bold text-lg text-text_black'>{`${calcContractPrice(contractInfo.contractProducts).toLocaleString()}원`}</p>
        </div>
      </div>

      <div className='relative flex flex-col h-full text-text_black overflow-y-scroll scrollbar-custom border-b border-ipt_border ml-2'>
        <div className='sticky top-0 flex justify-between bg-white text-text_black font-800 border-b border-ipt_border pb-3'>
          <span className='w-1/5 text-center'>상품명</span>
          <span className='w-1/12 text-center'>상품금액</span>
          <span className='w-1/12 text-center'>수량</span>
          <span className='w-1/5 text-center'>상품합계금액</span>
          <span className='w-1/5 text-center'>삭제</span>
        </div>
        {contractInfo.contractProducts.map((product, idx) => (
          <div
            key={idx}
            className='flex justify-between items-center py-3 border-b border-ipt_border text-sm  '>
            <p className='w-1/5 text-center'>{product.name}</p>
            <input
              id='price'
              placeholder='상품금액'
              className='w-1/12 text-center border border-ipt_border py-2 rounded-lg '
              type='text'
              value={product.price.toLocaleString()}
              onChange={e => handleChangeValue(e, idx)}
            />
            <input
              id='quantity'
              placeholder='수량'
              className='w-1/12 text-center border border-ipt_border py-2 rounded-lg'
              type='text'
              value={product.quantity}
              onChange={e => handleChangeValue(e, idx)}
            />
            <p className='w-1/5 text-center'>
              {`${(product.price * product.quantity).toLocaleString()} 원`}
            </p>
            <button className='w-1/5 flex justify-center '>
              <Remove onClick={() => handleRemoveProduct(product)} />
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ContractInfoForm;
