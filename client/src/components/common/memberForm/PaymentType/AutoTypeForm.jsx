import RadioGroup from '@/components/common/inputs/RadioGroup';
import { useEffect, useState } from 'react';
import CardMethodForm from '../PaymentMethod/CardMethodForm';
import CmsMethodForm from '../PaymentMethod/CmsMethodForm';

const PaymentMethod = [
  { label: '실시간 CMS', value: 'CMS' },
  { label: '카드', value: 'CARD' },
];

// formType : CREATE, UPDATE
const AutoTypeForm = ({ paymentType, formType }) => {
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState('CMS');

  // <------ Radio paymentMethod 변경 ------>
  const handleChangeValue = value => {
    setSelectedPaymentMethod(value);
  };

  // <------ 결제수단에 따른 폼 생성 ------>
  const renderPaymentMethodForm = () => {
    switch (selectedPaymentMethod) {
      case 'CMS':
        return <CmsMethodForm paymentMethod={selectedPaymentMethod} formType={formType} />;
      case 'CARD':
        return <CardMethodForm paymentMethod={selectedPaymentMethod} formType={formType} />;
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
        selectedOption={selectedPaymentMethod}
        onChange={handleChangeValue}
        classLabel='ml-1 mb-3'
      />
      <div>{renderPaymentMethodForm()}</div>
    </div>
  );
};

export default AutoTypeForm;
