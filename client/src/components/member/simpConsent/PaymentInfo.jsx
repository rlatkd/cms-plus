import React, { useState, useEffect, forwardRef, useImperativeHandle, useCallback } from 'react';
import PaymentCard from './PaymentCard';
import PaymentCMS from './PaymentCMS';
import { useUserDataStore } from '@/stores/useUserDataStore';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import { getAvailableOptions } from '@/apis/simpleConsent';

const PaymentInfo = forwardRef((props, ref) => {
  const { userData, setUserData } = useUserDataStore();
  const [localData, setLocalData] = useState({
    paymentMethod: userData.paymentDTO.paymentMethod || '',
    ...userData.paymentDTO,
  });
  const [availablePaymentMethods, setAvailablePaymentMethods] = useState([]);
  const [isVerified, setIsVerified] = useState(false);

  useEffect(() => {
    const fetchAvailableOptions = async () => {
      try {
        const options = await getAvailableOptions();
        setAvailablePaymentMethods(options.availablePaymentMethods);
      } catch (error) {
        console.error('결제 수단 옵션 가져오기 실패:', error);
      }
    };

    fetchAvailableOptions();
  }, []);

  useImperativeHandle(ref, () => ({
    handleNextClick: () => {
      setUserData({
        paymentDTO: {
          ...localData,
        },
      });
    },
    validatePaymentInfo: () => {
      const missingFields = [];

      if (!localData.paymentMethod) missingFields.push('결제수단');

      if (localData.paymentMethod === 'CARD') {
        if (!isVerified) missingFields.push('카드 인증');
        if (!localData.cardNumber) missingFields.push('카드번호');
        if (!localData.expiryDate) missingFields.push('유효기간');
        if (!localData.cardHolder) missingFields.push('명의자');
        if (!localData.cardOwnerBirth) missingFields.push('생년월일');
      } else if (localData.paymentMethod === 'CMS') {
        if (!isVerified) missingFields.push('계좌 인증');
        if (!localData.bank) missingFields.push('은행');
        if (!localData.accountHolder) missingFields.push('예금주');
        if (!localData.accountOwnerBirth) missingFields.push('생년월일');
        if (!localData.accountNumber) missingFields.push('계좌번호');
      }

      return missingFields;
    },
  }));

  const handlePaymentMethodChange = useCallback(method => {
    setLocalData(prev => ({ ...prev, paymentMethod: method }));
    setIsVerified(false); // 결제 수단이 변경되면 인증 상태 초기화
  }, []);

  const handleVerificationComplete = useCallback(verified => {
    console.log('카드/계좌 인증 완료:', verified);
    setIsVerified(verified);
  }, []);

  const handleInputChange = useCallback((name, value) => {
    setLocalData(prev => ({ ...prev, [name]: value }));
  }, []);

  const paymentOptions = availablePaymentMethods.map(method => ({
    label: method.title,
    value: method.code,
  }));

  return (
    <>
      <div className='w-full text-left'>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          회원님의
          <br />
          결제정보를 확인해주세요.
        </h3>
      </div>
      <RadioGroup
        label='결제수단'
        name='paymentMethod'
        options={paymentOptions}
        selectedOption={localData.paymentMethod}
        onChange={handlePaymentMethodChange}
        required={true}
      />
      {localData.paymentMethod === 'CARD' && (
        <PaymentCard
          localData={localData}
          onInputChange={handleInputChange}
          onVerificationComplete={handleVerificationComplete}
          isVerified={isVerified}
        />
      )}
      {localData.paymentMethod === 'CMS' && (
        <PaymentCMS
          localData={localData}
          onInputChange={handleInputChange}
          onVerificationComplete={handleVerificationComplete}
          isVerified={isVerified}
        />
      )}
    </>
  );
});

export default PaymentInfo;
