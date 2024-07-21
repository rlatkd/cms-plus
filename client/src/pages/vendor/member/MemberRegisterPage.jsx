import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';
import RegisterBasicInfo from '@/components/vendor/member/registers/RegisterBasicInfo';
import RegisterBillingInfo from '@/components/vendor/member/registers/RegisterBillingInfo';
import RegisterContractInfo from '@/components/vendor/member/registers/RegisterContractInfo';
import RegisterPaymentInfo from '@/components/vendor/member/registers/RegisterPaymentInfo';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useMemberBasicStore } from '@/stores/useMemberBasicStore';
import { useMemberBillingStore } from '@/stores/useMemberBillingStore';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import { useStatusStore } from '@/stores/useStatusStore';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const MemberRegisterPage = () => {
  const start = 0;
  const end = 4;
  const { status } = useStatusStore();
  const { handleClickPrevious, handleClickNext } = useStatusStepper('memberRegister', start, end);
  const navigate = useNavigate();

  // <------ 회원등록 입력 데이터 ------>
  const { resetBasicInfo } = useMemberBasicStore();
  const { resetContractInfo } = useMemberContractStore();
  const { resetBillingInfo } = useMemberBillingStore();
  const {
    resetPaymentTypeInfoReq_Virtual,
    resetPaymentTypeInfoReq_Buyer,
    resetPaymentTypeInfoReq_Auto,
    resetPaymentMethodInfoReq_Cms,
    resetPaymentMethodInfoReq_Card,
  } = useMemberPaymentStore();

  const componentMap = {
    0: { title: '기본정보', component: RegisterBasicInfo }, // 기본정보
    1: { title: '계약정보', component: RegisterContractInfo }, // 계약정보
    2: { title: '결제정보', component: RegisterPaymentInfo }, // 결제정보
    3: { title: '청구정보', component: RegisterBillingInfo }, // 청구정보
  };

  const { title, component: Content } = componentMap[status] || {
    title: 'error',
    component: () => 'error',
  };

  // <------ 상품 목록을 contractProducts형식으로 변환------>
  const mapContractProducts = products => {
    return products.map(option => ({
      productId: option.productId,
      price: option.price,
      quantity: option.quantity || 1, // 기본 값으로 1 설정
    }));
  };

  // TODO
  // <------ 회원등록 API ------>
  const axiosCreateMember = () => {};
  // TODO
  // <------ 페이지 이탈 시 Status reset ------>

  // <------ 회원등록 입력 데이터 reset------>
  useEffect(() => {
    resetBasicInfo();
    resetBillingInfo();
    resetContractInfo();
    resetPaymentTypeInfoReq_Virtual();
    resetPaymentTypeInfoReq_Buyer();
    resetPaymentTypeInfoReq_Auto();
    resetPaymentMethodInfoReq_Cms();
    resetPaymentMethodInfoReq_Card();
  }, []);

  return (
    <>
      <div className='up-dashboard relative mb-4 w-full desktop:h-[18%]'>
        progressivee
        <img
          src='/src/assets/close.svg'
          alt='back'
          className='absolute right-6 top-6 cursor-pointer w-4 h-4'
          onClick={() => navigate(-1)}
        />
      </div>
      <div className='primary-dashboard flex flex-col relative h-[1000px] large_desktop:h-[80%] '>
        <div className='flex items-center h-[50px] px-2 pb-[10px] '>
          <p className='text-text_black text-xl font-800'>{title}</p>
        </div>
        <Content formType={'CREATE'} />
        <div className='absolute bottom-0 left-0 flex h-[65px] w-full justify-between px-7 pb-5 font-800 text-lg '>
          <PreviousButton
            onClick={handleClickPrevious}
            status={status}
            type={'memberRegister'}
            start={start}
            end={end}
          />
          <NextButton
            onClick={() => {
              axiosCreateMember();
              handleClickNext();
            }}
            status={status}
            type={'memberRegister'}
            end={end}
          />
        </div>
      </div>
    </>
  );
};

export default MemberRegisterPage;
