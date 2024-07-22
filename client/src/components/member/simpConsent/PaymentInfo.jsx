import React from 'react';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import PaymentCard from '@/components/member/simpConsent/PaymentCard';
import PaymentCMS from '@/components/member/simpConsent/PaymentCMS';
import { useUserDataStore } from '@/stores/useUserDataStore';

const PaymentInfo = () => {
  const { userData, setUserData, clearPaymentInfo } = useUserDataStore();

  const paymentOptions = [
    { label: '카드', value: 'CARD' },
    { label: '실시간 CMS', value: 'CMS' },
  ];

  const handlePaymentMethodChange = value => {
    console.log(value);
    clearPaymentInfo(); // 결제 방법이 변경될 때 기존 결제 정보 초기화
    setUserData({ paymentDTO: { ...userData.paymentDTO, paymentMethod: value } });
  };

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
        selectedOption={userData.paymentDTO.paymentMethod}
        onChange={handlePaymentMethodChange}
        required={true}
      />
      {userData.paymentDTO.paymentMethod === 'CARD' ? <PaymentCard /> : <PaymentCMS />}
    </>
  );
};

export default PaymentInfo;
