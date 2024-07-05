import ProductModal from '@/components/vendor/modal/ProductModal';
import { useState } from 'react';

const ProductListPage = () => {
  const [isShowModal, setIsShowModal] = useState(false);
  return (
    <div className='h-full w-full rounded-xl p-6 shadow-dash-board'>
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
