import RadioGroup from '@/components/common/RadioGroup';
import PaymentCard from '@/components/member/simpConsent/PaymentCard';
import PaymentCMS from '@/components/member/simpConsent/PaymentCMS';
import { useState } from 'react';

const PaymentInfo = () => {
  const [paymentMethod, setPaymentMethod] = useState('card');

  const paymentOptions = [
    { label: '카드', value: 'card' },
    { label: '실시간 CMS', value: 'cms' },
  ];

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
        selectedOption={paymentMethod}
        onChange={setPaymentMethod}
        required={true}
      />
      {paymentMethod === 'card' ? <PaymentCard /> : <PaymentCMS />}
    </>
  );
};

export default PaymentInfo;
