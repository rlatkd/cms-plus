import ProductModal from '@/components/vendor/modal/ProductModal';
import { useState } from 'react';

const ProductListPage = () => {

  const [isShowModal, setIsShowModal] = useState(false);
  const [modalTitle, setModalTitle] = useState(''); //모달 제목 상태

  // 상품 등록 모달 열기용 이벤트핸들러
  const openRegisterModalHandle = () => {
    setModalTitle('상품 등록');
    setIsShowModal(true);
  };

  // 상품 상세조회 모달 열기용 이벤트핸들러
  const openDetailModalHandle = () => {
    setModalTitle('상품 상세 정보');
    setIsShowModal(true);
  };

  return (
    <div className='primary-dashboard h-full w-full'>
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white mr-10'
        onClick={openRegisterModalHandle}>
        임시 상품 등록 모달
      </button>
      
      {/* 이건 목록에서 상품 하나 클릭하면 들어가도록 */}
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white'
        onClick={openDetailModalHandle}>
        임시 상품 상세 모달
      </button>
      <ProductModal
        isShowModal={isShowModal}
        setIsShowModal={setIsShowModal}
        modalTitle={modalTitle}
      />
    </div>
  );

};

export default ProductListPage;