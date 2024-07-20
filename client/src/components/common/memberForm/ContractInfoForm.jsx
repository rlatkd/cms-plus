import { useEffect, useState } from 'react';
import InputWeb from '../inputs/InputWeb';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import ProductSelectFieldcopy from '../selects/ProductSelectFieldcopy';
import Remove from '@/assets/Remove';
import { getProductListTmp } from '@/apis/product';
import Trash from '@/assets/Trash';

// formType : CREATE, UPDATE
const ContractInfoForm = ({ formType }) => {
  const { contractInfo, setContractInfoItem, setContractProducts, resetContractInfo } =
    useMemberContractStore(); // 상품 정보 zustand
  const [productList, setProductList] = useState([]); // 상품 목록
  const [selectedProducts, setSelectedProducts] = useState([]); // 선택된 상품 목록

  // <--------Options생성-------->
  const createOptions = (itemList, valueKey) => {
    return itemList.map(item => ({
      value: item[valueKey],
      label: item[valueKey],
      price: item.productPrice,
      productId: item.productId,
    }));
  };

  const options = createOptions(productList, 'productName');

  // <--------상품 목록을 contractProducts형식으로 변환-------->
  const mapContractProducts = products => {
    return products.map(option => ({
      productId: option.productId,
      price: option.price,
      quantity: option.quantity || 1, // 기본 값으로 1 설정
    }));
  };

  // <--------상품 추가-------->
  const handleProductChange = newSelectedOptions => {
    setSelectedProducts(newSelectedOptions);
    setContractProducts(mapContractProducts(newSelectedOptions));

    console.log(newSelectedOptions);
  };

  // <--------상품 제거-------->
  const handleRemoveProduct = product => {
    const newSelectedProducts = selectedProducts.filter(p => p.productId !== product.productId);
    setSelectedProducts(newSelectedProducts);
    setContractProducts(mapContractProducts(newSelectedProducts));
  };

  // <--------상품정보, 계약명 수정-------->
  const handleChangeValue = (e, index = null) => {
    const { id, value } = e.target;
    if (id === 'contractName') {
      setContractInfoItem({ [id]: value });
    } else if (index !== null) {
      const updatedSelectedProducts = selectedProducts.map((product, idx) =>
        idx === index ? { ...product, [id]: value } : product
      );
      setSelectedProducts(updatedSelectedProducts);
      setContractProducts(mapContractProducts(updatedSelectedProducts));
    }
  };

  // <--------전체 상품 목록 조회-------->
  const axiosProductList = async () => {
    try {
      const res = await getProductListTmp();
      console.log('!----전체 상품 목록 조회----!'); // 삭제예정
      setProductList(res.data);
    } catch (err) {
      console.error('axiosProductList => ', err.response.data);
    }
  };

  // formType : CREATE일 경우 contractInfo를 reset
  useEffect(() => {
    if (formType === 'CREATE') resetContractInfo();
  }, []);

  useEffect(() => {
    axiosProductList();
  }, []);

  return (
    <div className='flex flex-col pt-5 px-2 h-[calc(100%-120px)] '>
      <div className='flex justify-between w-full mb-5 '>
        <ProductSelectFieldcopy
          label='상품추가'
          placeholder='상품을 선택해주세요.'
          required={true}
          options={options}
          onChange={handleProductChange}
          selectedOptions={selectedProducts}
          classContainer='flex-1 mr-7 w-'
          classButton='w-full'
        />
        <InputWeb
          id='contractName'
          label='계약정보명'
          placeholder='계약정보명( 최대 20자 )'
          type='text'
          required
          classContainer='flex-1'
          value={contractInfo.contractName}
          onChange={handleChangeValue}
        />
      </div>

      <div className='relative flex flex-col h-full text-text_black overflow-y-scroll scrollbar-custom border-b border-ipt_border ml-2'>
        <div className='sticky top-0 flex justify-between bg-white text-text_black font-800 border-b border-ipt_border pb-3'>
          <span className='w-1/5 text-center'>상품명</span>
          <span className='w-1/12 text-center'>상품금액</span>
          <span className='w-1/12 text-center'>수량</span>
          <span className='w-1/5 text-center'>상품합계금액</span>
          <span className='w-1/5 text-center'>삭제</span>
        </div>
        {selectedProducts.map((product, idx) => (
          <div
            key={idx}
            className='flex justify-between items-center py-3 border-b border-ipt_border text-sm'>
            <p className='w-1/5 text-center'>{product.label}</p>
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
