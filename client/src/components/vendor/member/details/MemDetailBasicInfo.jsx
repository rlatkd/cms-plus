import { useNavigate, useParams } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import remove from '@/assets/remove.svg';
import BasicInfoForm from '@/components/common/memberForm/BasicInfoForm';

const MemDetailBasicInfo = () => {
  const navigate = useNavigate();

  const memberId = useParams();

  // id값은 추후 변경
  const handleGoDetail = () => {
    navigate(`/vendor/members/update/${memberId.id}`);
  };

  return (
    <div className='flex flex-col sub-dashboard pb-6  mb-5 h-640 w-full'>
      <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
        <p className='text-text_black text-xl font-800'>기본정보</p>

        <div className='flex'>
          <button
            className='flex justify-between items-center px-4 py-2 text-text_grey
            font-700 rounded-md border border-text_grey cursor-pointer'>
            <img src={remove} alt='remove' className='mr-2 ' />
            <p>회원삭제</p>
          </button>

          <button
            className='flex justify-between items-center px-4 py-2 ml-4 text-mint
            font-700 rounded-md border border-mint cursor-pointer'
            onClick={handleGoDetail}>
            <img src={edit} alt='edit' className='mr-2 ' />
            <p>회원수정</p>
          </button>
        </div>
      </div>
      <BasicInfoForm formType='DETAIL' disabled={true} />
    </div>
  );
};

export default MemDetailBasicInfo;
