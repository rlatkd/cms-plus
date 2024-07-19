import { useNavigate } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import remove from '@/assets/remove.svg';

const productForm = (product, idx) => {
  return (
    <div key={idx} className='flex justify-between border border-ipt_border px-5 py-5 m-3 rounded-lg'>
      <p>{product.name}</p>
      <p>{product.price.toLocaleString()}원</p>
      <p>{product.quantity}개</p>
    </div>
  );
}

const ConDetailContract = ({ contractData }) => {
  const navigate = useNavigate();

  const handleButtonClick = () => {
    navigate('/vendor/contracts/Product/update/1');
  };

  return (
    <div className='bg-white p-4 rounded-lg shadow w-1/2'>
      <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
        <p className='text-text_black text-xl font-800'>계약정보</p>
        <button
            className='flex justify-between items-center px-4 py-2 ml-4 text-mint
            font-700 rounded-md border border-mint cursor-pointer'
            onClick={handleButtonClick}
            >
            <img src={edit} alt='edit' className='mr-2 ' />
            <p>상품수정</p>
          </button>
      </div>
      <div className='my-8'>
        <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3 mx-5'>
          <p className='text-text_black text-lg font-400'>상품수 : {contractData.contractProducts.length}개</p>
          <p className='text-text_black text-lg font-400'>총금액 : {contractData.contractPrice.toLocaleString()}원</p>
        </div>
        <div className='mt-5'>
          {contractData.contractProducts.map((product, idx) => productForm(product, idx))}
        </div>
      </div>
    </div>
  );
};

export default ConDetailContract;
