import { updateMemberBilling } from '@/apis/billing';
import BillingInfoForm from '@/components/common/memberForm/BillingInfoForm';
import { useMemberBillingStore } from '@/stores/useMemberBillingStore';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const UpdateBillingInfo = ({ formType }) => {
  const { billingInfo } = useMemberBillingStore();
  const navigate = useNavigate();

  const { contractId, memberId } = useParams();

  // <--------청구 정보 수정 API-------->
  const axiosUpdateMemberBilling = async () => {
    try {
      await updateMemberBilling(memberId, billingInfo);
      console.log('!----청구정보 수정 성공----!'); // 삭제예정
      await navigate(`/vendor/contracts/detail/${contractId}`);
      onAlertClick('청구정보가 수정되었습니다!');
    } catch (err) {
      console.error('axiosMemberUpdate => ', err.response);
    }
  };

  // <--------청구정보 수정 성공 Alert창-------->
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async message => {
    await alertComp(message);
  };

  return (
    <>
      <BillingInfoForm formType={formType} />
      <div className='absolute bottom-0 left-0 flex h-[65px] w-full justify-end px-7 pb-5 font-800 text-lg '>
        <button
          className=' px-10 py-2 border border-mint rounded-lg text-mint'
          onClick={() => navigate(-1)}>
          취소
        </button>
        <button
          className=' px-10 py-2 bg-mint rounded-lg text-white transition-all duration-200 hover:bg-mint_hover ml-3'
          onClick={axiosUpdateMemberBilling}>
          저장
        </button>
      </div>
    </>
  );
};

export default UpdateBillingInfo;
