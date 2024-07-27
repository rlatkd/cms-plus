import { postCreateMember } from '@/apis/member';
import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';
import ProgressBar from '@/components/common/ProgressBar';
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
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { formatCardYearForStorage } from '@/utils/format/formatCard';
import { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import close from '@/assets/close.svg';

const MemberRegisterPage = () => {
  const start = 0;
  const end = 4;
  const { status, reset } = useStatusStore();
  const { handleClickPrevious, handleClickNext } = useStatusStepper('memberRegister', start, end);
  const navigate = useNavigate();

  // <------ 회원등록 입력 데이터 ------>
  const { basicInfo, resetBasicInfo } = useMemberBasicStore();
  const { contractInfo, resetContractInfo } = useMemberContractStore();
  const { billingInfo, resetBillingInfo } = useMemberBillingStore();
  const {
    paymentType,
    paymentMethod,
    paymentTypeInfoReq_Virtual,
    paymentTypeInfoReq_Buyer,
    paymentTypeInfoReq_Auto,
    paymentMethodInfoReq_Cms,
    paymentMethodInfoReq_Card,
    ...paymentResetFunctions
  } = useMemberPaymentStore();

  // <------ 등록 폼 교체 ------>
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

  // <------ 회원등록 API ------>
  const axiosCreateMember = async () => {
    try {
      const data = {
        ...basicInfo, // 기본정보
        contractCreateReq: transformContractInfo(), // 계약정보
        paymentCreateReq: transformPaymentInfo(), // 결제정보
        ...billingInfo, // 청구정보
      };
      console.log(data);
      if (status === 3) {
        const res = await postCreateMember(data);
        console.log('!----회원등록 성공----!'); // 삭제예정
        await navigate('/vendor/members');
        onAlertClick('회원정보가 등록되었습니다!');
      }
    } catch (err) {
      console.error('axiosCreateMember => ', err.response);
    }
  };

  // <------ 상품 목록을 contractProducts형식으로 변환------>
  const mapContractProducts = products => {
    return products.map(option => ({
      productId: option.productId,
      price: option.price,
      quantity: option.quantity || 1, // 기본 값으로 1 설정
    }));
  };

  // <------ 계약정보 데이터 변화 ------>
  const transformContractInfo = () => {
    const { contractName, contractStartDate, contractEndDate, contractDay, contractProducts } =
      contractInfo;
    return {
      contractName,
      contractStartDate,
      contractEndDate,
      contractDay,
      contractProducts: mapContractProducts(contractProducts),
    };
  };

  // <------ 결제정보 데이터 변화 ------>
  const transformPaymentInfo = () => {
    const paymentCreateReq = {
      paymentTypeInfoReq: {
        paymentType,
        ...(paymentType === 'VIRTUAL' && paymentTypeInfoReq_Virtual),
        ...(paymentType === 'BUYER' && paymentTypeInfoReq_Buyer),
        ...(paymentType === 'AUTO' &&
          (() => {
            const { consetImgName, ...rest } = paymentTypeInfoReq_Auto;
            return rest;
          })()),
      },
    };

    if (paymentType === 'AUTO') {
      paymentCreateReq.paymentMethodInfoReq = {
        paymentMethod,
        ...(paymentMethod === 'CMS' && paymentMethodInfoReq_Cms),
        ...(paymentMethod === 'CARD' && {
          ...paymentMethodInfoReq_Card,
          cardYear: formatCardYearForStorage(paymentMethodInfoReq_Card.cardYear),
        }),
      };
    }

    return paymentCreateReq;
  };

  // <----- 기본정보 수정 성공 Alert창 ------>
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async message => {
    await alertComp(message);
  };

  // <----- 페이지 이탈 시 Status reset ----->
  useEffect(() => {
    return () => {
      reset();
      resetBasicInfo();
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

  // <----- 회원등록 입력 데이터 reset ----->
  // useEffect(() => {

  // }, []);

  return (
    <>
      <div className='up-dashboard relative flex justify-center items-center mb-4 w-full desktop:h-[18%]'>
        <ProgressBar steps={['기본정보', '계약정보', '결제정보', '청구정보']} />
        <img
          src={close}
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
