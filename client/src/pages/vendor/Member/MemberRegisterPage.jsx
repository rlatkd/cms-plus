import NextButton from '@/components/common/StatusNextButton';
import PreviousButton from '@/components/common/StatusPreButton';
import RegisterBasicInfo from '@/components/vendor/member/RegisterBasicInfo';
import RegisterBillingInfo from '@/components/vendor/member/RegisterBillingInfo';
import RegisterContractInfo from '@/components/vendor/member/RegisterContractInfo';
import RegisterPaymentInfo from '@/components/vendor/member/RegisterPaymentInfo';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useStatusStore } from '@/stores/useStatusStore';

const MemberRegisterPage = () => {
  const start = 0;
  const end = 4;
  const status = useStatusStore(state => state.status);
  const { handleClickPrevious, handleClickNext } = useStatusStepper('memberregister', start, end);

  const componentMap = {
    0: RegisterBasicInfo, // 기본정보
    1: RegisterContractInfo, // 계약정보
    2: RegisterPaymentInfo, // 결제정보
    3: RegisterBillingInfo, // 청구정보
  };

  const Content = componentMap[status] || (() => 'error');

  return (
    <div className='w- flex h-full w-full flex-col'>
      <div className='mb-5 h-48 w-full rounded-lg bg-white p-6 shadow-dash-sub'>progressivee</div>
      <div className='relative h-full w-full rounded-xl p-6 shadow-dash-board'>
        information
        <Content />
        <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between border border-red-400 p-6 font-bold'>
          <PreviousButton
            onClick={handleClickPrevious}
            status={status}
            type={'memberregister'}
            start={start}
            end={end}
          />
          <NextButton onClick={handleClickNext} status={status} type={'memberregister'} end={end} />
        </div>
      </div>
    </div>
  );
};

export default MemberRegisterPage;
