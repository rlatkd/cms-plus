import CardInfo from '@/components/member/card/CardInfo';
import ChooseCard from '@/components/member/card/ChooseCard';
import Loading from '@/components/common/member/Loading';
import Success from '@/components/common/member/Success';

import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import useStatusStepper from '@/hooks/useStatusStepper';
import { useStatusStore } from '@/stores/useStatusStore';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { requestCardPayment } from '@/apis/payment';
import { useEffect, useState } from 'react';
import { validateField } from '@/utils/validators';
import { unformatCardNumber } from '@/utils/format/formatCard';
import { useNavigate, useParams } from 'react-router-dom';

const PaymentCardPage = () => {
  const start = 0;
  const end = 6;
  const { status, reset, setStatus } = useStatusStore();
  const { handleClickPrevious, handleClickNext: originalHandleClickNext } = useStatusStepper(
    'card',
    start,
    end
  );
  const [isVerified, setIsVerified] = useState(false);
  const { invoiceInfo, selectedCard } = useInvoiceStore();
  const [billingId, setBillingId] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');

  const { invoiceId } = useParams();
  const navigate = useNavigate();

  const validateSelectedCard = () => {
    const missingFields = [];
    if (!selectedCard) missingFields.push('카드 선택');

    return { missingFields };
  };

  const validateCardInfo = () => {
    const { cardNumber, expiryDate, cardOwner, cardOwnerBirth } = cardInfo;
    const missingFields = [];
    const invalidFields = [];

    if (!isVerified) missingFields.push('카드 인증');

    if (!cardNumber) missingFields.push('카드번호');
    else if (!validateField('cardNumber', unformatCardNumber(cardNumber)))
      invalidFields.push('카드번호');

    if (!expiryDate) missingFields.push('유효기간');
    else if (!validateField('expiryDate', expiryDate)) invalidFields.push('유효기간');

    if (!cardOwner) missingFields.push('명의자');
    else if (!validateField('name', cardOwner)) invalidFields.push('명의자');

    if (!cardOwnerBirth) missingFields.push('생년월일');
    else if (!validateField('birth', cardOwnerBirth)) invalidFields.push('생년월일');

    return { missingFields, invalidFields };
  };

  const handleClickNext = async () => {
    let missingFields = [];
    let invalidFields = [];
    let selectedCardValidation;
    let cardInfoValidation;

    switch (status) {
      case 3:
        selectedCardValidation = validateSelectedCard();
        missingFields = selectedCardValidation.missingFields;
        break;

      case 4:
        cardInfoValidation = validateCardInfo();
        missingFields = cardInfoValidation.missingFields;
        invalidFields = cardInfoValidation.invalidFields;
        break;

      default:
        break;
    }

    if (missingFields.length > 0) {
      alert(`다음 필드를 입력해주세요: ${missingFields.join(', ')}`);
      return;
    }

    if (invalidFields.length > 0) {
      alert(`다음 필드의 형식이 올바르지 않습니다: ${invalidFields.join(', ')}`);
      return;
    }

    // 모든 유효성 검사를 통과한 경우에만 실행
    if (status === 4 && missingFields.length === 0 && invalidFields.length === 0) {
      try {
        await axiosCardPayment();
        // 결제가 성공적으로 완료된 경우에만 다음 단계로 진행
        originalHandleClickNext();
      } catch (error) {
        console.error('Card payment failed:', error);
        alert('카드 결제에 실패했습니다. 다시 시도해주세요.');
        return;
      }
    } else {
      // status가 4가 아니거나, 유효성 검사를 통과하지 못한 경우
      originalHandleClickNext();
    }
  };

  const [cardInfo, setCardInfo] = useState({
    cardNumber: '',
    expiryDate: '',
    cardOwner: '',
    cardOwnerBirth: '',
  });

  const componentMap = {
    3: ChooseCard, //카드사 선택
    4: CardInfo, //카드정보 입력
    5: () => <Loading content={'결제중...'} />, //결제중
    6: () => <Success content='결제가 완료되었습니다!' />, //입금완료
  };

  const Content = componentMap[status] || (() => 'error');

  const number = cardInfo.cardNumber; //카드번호
  const method = 'CARD';

  const paymentData = {
    billingId: billingId,
    phoneNumber: phoneNumber,
    method: method,
    number: number,
  };

  const axiosCardPayment = async () => {
    try {
      const res = await requestCardPayment(paymentData);
    } catch (err) {
      console.error('axiosCardPayment => ', err.response);
    }
  };

  useEffect(() => {
    if (invoiceInfo) {
      setBillingId(invoiceInfo.billingId);
      setPhoneNumber(invoiceInfo.member.phone);
    } else {
      reset();
      navigate(`/member/invoice/${invoiceId}`);
    }
  }, []);

  // <----- 로딩 타임아웃 설정 ----->
  useEffect(() => {
    if (status === 5) {
      const timer = setTimeout(() => {
        setStatus(6);
      }, 2500);

      return () => clearTimeout(timer);
    }
  }, [status, setStatus]);

  return (
    <>
      <Content
        cardInfo={cardInfo}
        setCardInfo={setCardInfo}
        isVerified={isVerified}
        setIsVerified={setIsVerified}
      />
      {status != 5 && status != 6 && (
        <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
          <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
          <NextButton onClick={handleClickNext} type={'card'} status={status} end={end} />
        </div>
      )}
    </>
  );
};

export default PaymentCardPage;
