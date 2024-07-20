import { updateMember } from '@/apis/member';
import BasicInfoForm from '@/components/common/memberForm/BasicInfoForm';
import { useMemberStore } from '@/stores/useMemberStore';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const MemberInfoUpdatePage = () => {
  const { basicInfo } = useMemberStore();
  const navigate = useNavigate();

  const memberId = useParams();

  // <--------기본 정보 수정 API-------->
  const axiosMemberUpdate = async () => {
    try {
      await updateMember(memberId.id, basicInfo);
      console.log('!----회원 수정 성공----!'); // 삭제예정
      await navigate(`/vendor/members/detail/${memberId.id}`);
      onAlertClick();
    } catch (err) {
      console.error('axiosMemberUpdate => ', err.response.data);
    }
  };

  // <--------기본정보 수정 성공 Alert창-------->
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async () => {
    await alertComp('회원정보가 수정되었습니다!');
  };

  return (
    <>
      <div className='up-dashboard relative mb-4 w-full desktop:h-[18%]'>progressivee</div>
      <div className='primary-dashboard flex flex-col relative h-[1000px] large_desktop:h-[80%] '>
        <div className='flex items-center h-[50px] px-2 pb-[10px] border-b border-ipt_border'>
          <p className='text-text_black text-xl font-800'>기본정보</p>
        </div>
        <BasicInfoForm formType='UPDATE' />
        <div className='absolute bottom-0 left-0 flex h-[70px] w-full justify-end px-7 pb-5 font-800 text-lg '>
          <button
            className=' px-10 py-2 border border-mint rounded-xl text-mint'
            onClick={() => navigate(-1)}>
            취소
          </button>
          <button
            className=' px-10 py-2 bg-mint rounded-xl text-white transition-all duration-200 hover:bg-mint_hover ml-3'
            onClick={axiosMemberUpdate}>
            저장
          </button>
        </div>
      </div>
    </>
  );
};

export default MemberInfoUpdatePage;
