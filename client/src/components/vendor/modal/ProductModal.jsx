import { useEffect, useState } from 'react';
import BaseModal from '@/components/common/BaseModal';
import { createProduct } from '@/apis/product';
import InputWeb from '@/components/common/inputs/InputWeb';

const ProductModal = ({
  isShowModal,
  setIsShowModal,
  modalTitle,
  productDetailData,
  refreshProductList,
}) => {
  const [productName, setProductName] = useState('');
  const [productPrice, setProductPrice] = useState('');
  const [productMemo, setProductMemo] = useState('');
  const [contractNumber, setContractNumber] = useState('');

  const handleCreateProduct = async () => {
    const productData = {
      productName,
      productPrice,
      productMemo,
    };

    try {
      await createProduct(productData);
      // alert('상품을 등록했습니다.');
      setIsShowModal(false);
      refreshProductList(); // 등록 후 상품 목록 리렌더링
    } catch (err) {
      alert('상품 등록에 실패했습니다.');
      console.error('axiosProductDetail => ', err.response.data);
    }
  };

  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // 모달이 열릴 때마다 초기 상태 설정
  useEffect(() => {
    if (modalTitle === '상품 등록') {
      setProductName(''); // 빈값
      setProductPrice('');
      setProductMemo('');
    } else if (modalTitle === '상품 상세 정보' && productDetailData) {
      setProductName(productDetailData.productName); // 기존 데이터값
      setProductPrice(productDetailData.productPrice);
      setProductMemo(productDetailData.productMemo);
    }
  }, [isShowModal, modalTitle, productDetailData]);

  const handleDelete = () => {
    const isConfirmed = window.confirm('정말 삭제하시겠습니까?');
    if (isConfirmed) {
      setIsShowModal(false);
    }
  };

  // 모달이 열리면서 동시에 api 요청하는데, 모달이 열릴때까지 api 응답이 안 도착하는 경우가 있음
  // api 응답 와야 모달 열리게 보장
  if (modalTitle === '상품 상세 정보' && !productDetailData) {
    return <div />;
  }

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      height='h-640'
      width='w-640'>
      <div className='flex flex-col justify-between gap-1.5'>
        <div className='mb-4 flex gap-4'>
          <div className='w-1/2'>
            <label className='block mb-2 font-semibold text-text_grey'>상품명</label>
            <InputWeb
              id='productName'
              type='text'
              placeholder='상품명을 입력해주세요.'
              value={productName}
              readOnly={modalTitle === '상품 상세 정보'}
              tabIndex={modalTitle === '상품 상세 정보' ? '-1' : '0'}
              onChange={e => setProductName(e.target.value)}
              classInput={
                modalTitle === '상품 상세 정보'
                  ? 'mt-1 w-full rounded-md border border-slate-300 px-3 py-2 text-sm placeholder-slate-400 shadow-sm placeholder:text-sm bg-ipt_disa cursor-default'
                  : 'mt-1 w-full rounded-md border border-slate-300 bg-white px-3 py-2 text-sm placeholder-slate-400 shadow-sm placeholder:text-sm focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm'
              }
              onMouseDown={modalTitle === '상품 상세 정보' ? e => e.preventDefault() : undefined}
            />
          </div>
          <div className='w-1/2' />
        </div>
        <div className='mb-4 flex gap-4'>
          <div className='w-1/2'>
            <label className='block mb-2 font-semibold text-text_grey'>금액(원)</label>
            <InputWeb
              id='productPrice'
              type='text'
              placeholder='금액을 입력해주세요.'
              value={productPrice}
              onChange={e => setProductPrice(e.target.value)}
              classInput='mt-1 w-full rounded-md border border-slate-300 bg-white px-3 py-2 text-sm placeholder-slate-400 shadow-sm placeholder:text-sm focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm'
            />
          </div>
          <div className='w-1/2'>
            <label className='block mb-2 font-semibold text-text_grey'>계약수(건)</label>
            <InputWeb
              id='contractNumber'
              type='text'
              placeholder={modalTitle === '상품 상세 정보' ? productDetailData.contractNumber : ''}
              value={contractNumber}
              readOnly
              tabIndex='-1'
              classInput='mt-1 w-full rounded-md border border-slate-300 px-3 py-2 text-sm placeholder-slate-400 shadow-sm placeholder:text-sm bg-ipt_disa cursor-default'
              onMouseDown={e => e.preventDefault()}
            />
          </div>
        </div>
        <div className='mb-4'>
          <label className='block mb-2 font-semibold text-text_grey'>비고</label>
          <textarea
            placeholder={
              modalTitle === '상품 상세 정보'
                ? productDetailData.productMemo
                : '메모를 남겨 주세요.'
            }
            value={productMemo}
            onChange={e => setProductMemo(e.target.value)}
            className='mt-1 w-full h-60 rounded-md border border-slate-300 bg-white px-3 py-2 text-sm placeholder-slate-400 shadow-sm textarea:text-sm focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm resize-none'
          />
        </div>
        <div className='flex justify-end gap-2 h-10'>
          {modalTitle === '상품 등록' ? (
            <>
              <button
                className='bg-white text-mint border border-mint px-7 py-2 rounded-md hover:bg-mint_hover hover:text-white h-10 w-50'
                onClick={() => setIsShowModal(false)}>
                <span>취소</span>
              </button>
              <button
                className='bg-mint text-white px-10 py-2 rounded-md hover:bg-mint_hover flex flex-row items-center w-36 justify-center'
                onClick={handleCreateProduct}>
                <span>등록</span>
              </button>
            </>
          ) : (
            <>
              <button
                className='bg-white text-mint border border-mint px-7 py-2 rounded-md hover:bg-mint_hover hover:text-white h-10 w-50'
                onClick={() => setIsShowModal(false)}>
                <span>취소</span>
              </button>
              <button
                className='bg-mint text-white px-7 py-2 rounded-md hover:bg-mint_hover h-10 w-28'
                onClick={handleDelete}>
                <span>삭제</span>
              </button>
              <button className='bg-mint text-white px-10 py-2 rounded-md hover:bg-mint_hover flex flex-row items-center w-36 justify-center'>
                <span>수정</span>
              </button>
            </>
          )}
        </div>
      </div>
    </BaseModal>
  );
};

export default ProductModal;
