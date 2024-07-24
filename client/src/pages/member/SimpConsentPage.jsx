import React, { useState, useRef } from 'react';
import axios from 'axios';
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

const SimpConsentPage = () => {
  const start = 0;
  const end = 6;
  const status = useStatusStore(state => state.status);
  const setStatus = useStatusStore(state => state.setStatus);
  const { handleClickPrevious, handleClickNext: originalHandleClickNext } = useStatusStepper(
    'simpconsent',
    start,
    end
  );

  const userData = useUserDataStore(state => state.userData);
  const setUserData = useUserDataStore(state => state.setUserData);
  const [isCardVerified, setIsCardVerified] = useState(false);
  const paymentInfoRef = useRef();
  const contractInfoRef = useRef();

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

  const validateSignatureInfo = () => {
    const { signatureUrl } = userData.contractDTO;
    const missingFields = [];

    if (!signatureUrl) missingFields.push('서명');

    return missingFields;
  };

  const handleClickNext = async () => {
    if (status === 1) {
      //const missingFields = validateBasicInfo();
      const missingFields = [];
      if (missingFields.length > 0) {
        alert(`다음 필드를 입력해주세요: ${missingFields.join(', ')}`);
        return;
      }
    }

    if (status === 2) {
      if (contractInfoRef.current) {
        //const missingFields = contractInfoRef.current.validateContractInfo();
        const missingFields = [];
        if (missingFields.length > 0) {
          alert(`다음 필드를 입력해주세요: ${missingFields.join(', ')}`);
          return;
        }
      }
    }

    if (status === 3) {
      if (paymentInfoRef.current) {
        // const missingFields = paymentInfoRef.current.validatePaymentInfo();
        const missingFields = [];
        if (missingFields.length > 0) {
          alert(`다음 필드를 입력해주세요: ${missingFields.join(', ')}`);
          return;
        }
        paymentInfoRef.current.handleNextClick();
      }
    }

    if (status === 4) {
      const missingFields = validateSignatureInfo();
      if (missingFields.length > 0) {
        alert(`다음 필드를 입력해주세요: ${missingFields.join(', ')}`);
        return;
      }

      try {
        setStatus(5); // Show loading
        const preparedData = prepareData(userData);
        const response = await axios.post(
          'http://localhost:8080/api/v1/simple-consent',
          preparedData,
          {
            headers: {
              'Content-Type': 'application/json',
            },
          }
        );
        if (response.status === 200) {
          setStatus(6); // Show success
        } else {
          console.error('API request failed');
          setStatus(4); // Go back to signature page
        }
      } catch (error) {
        console.error('API request failed', error.response?.data || error.message);
        setStatus(4); // Go back to signature page
      }
    } else {
      originalHandleClickNext();
    }
  };

  const componentMap = {
    0: Main,
    1: () => <BasicInfo userData={userData} setUserData={setUserData} />,
    2: () => <ContractInfo ref={contractInfoRef} />,
    3: () => (
      <PaymentInfo ref={paymentInfoRef} onVerificationComplete={handleCardVerificationComplete} />
    ),
    4: () => <Signature userData={userData} setUserData={setUserData} />,
    5: () => <Loading content={'등록중...'} />,
    6: () => <Success userData={userData} />,
  };

  const Content = componentMap[status] || (() => 'error');

  return (
    <>
      <Content />
      <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
        <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
        <NextButton onClick={handleClickNext} status={status} type={'simpconsent'} end={end} />
      </div>
    </>
  );
};

export default SimpConsentPage;
