import { useState } from 'react';
import Main from '@/components/member/simpConsent/Main';
import BasicInfo from '@/components/member/simpConsent/BasicInfo';
import ContractInfo from '@/components/member/simpConsent/ContractInfo';
import PaymentInfo from '@/components/member/simpConsent/PaymentInfo';
import Signature from '@/components/member/simpConsent/Signature';
import Loading from '@/components/common/member/Loading';
import Success from '@/components/common/member/Success';

import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import { useStatusStore } from '@/stores/useStatusStore';
import { useUserDataStore } from '@/stores/useUserDataStore';
import useStatusStepper from '@/hooks/useStatusStepper';
import { sendSimpleConsentData } from '@/apis/simpleConsent';

const SimpConsentPage = () => {
  const start = 0;
  const end = 6;
  const { handleClickPrevious, handleClickNext: originalHandleClickNext } = useStatusStepper(
    'simpconsent',
    start,
    end
  );

  const { status, setStatus, reset } = useStatusStore();
  const { userData, setUserData } = useUserDataStore();
  const [isCardVerified, setIsCardVerified] = useState(false);

  const handleCardVerificationComplete = verified => {
    setIsCardVerified(verified);
  };

  const prepareData = data => {
    return {
      ...data,
      contractDay: parseInt(data.contractDay, 10),
      totalPrice: parseInt(data.totalPrice, 10),
      signatureUrl: null,
      signatureBlob: null,
    };
  };

  const validateBasicInfo = () => {
    const { name, phone, email } = userData.memberDTO;
    const missingFields = [];

    if (!name) missingFields.push('회원명');
    if (!phone) missingFields.push('휴대전화');
    if (!email) missingFields.push('이메일');

    return missingFields;
  };

  const validateContractInfo = () => {
    const { contractName, items, startDate, endDate, contractDay } = userData.contractDTO;
    const missingFields = [];

    if (!contractName) missingFields.push('계약명');
    if (items.length === 0) missingFields.push('선택된 상품');
    if (!startDate) missingFields.push('시작 날짜');
    if (!endDate) missingFields.push('종료 날짜');
    if (!contractDay) missingFields.push('약정일');

    return missingFields;
  };

  const validatePaymentInfo = () => {
    const {
      paymentMethod,
      cardNumber,
      expiryDate,
      cardHolder,
      cardOwnerBirth,
      bank,
      accountHolder,
      accountOwnerBirth,
      accountNumber,
      isVerified,
    } = userData.paymentDTO;
    const missingFields = [];

    if (!paymentMethod) missingFields.push('결제수단');

    if (paymentMethod === 'CARD') {
      if (!isVerified) missingFields.push('카드 인증');
      if (!cardNumber) missingFields.push('카드번호');
      if (!expiryDate) missingFields.push('유효기간');
      if (!cardHolder) missingFields.push('명의자');
      if (!cardOwnerBirth) missingFields.push('생년월일');
    } else if (paymentMethod === 'CMS') {
      if (!isVerified) missingFields.push('계좌 인증');
      if (!bank) missingFields.push('은행');
      if (!accountHolder) missingFields.push('예금주');
      if (!accountOwnerBirth) missingFields.push('생년월일');
      if (!accountNumber) missingFields.push('계좌번호');
    }

    return missingFields;
  };

  const validateSignatureInfo = () => {
    const { signatureUrl } = userData.contractDTO;
    const missingFields = [];

    if (!signatureUrl) missingFields.push('서명');

    return missingFields;
  };

  const handleClickNext = async () => {
    let missingFields = [];

    switch (status) {
      case 1:
        missingFields = validateBasicInfo();
        break;
      case 2:
        missingFields = validateContractInfo();
        break;
      case 3:
        missingFields = validatePaymentInfo();
        break;
      case 4:
        missingFields = validateSignatureInfo();
        break;
      default:
        break;
    }

    if (missingFields.length > 0) {
      alert(`다음 필드를 입력해주세요: ${missingFields.join(', ')}`);
      return;
    }

    if (status === 4) {
      console.log('회원 DTO (stringified):', JSON.stringify(userData.memberDTO, null, 2));
      console.log('결제 DTO (stringified):', JSON.stringify(userData.paymentDTO, null, 2));
      console.log('계약 DTO (stringified):', JSON.stringify(userData.contractDTO, null, 2));

      try {
        setStatus(5); // 로딩
        const preparedData = prepareData(userData);
        await sendSimpleConsentData(preparedData);
        setStatus(6); // 성공
      } catch (error) {
        console.error('API request failed', error);
        setStatus(4); // 서명페이지로 다시 보내기
      }
    } else if (status === 6) {
      // Success 페이지에서 NextButton을 클릭했을 때
      reset();
    } else {
      originalHandleClickNext();
    }
  };

  const componentMap = {
    0: Main,
    1: BasicInfo,
    2: ContractInfo,
    3: PaymentInfo,
    4: Signature,
    5: () => <Loading content={'등록중...'} />,
    6: Success,
  };

  const Content = componentMap[status] || (() => 'error');

  return (
    <>
      <Content
        userData={userData}
        setUserData={setUserData}
        onVerificationComplete={handleCardVerificationComplete}
        isCardVerified={isCardVerified}
      />
      <div className='h-28' />
      <div className='fixed bottom-0 left-0 w-full'>
        <div className='absolute inset-0 bg-white opacity-100 blur' />
        <div className='relative flex h-24 w-full justify-between p-6 font-bold z-50'>
          <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
          <NextButton onClick={handleClickNext} status={status} type={'simpconsent'} end={end} />
        </div>
      </div>
    </>
  );
};

export default SimpConsentPage;
