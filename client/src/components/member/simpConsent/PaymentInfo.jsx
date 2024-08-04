import { useState, useEffect, useCallback } from 'react';
import PaymentCard from './PaymentCard';
import PaymentCMS from './PaymentCMS';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import { getAvailableOptions } from '@/apis/simpleConsent';

const PaymentInfo = ({ userData, setUserData, vendorId, contractId, name }) => {
  const [availablePaymentMethods, setAvailablePaymentMethods] = useState([]);
  const [isVerified, setIsVerified] = useState(false);

  useEffect(() => {
    const fetchAvailableOptions = async () => {
      try {
        const options = await getAvailableOptions(vendorId);
        setAvailablePaymentMethods(options.availablePaymentMethods);
      } catch (error) {
        console.error('결제 수단 옵션 가져오기 실패:', error);
      }
    };

    fetchAvailableOptions();
  }, []);

  const handlePaymentMethodChange = useCallback(
    method => {
      console.log('payment method change')
      setUserData({
        ...userData,
        paymentDTO: { ...userData.paymentDTO, paymentMethod: method },
      });
    },
    [userData, setUserData]
  );

  const handleInputChange = useCallback(
    (name, value) => {
        setUserData({
          ...userData,
          paymentDTO: { ...userData.paymentDTO, [name]: value },
        });
      },
      [userData, setUserData]
    );

  const handleVerificationComplete = useCallback(
    verified => {
      console.log('카드/계좌 인증:', verified);
      
      // 부모 컴포넌트의 userData도 업데이트
      setUserData(prevData => ({
        ...prevData,
        paymentDTO: {
          ...prevData.paymentDTO,
          isVerified: true,
        },
      }));
    },
    [setUserData]
  );

  const paymentOptions = availablePaymentMethods.map(method => ({
    label: method.title,
    value: method.code,
  }));

  useEffect(() => {
    if (contractId) {
      setIsVerified(true);
      handleInputChange('isVerified', true);
    }
  }, []);

  return (
    <>
      <div className='w-full text-left p-1'>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          {name}님의
          <br />
          결제정보를 확인해주세요.
        </h3>
      </div>
      <RadioGroup
        label='결제수단'
        name='paymentMethod'
        options={paymentOptions}
        selectedOption={userData.paymentDTO.paymentMethod}
        onChange={handlePaymentMethodChange}
        required={true}
        disabled={!!contractId}
      />
      {userData.paymentDTO.paymentMethod === 'CARD' && (
        <PaymentCard
          paymentData={userData.paymentDTO}
          onInputChange={handleInputChange}
          onVerificationComplete={handleVerificationComplete}
          isVerified={isVerified}
          contractId={contractId}
        />
      )}
      {userData.paymentDTO.paymentMethod === 'CMS' && (
        <PaymentCMS
          paymentData={userData.paymentDTO}
          onInputChange={handleInputChange}
          onVerificationComplete={handleVerificationComplete}
          isVerified={isVerified}
          contractId={contractId}
        />
      )}
    </>
  );
};

export default PaymentInfo;