import ContractInfoForm from '@/components/common/memberForm/ContractInfoForm';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';

const ProductInfoUpdatePage = () => {
  const navigate = useNavigate();

  // 계약 정보 수정 API

  // 계약정보 수정 성공 Alert창
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async () => {
    const result = await alertComp('계약정보가 수정되었습니다!');
  };

  return (
    <div className='flex h-full w-full flex-col'>
      <div className='sub-dashboard relative mb-5 h-1/5 w-full'>
        progressivee
        {/* <img
          src='/src/assets/close.svg'
          alt='back'
          className='absolute right-6 top-6 cursor-pointer'
          onClick={() => navigate(-1)}
        /> */}
      </div>
      <div className='primary-dashboard relative h-4/5 w-full '>
        <div className='border-b border-ipt_border px-2 pt-1 pb-3'>
          <p className='text-text_black text-xl font-800'>기본정보</p>
        </div>
        <ContractInfoForm formType='UPDATE' />
        <button
          className='absolute bottom-6 right-40 px-9 py-2 border border-mint rounded-lg font-800 text-mint '
          onClick={() => navigate(-1)}>
          취소
        </button>
        <button className='absolute bottom-6 right-11 px-9 py-2 bg-mint rounded-lg font-800 text-white transition-all duration-200 hover:bg-mint_hover'>
          저장
        </button>
      </div>
    </div>
  );
};

export default ProductInfoUpdatePage;
