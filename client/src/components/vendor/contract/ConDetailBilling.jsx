import { useNavigate } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import InputWeb from '@/components/common/inputs/InputWeb';

const ConDetailBilling = ({ contractData, children }) => {
  const navigate = useNavigate();

  const handleButtonClick = () => {
    navigate('/vendor/contracts/billings/update/1');
  };

  return (
    <div className='flex flex-col sub-dashboard pb-6  mb-5 h-640 w-full'>
      <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
        <p className='text-text_black text-xl font-800'>청구정보</p>
        <button
            className='flex justify-between items-center px-4 py-2 ml-4 text-mint
            font-700 rounded-md border border-mint cursor-pointer'>
            <img src={edit} alt='edit' className='mr-2 ' />
            <p>청구수정</p>
          </button>
      </div>
      <div className="grid grid-cols-5 gap-4 my-8 mx-4">
        <InputWeb
          id='autobilling'
          label='청구 자동 생성'
          placeholder='수동'
          type='text'
          disabled={true}
        />
        <InputWeb
          id='autosend'
          label='청구서 자동 발송'
          placeholder='수동'
          type='text'
          disabled={true}
        />
        <InputWeb
          id='memberPhone'
          label='납부자 업체연락처'
          placeholder='010-1234-5678'
          type='text'
          disabled={true}
        />
        <InputWeb
          id='memberEmail'
          label='납부자 이메일'
          placeholder='hyosung123@gmail.com'
          type='text'
          disabled={true}
        />
        <InputWeb
          id='sendMethod'
          label='청구서 발송 수단'
          placeholder='휴대전화'
          type='text'
          disabled={true}
        />
      </div>
      <div className='flex-row justify-between items-center px-2 pt-1 pb-3 mx-5'>
          <p className='text-text_black text-lg font-400 mb-2'>전체청구 / 합계금액</p>
          <p className='text-text_black text-xl font-800'>총 {contractData.totalBillingCount}건 / {contractData.totalBillingPrice.toLocaleString()}원</p>
      </div>  
      {children}
    </div>
  );
};

export default ConDetailBilling;