import RadioGroup from '@/components/common/inputs/RadioGroup';
import { useEffect, useState } from 'react';
import CardMethodForm from '../PaymentMethod/CardMethodForm';
import CmsMethodForm from '../PaymentMethod/CmsMethodForm';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';

const PaymentMethod = [
  { label: '실시간 CMS', value: 'CMS' },
  { label: '카드', value: 'CARD' },
];

// formType : CREATE, UPDATE
const AutoTypeForm = ({ paymentType, formType }) => {
  const { paymentMethod, setPaymentMethod } = useMemberPaymentStore();

  // <------ Radio paymentMethod 변경 ------>
  const handleChangeValue = value => {
    setPaymentMethod(value);
  };

  // <------ 결제수단에 따른 폼 생성 ------>
  const renderPaymentMethodForm = () => {
    switch (paymentMethod) {
      case 'CMS':
        return <CmsMethodForm paymentMethod={paymentMethod} formType={formType} />;
      case 'CARD':
        return <CardMethodForm paymentMethod={paymentMethod} formType={formType} />;
      default:
        return null;
    }
  };

  return (
    <div>
      <RadioGroup
        label='결제수단'
        name='PaymentMethod'
        options={PaymentMethod}
        required={true}
        selectedOption={paymentMethod}
        onChange={handleChangeValue}
        classLabel='ml-1 mb-3'
      />
      <div>{renderPaymentMethodForm()}</div>
    </div>
  );
};

export default AutoTypeForm;
