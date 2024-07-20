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

  // 기본 정보 수정 API
  const axiosMemberUpdate = async () => {
    try {
      console.log(memberId.id, basicInfo);
      const res = await updateMember(memberId.id, basicInfo);
      console.log('!----회원 수정 성공----!'); // 삭제예정
      await navigate(`/vendor/members/detail/${memberId.id}`);
      onAlertClick();
    } catch (err) {
      console.error('axiosMemberUpdate => ', err.response.data);
    }
  };

  // 기본정보 수정 성공 Alert창
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async () => {
    const result = await alertComp('회원정보가 수정되었습니다!');
  };

  return (
    <div className='flex h-full w-full flex-col '>
      <div className='up-dashboard relative mb-5 h-1/5 w-full'>progressivee</div>
      <div className='primary-dashboard flex flex-col relative h-4/5 '>
        <div className='border-b border-ipt_border px-2 pt-1 pb-3'>
          <p className='text-text_black text-xl font-800'>기본정보</p>
        </div>
        <BasicInfoForm formType='UPDATE' />
        <button
          className='absolute bottom-6 right-40 px-9 py-2 border border-mint rounded-lg font-800 text-mint '
          onClick={() => navigate(-1)}>
          취소
        </button>
        <button
          className='absolute bottom-6 right-11 px-9 py-2 bg-mint rounded-lg font-800 text-white transition-all duration-200 hover:bg-mint_hover'
          onClick={axiosMemberUpdate}>
          저장
        </button>
      </div>
    </div>
  );
};

export default MemberInfoUpdatePage;
