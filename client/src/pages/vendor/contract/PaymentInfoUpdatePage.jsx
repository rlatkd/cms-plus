import { updatePaymentDetail } from '@/apis/payment';
import PaymentInfoForm from '@/components/common/memberForm/PaymentInfoForm';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import AlertWdithContext from '@/utils/dialog/alertwidth/AlertWidthContext';
import { formatCardYearForStorage } from '@/utils/format/formatCardDate';
import { useContext, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const PaymentInfoUpdatePage = () => {
  const {
    paymentType,
    paymentMethod,
    paymentTypeInfoReq_Virtual,
    paymentTypeInfoReq_Buyer,
    paymentTypeInfoReq_Auto,
    paymentMethodInfoReq_Cms,
    paymentMethodInfoReq_Card,
    resetPaymentType,
    resetPaymentTypeInfoReq_Virtual,
    resetPaymentTypeInfoReq_Buyer,
    resetPaymentTypeInfoReq_Auto,
    resetPaymentMethod,
    resetPaymentMethodInfoReq_Cms,
    resetPaymentMethodInfoReq_Card,
  } = useMemberPaymentStore(); // 결제정보 - 수정목적

  const navigate = useNavigate();
  const { id: contractId } = useParams();

  // <------ 결제 정보 수정 요청Data 형태변환 ------>
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
      ...(paymentType === 'AUTO' && {
        paymentMethodInfoReq: {
          paymentMethod,
          ...(paymentMethod === 'CMS' && paymentMethodInfoReq_Cms),
          ...(paymentMethod === 'CARD' && {
            ...paymentMethodInfoReq_Card,
            cardYear: formatCardYearForStorage(paymentMethodInfoReq_Card.cardYear),
          }),
        },
      }),
    };

    return paymentCreateReq;
  };

  // <------ 결제 정보 수정 API ------>
  const axiosUpdatePaymentDetail = async () => {
    try {
      const res = await updatePaymentDetail(contractId, transformPaymentInfo());
      console.log('!----결제 정보 수정 성공----!'); // 삭제예정
      console.log(res);
      await navigate(`/vendor/contracts/detail/${contractId}`);
      onAlertWidthClick();
    } catch (err) {
      console.error('axiosUpdatePaymentDetail => ', err.response.data);
    }
  };

  // <------ 결제정보 수정 성공 Alert창 ------>
  const { alertWidth: alertWidthComp } = useContext(AlertWdithContext);
  const onAlertWidthClick = async () => {
    const result = await alertWidthComp('결제정보가 수정되었습니다!');
  };

  // <------ 페이지 이탈 시 데이터 reset ------>
  // 언마운트 시 함수를 발생 시키는 구조
  useEffect(() => {
    const handleResetPaymentStore = () => {
      resetPaymentType();
      if (paymentType === 'VIRTUAL') {
        resetPaymentTypeInfoReq_Virtual();
      } else if (paymentType === 'BUYER') {
        resetPaymentTypeInfoReq_Buyer();
      } else if (paymentType === 'AUTO') {
        resetPaymentTypeInfoReq_Auto();
        if (paymentMethod === 'CMS') {
          resetPaymentMethodInfoReq_Cms();
        } else if (paymentMethod === 'CARD') {
          resetPaymentMethodInfoReq_Card();
        }
      }
      resetPaymentMethod();
    };

    return () => {
      handleResetPaymentStore();
    };
  }, []);

  return (
    <>
      <div className='up-dashboard relative mb-4 w-full desktop:h-[18%]'>progressivee</div>
      <div className='primary-dashboard flex flex-col relative h-[1000px] large_desktop:h-[80%] '>
        <div className='flex items-center h-[50px] px-2 pb-[10px] '>
          <p className='text-text_black text-xl font-800'>기본정보</p>
        </div>
        <PaymentInfoForm formType={'UPDATE'} />
        <div className='absolute bottom-0 left-0 flex h-[65px] w-full justify-end px-7 pb-5 font-800 text-lg '>
          <button
            className=' px-10 py-2 border border-mint rounded-lg text-mint'
            onClick={() => navigate(-1)}>
            취소
          </button>
          <button
            className=' px-10 py-2 bg-mint rounded-lg text-white transition-all duration-200 hover:bg-mint_hover ml-3'
            onClick={axiosUpdatePaymentDetail}>
            저장
          </button>
        </div>
      </div>
    </>
  );
};

export default PaymentInfoUpdatePage;
