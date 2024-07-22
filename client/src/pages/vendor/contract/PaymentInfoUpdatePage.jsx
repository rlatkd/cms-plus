import PaymentInfoForm from '@/components/common/memberForm/PaymentInfoForm';
import { useNavigate } from 'react-router-dom';

const PaymentInfoUpdatePage = () => {
  const navigate = useNavigate();

  return (
    <>
      <div className='up-dashboard relative mb-4 w-full desktop:h-[18%]'>progressivee</div>
      <div className='primary-dashboard flex flex-col relative h-[1000px] large_desktop:h-[80%] '>
        <div className='flex items-center h-[50px] px-2 pb-[10px] '>
          <p className='text-text_black text-xl font-800'>기본정보</p>
        </div>
        <PaymentInfoForm formType={'CREATE'} />
        <div className='absolute bottom-0 left-0 flex h-[65px] w-full justify-end px-7 pb-5 font-800 text-lg '>
          <button
            className=' px-10 py-2 border border-mint rounded-lg text-mint'
            onClick={() => navigate(-1)}>
            취소
          </button>
          <button className=' px-10 py-2 bg-mint rounded-lg text-white transition-all duration-200 hover:bg-mint_hover ml-3'>
            저장
          </button>
        </div>
      </div>
    </>
  );
};

export default PaymentInfoUpdatePage;
