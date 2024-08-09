import { updatePaymentDetail } from '@/apis/payment';
import { sendReqSimpConsent } from '@/apis/simpleConsent';
import PaymentInfoForm from '@/components/common/memberForm/PaymentInfoForm';
import useAlert from '@/hooks/useAlert';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import { formatCardYearForStorage } from '@/utils/format/formatCard';
import { validateField } from '@/utils/validators';
import { useNavigate, useParams } from 'react-router-dom';

const UpdatePaymentInfo = ({ formType }) => {
  const { ...payment } = useMemberPaymentStore(); // 결제정보 - 수정목적
  const { contractInfo } = useMemberContractStore();
  const { contractId } = useParams();
  const onAlert = useAlert();
  const navigate = useNavigate();

  // <------ 결제 정보 수정 요청Data 형태변환 ------>
  const transformPaymentInfo = () => {
    const paymentCreateReq = {
      paymentTypeInfoReq: {
        paymentType: payment.paymentType,
        ...(payment.paymentType === 'VIRTUAL' && payment.paymentTypeInfoReq_Virtual),
        ...(payment.paymentType === 'BUYER' && payment.paymentTypeInfoReq_Buyer),
        ...(payment.paymentType === 'AUTO' &&
          (() => {
            const { consetImgName, ...rest } = payment.paymentTypeInfoReq_Auto;
            return rest;
          })()),
      },
      ...(payment.paymentType === 'AUTO' &&
        payment.paymentMethod !== '' && {
          paymentMethodInfoReq: {
            paymentMethod: payment.paymentMethod,
            ...(payment.paymentMethod === 'CMS' && payment.paymentMethodInfoReq_Cms),
            ...(payment.paymentMethod === 'CARD' && {
              ...payment.paymentMethodInfoReq_Card,
              cardYear: formatCardYearForStorage(payment.paymentMethodInfoReq_Card.cardYear),
            }),
          },
        }),
      contractDay: contractInfo.contractDay,
    };
    return paymentCreateReq;
  };

  // <------ 결제 정보 수정 API ------>
  const axiosUpdatePaymentDetail = async () => {
    try {
      // if (!validatePaymentInfo()) return;
      const res = await updatePaymentDetail(contractId, transformPaymentInfo());
      console.log('!----결제 정보 수정 성공----!'); // 삭제예정

      if (payment.isSimpConsentCheck && !payment.paymentMethod && payment.paymentType === 'AUTO') {
        await axiosSendReqSimpConsent(contractId);
      }

      onAlert({ msg: '결제정보가 수정되었습니다!', type: 'success', title: '결제정보수정' });
    } catch (err) {
      onAlert({ err });
      console.error('axiosUpdatePaymentDetail => ', err.response);
    }
  };

  // <----- 간편서명동의 링크 발송하기 ----->
  const axiosSendReqSimpConsent = async contractId => {
    try {
      const res = await sendReqSimpConsent(contractId);
      console.log('!----간편서명동의 링크 발송하기 성공----!'); // 삭제예정
    } catch (err) {
      console.error('axiosSendReqSimpConsent => ', err.response);
    }
  };

  // <----- 유효성 검사 : PaymentInfo ----->
  const validatePaymentInfo = data => {
    const isValidContractDay = contractInfo.contractDay >= 1 && contractInfo.contractDay <= 31;
    let isSuccess = isValidContractDay;

    const paymentTypeInfoReq = data.paymentTypeInfoReq;
    const paymentMethodInfoReq = data.paymentMethodInfoReq;

    // 자동결제 선택한 경우
    if (paymentTypeInfoReq.paymentType === 'AUTO') {
      // Cms 선택한 경우
      if (paymentMethodInfoReq && paymentMethodInfoReq.paymentMethod === 'CMS') {
        const isValidBank = paymentMethodInfoReq.bank !== '';
        const isValidAccountNumber = validateField(
          'accountNumber',
          paymentMethodInfoReq.accountNumber
        );
        const isValidAccountOwner = validateField('name', paymentMethodInfoReq.accountOwner);
        const isValidAccountOwnerBirth = paymentMethodInfoReq.accountOwnerBirth !== '';

        isSuccess =
          isSuccess &&
          isValidBank &&
          isValidAccountNumber &&
          isValidAccountOwner &&
          isValidAccountOwnerBirth;
      }
      // Card 선택한 경우
      else if (paymentMethodInfoReq && paymentMethodInfoReq.paymentMethod === 'CARD') {
        const isValidCardNumber = validateField('cardNumber', paymentMethodInfoReq.cardNumber);
        const isValidCardMonth = validateField('cardMonth', paymentMethodInfoReq.cardMonth);
        const isValidCardYear = paymentMethodInfoReq.cardYear !== '';
        const isValidCardOwner = validateField('name', paymentMethodInfoReq.cardOwner);
        const isValidCardOwnerBirth = paymentMethodInfoReq.cardOwnerBirth !== '';

        isSuccess =
          isSuccess &&
          isValidCardNumber &&
          isValidCardMonth &&
          isValidCardYear &&
          isValidCardOwner &&
          isValidCardOwnerBirth;
      }
    }
    if (paymentTypeInfoReq.paymentType === 'VIRTUAL') {
      const isValidBank = paymentTypeInfoReq.bank !== '';
      const isValidAccountOwner = validateField('name', paymentTypeInfoReq.accountOwner);
      isSuccess = isSuccess && isValidBank && isValidAccountOwner;
    }

    if (!isSuccess) {
      onAlert({ msg: '결제정보가 잘못 입력되었습니다.', type: 'error', title: '입력 정보 오류' });
    }
    return isSuccess;
  };

  return (
    <>
      <PaymentInfoForm formType={formType} />
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
    </>
  );
};

export default UpdatePaymentInfo;
