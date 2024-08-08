import { useState, useEffect, useCallback } from 'react';
import PaymentCard from './PaymentCard';
import PaymentCMS from './PaymentCMS';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import { getAvailableOptions } from '@/apis/simpleConsent';

const PaymentInfo = ({
  userData,
  setUserData,
  vendorId,
  contractId,
  contract,
  name,
  isVerified,
  setIsVerified,
}) => {
  const [availablePaymentMethods, setAvailablePaymentMethods] = useState([]);

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
      setUserData({
        ...userData,
        paymentDTO: { ...userData.paymentDTO, paymentMethod: method },
      });
      setIsVerified(false);
    },
    [userData, setUserData, setIsVerified]
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

  const paymentOptions = availablePaymentMethods.map(method => ({
    label: method.title,
    value: method.code,
  }));

  useEffect(() => {
    if (contract.paymentMethodInfo) {
      setIsVerified(true);
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
        disabled={!!contract.paymentMethodInfo}
      />
      {userData.paymentDTO.paymentMethod === 'CARD' && (
        <PaymentCard
          paymentData={userData.paymentDTO}
          onInputChange={handleInputChange}
          contractId={contractId}
          contract={contract}
          isVerified={isVerified}
          setIsVerified={setIsVerified}
        />
      )}
      {userData.paymentDTO.paymentMethod === 'CMS' && (
        <PaymentCMS
          paymentData={userData.paymentDTO}
          onInputChange={handleInputChange}
          contractId={contractId}
          contract={contract}
          isVerified={isVerified}
          setIsVerified={setIsVerified}
        />
      )}
    </>
  );
};

export default PaymentInfo;
