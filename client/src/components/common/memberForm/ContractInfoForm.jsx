import { useContext, useEffect, useState } from 'react';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import ProductSelectFieldcopy from '@/components/common/selects/ProductSelectFieldcopy';
import Remove from '@/assets/Remove';
import { getAllProductList } from '@/apis/product';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import useAlert from '@/hooks/useAlert';

// formType : CREATE, UPDATE
const ContractInfoForm = ({ formType }) => {
  const { contractInfo, setContractInfoItem, setContractProducts } = useMemberContractStore(); // 상품 정보 zustand
  const [productList, setProductList] = useState([]); // 상품 목록
  const onAlert = useAlert();

  // <-----Options생성----->
  const createOptions = (itemList, valueKey) => {
    return itemList.map(item => ({
      value: item[valueKey] + ' (' + String(item.price.toLocaleString()) + '원)',
      label: item[valueKey],
      name: item[valueKey],
      price: item.price,
      productId: item.productId,
    }));
  };

  const options = createOptions(productList, 'name');

  // <-----상품 추가, 제거----->
  const handleProductChange = newSelectedOptions => {
    if (newSelectedOptions.length > 10) {
      onAlert({ msg: '10개 이상 등록할 수 없습니다!', type: 'error', title: '등록 제한' });
      return;
    }
    if (newSelectedOptions.length < 1) {
      onAlert({ msg: '반드시 하나의 상품이 필요합니다!', type: 'error', title: '상품 필수' });
      return;
    }
    setContractProducts(newSelectedOptions);
  };

  // <-----상품 제거----->
  const handleRemoveProduct = product => {
    const contractProducts = contractInfo.contractProducts;
    if (contractProducts.length == 1) {
      onAlert({ msg: '반드시 하나의 상품이 필요합니다!', type: 'error', title: '상품 필수' });
      return;
    }
    const newSelectedProducts = contractProducts.filter(p => p.productId !== product.productId);
    setContractProducts(newSelectedProducts);
  };

  // <-----상품정보, 계약명 수정----->
  const handleChangeValue = (e, index = null) => {
    const { id, value } = e.target;

    if (id === 'contractName') {
      setContractInfoItem({ [id]: value });
    } else if (index !== null) {
      const numericValue = value.replace(/\D/g, '');
      const updatedSelectedProducts = contractInfo.contractProducts.map((product, idx) =>
        idx === index
          ? { ...product, [id]: numericValue === '' ? 0 : Number(numericValue) }
          : product
      );
      setContractProducts(updatedSelectedProducts);
    }
  };

  // TODO
  // <----- 정규표현식 예외처리 ----->

  // <----- 전체 상품 목록 조회 ----->
  const axiosProductList = async () => {
    try {
      const res = await getAllProductList();
      console.log('!----전체 상품 목록 조회----!'); // 삭제예정
      setProductList(res.data);
      if (formType === 'CREATE') {
        addDefaultProduct(res.data[0]);
      }
    } catch (err) {
      console.error('axiosProductList => ', err.response);
    }
  };

  // <----- 상품 총 합계 계산 ----->
  const calcContractPrice = mContractProducts => {
    return mContractProducts.reduce((sum, cp) => {
      return sum + cp.price * cp.quantity;
    }, 0);
  };

  // <----- 기본 상품 등록 ----->
  const addDefaultProduct = defaultProduct => {
    if (contractInfo.contractProducts.length === 0) {
      const product = {
        value: defaultProduct.name,
        label: defaultProduct.name,
        name: defaultProduct.name,
        price: defaultProduct.price,
        productId: defaultProduct.productId,
        quantity: 1,
      };
      setContractProducts([product]);
    }
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
          classContainer='w-1/4 mr-8'
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
        <div className='flex items-center text-lg font-800 text-text_black ml-auto bg-background border border-ipt_border rounded-lg px-5 py-3 mr-2'>
          <p className='mr-2'>합계:</p>
          <p className='text-right  '>{`${calcContractPrice(contractInfo.contractProducts).toLocaleString()}원`}</p>
        </div>
      </div>

      <div className='relative flex flex-col h-full text-text_black overflow-y-scroll scrollbar-custom border-b border-ipt_border ml-2'>
        <div className='sticky top-0 flex justify-between bg-white text-text_black font-800 border-b border-ipt_border pb-3'>
          <span className='w-1/5 text-center'>상품명</span>
          <span className='w-2/12 text-center'>상품금액</span>
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
              className='w-2/12 text-center border border-ipt_border py-2 rounded-lg '
              type='text'
              value={product.price.toLocaleString()}
              onChange={e => handleChangeValue(e, idx)}
              maxLength={7}
            />
            <input
              id='quantity'
              placeholder='수량'
              className='w-1/12 text-center border border-ipt_border py-2 rounded-lg'
              type='text'
              value={product.quantity.toLocaleString()}
              onChange={e => handleChangeValue(e, idx)}
              maxLength={2}
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
