import ProgressBar from '@/components/common/ProgressBar';
import UpdateBillingInfo from '@/components/vendor/contract/updates/UpdateBillingInfo';
import UpdateContractInfo from '@/components/vendor/contract/updates/UpdateContractInfo';
import UpdatePaymentInfo from '@/components/vendor/contract/updates/UpdatePaymentInfo';
import { useMemberBillingStore } from '@/stores/useMemberBillingStore';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import { useStatusStore } from '@/stores/useStatusStore';
import { useEffect } from 'react';

const ContractUpdatePage = () => {
  const { status, reset } = useStatusStore();

  // <------ 회원수정 데이터 ------>
  const { resetContractInfo } = useMemberContractStore();
  const { resetBillingInfo } = useMemberBillingStore();
  const { ...paymentResetFunctions } = useMemberPaymentStore();

  // <------ 등록 폼 교체 ------>
  const componentMap = {
    0: { title: '계약정보', component: UpdateContractInfo }, // 계약정보
    1: { title: '결제정보', component: UpdatePaymentInfo }, // 결제정보
    2: { title: '청구정보', component: UpdateBillingInfo }, // 청구정보
  };

  const { title, component: Content } = componentMap[status] || {
    title: 'error',
    component: () => 'error',
  };

  // <----- 페이지 이탈 시 Status reset ----->
  useEffect(() => {
    return () => {
      reset();
      resetBillingInfo();
      resetContractInfo();
      paymentResetFunctions.resetPaymentType();
      paymentResetFunctions.resetPaymentMethod();
      paymentResetFunctions.resetPaymentTypeInfoReq_Virtual();
      paymentResetFunctions.resetPaymentTypeInfoReq_Buyer();
      paymentResetFunctions.resetPaymentTypeInfoReq_Auto();
      paymentResetFunctions.resetPaymentMethodInfoReq_Cms();
      paymentResetFunctions.resetPaymentMethodInfoReq_Card();
    };
  }, []);

  return (
    <>
      <div className='up-dashboard relative flex justify-center items-center mb-4 w-full desktop:h-[18%]'>
        <ProgressBar steps={['계약정보', '결제정보', '청구정보']} />
      </div>
      <div className='primary-dashboard flex flex-col relative h-[1000px] large_desktop:h-[80%] '>
        <div className='flex items-center h-[50px] px-2 pb-[10px] '>
          <p className='text-text_black text-xl font-800'>{title}</p>
        </div>
        <Content formType={'UPDATE'} />
      </div>
    </>
  );
};

export default ContractUpdatePage;
