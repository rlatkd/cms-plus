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
      <RadioGroup
        label='결제수단'
        name='paymentMethod'
        options={paymentOptions}
        selectedOption={paymentMethod}
        onChange={setPaymentMethod}
      />
      {paymentMethod === 'card' ? <PaymentCard /> : <PaymentCMS />}
    </>
  );
};

export default PaymentInfo;
