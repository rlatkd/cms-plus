import { postCreateMember } from '@/apis/member';
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
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const MemberRegisterPage = () => {
  const start = 0;
  const end = 4;
  const { status } = useStatusStore();
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
    resetPaymentType,
    resetPaymentMethod,
    resetPaymentTypeInfoReq_Virtual,
    resetPaymentTypeInfoReq_Buyer,
    resetPaymentTypeInfoReq_Auto,
    resetPaymentMethodInfoReq_Cms,
    resetPaymentMethodInfoReq_Card,
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

  // TODO
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
        onAlertClick();
      }
    } catch (err) {
      console.error('axiosCreateMember => ', err.response.data);
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
        ...(paymentType === 'AUTO' &&
          (() => {
            const { consetImgName, ...rest } = paymentTypeInfoReq_Auto;
            return rest;
          })()),
        ...(paymentType === 'BUYER' && paymentTypeInfoReq_Buyer),
        ...(paymentType === 'VIRTUAL' && paymentTypeInfoReq_Virtual),
      },
    };

    if (paymentType === 'AUTO') {
      paymentCreateReq.paymentMethodInfoReq = {
        paymentMethod,
        ...(paymentMethod === 'CMS' && paymentMethodInfoReq_Cms),
        ...(paymentMethod === 'CARD' && paymentMethodInfoReq_Card),
      };
    }

    return paymentCreateReq;
  };

  // <--------기본정보 수정 성공 Alert창-------->
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async () => {
    await alertComp('회원정보가 등록되었습니다!');
  };

  // TODO
  // <------ 페이지 이탈 시 Status reset ------>

  // <------ 회원등록 입력 데이터 reset------>
  useEffect(() => {
    resetBasicInfo();
    resetBillingInfo();
    resetContractInfo();
    resetPaymentType();
    resetPaymentMethod();
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
