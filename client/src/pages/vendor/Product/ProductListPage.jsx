import ProductModal from '@/components/vendor/modal/ProductModal';
import { useState } from 'react';

const ProductListPage = () => {
  const [isShowModal, setIsShowModal] = useState(false);
  return (
    <div className='primary-dashboard h-full w-full'>
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white'
        onClick={() => setIsShowModal(true)}>
        임시상품등록모달
      </button>

      <ProductModal
        isShowModal={isShowModal}
        setIsShowModal={setIsShowModal}
        modalTitle={'상품등록'}
      />
    </div>
  );
};
export default ProductListPage;
