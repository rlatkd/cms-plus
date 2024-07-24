import { useNavigate, useParams } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import ConDetailPaymentType from './ConDetailPaymentType';
import ConDetailPaymentMethod from './ConDetailPaymentMethod';

const ConDetailPayment = ({ contractData }) => {
  const navigate = useNavigate();

  const { id: contractId } = useParams();

  const handleButtonClick = () => {
    navigate(`/vendor/contracts/payment/update/${contractId}`);
  };

  return (
    <div className='bg-white p-4 rounded-lg shadow w-1/2'>
      <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
        <p className='text-text_black text-xl font-800'>결제정보</p>
        <button
          className='flex justify-between items-center px-4 py-2 ml-4 text-mint
            font-700 rounded-md border border-mint cursor-pointer'
          onClick={handleButtonClick}>
          <img src={edit} alt='edit' className='mr-2 ' />
          <p>결제수정</p>
        </button>
      </div>
      <ConDetailPaymentType contractData={contractData} />
      {contractData.paymentTypeInfo.paymentType.code !== 'BUYER' && (
        <>
          <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
            <p className='text-text_black text-xl font-800'>현재 결제수단</p>
          </div>
          <ConDetailPaymentMethod contractData={contractData} />
        </>
      )}
    </div>
  );
};

export default ConDetailPayment;
