import InputWeb from '@/components/common/inputs/InputWeb';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import AutoTypeForm from '@/components/common/memberForm/PaymentType/AutoTypeForm';
import BuyerTypeForm from '@/components/common/memberForm/PaymentType/BuyerTypeForm';
import VirtualAccountTypeForm from '@/components/common/memberForm/PaymentType/VirtualAccountTypeForm';
import SelectField from '@/components/common/selects/SelectField';
import { useState } from 'react';

const PaymentType = [
  { label: '자동결제', value: 'AUTO' },
  { label: '납부자 결제', value: 'BUYER' },
  { label: '가상계좌', value: 'VIRTUAL' },
];

const contractDays = [...Array(31)].map((_, i) => ({ value: i + 1, label: `${i + 1}일` }));

// formType : CREATE, UPDATE
const RegisterPaymentInfo = ({ formType }) => {
  const [selectedPaymentType, setSelectedPaymentType] = useState('AUTO');

  // <------ 인풋 필드 입력값 변경 ------>
  const handleChangeValue = value => {
    setSelectedPaymentType(value);
  };

  // <------ 결제방식에 따른 폼 생성 ------>
  const renderPaymentTypeForm = () => {
    switch (selectedPaymentType) {
      case 'AUTO':
        return <AutoTypeForm paymentType={selectedPaymentType} formType={formType} />;
      case 'BUYER':
        return <BuyerTypeForm paymentType={selectedPaymentType} formType={formType} />;
      case 'VIRTUAL':
        return <VirtualAccountTypeForm paymentType={selectedPaymentType} formType={formType} />;
      default:
        return null;
    }
  };

  // TODO
  // 최종적으로 선택한 결제방식, 수단으로 회원 등록이 진행된다는 알림문구 하나 있으면 좋을 듯

  return (
    <div className='flex flex-col pt-5 px-2 h-[calc(100%-120px)] '>
      <div className='flex mb-4 border-b border-ipt_border '>
        <RadioGroup
          label='결제방식'
          name='PaymentType'
          options={PaymentType}
          required={true}
          selectedOption={selectedPaymentType}
          onChange={handleChangeValue}
          classLabel='ml-1 mb-3'
        />
        <SelectField
          label='약정일'
          classContainer=' mx-6'
          classLabel='text-15 text-text_black font-700'
          classSelect='py-3 pr-40 rounded-lg'
          required
          options={contractDays}
        />

        {/* TODO */}
        {/* 데이터 피커로 변경 */}
        <div className='relative flex justify-center '>
          <InputWeb
            id='contractStartDate'
            label='계약 시작일'
            placeholder='연도-월-일'
            type='calendar'
            required
            classInput='py-3'
          />
          <p className='mx-4 mt-10'>~</p>
          <InputWeb
            id='contractEndDate'
            label='계약 종료일'
            placeholder='연도-월-일'
            type='calendar'
            required
            classInput='py-3'
          />
        </div>
      </div>
      <div>{renderPaymentTypeForm()}</div>
    </div>
  );
};

export default RegisterPaymentInfo;
