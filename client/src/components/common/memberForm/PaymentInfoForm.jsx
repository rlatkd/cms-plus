import React, { useEffect } from 'react';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import AutoTypeForm from '@/components/common/memberForm/PaymentType/AutoTypeForm';
import BuyerTypeForm from '@/components/common/memberForm/PaymentType/BuyerTypeForm';
import VirtualAccountTypeForm from '@/components/common/memberForm/PaymentType/VirtualAccountTypeForm';
import SelectField from '@/components/common/selects/SelectField';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import InputCalendar from '@/components/common/inputs/InputCalendar';

const PaymentType = [
  { label: '자동결제', value: 'AUTO' },
  { label: '납부자 결제', value: 'BUYER' },
  { label: '가상계좌', value: 'VIRTUAL' },
];

const contractDays = [
  { value: '', label: '-- 선택 --' },
  ...[...Array(31)].map((_, i) => ({ value: i + 1, label: `${i + 1}일` })),
];

const PaymentInfoForm = ({ formType }) => {
  const { paymentType, setPaymentType } = useMemberPaymentStore();
  const { contractInfo, setContractInfoItem } = useMemberContractStore();

  const handleChangePaymentType = value => {
    setPaymentType(value);
  };

  const handleChangeContractInfo = e => {
    const { id, value } = e.target;
    setContractInfoItem({ [id]: value });
  };

  const renderPaymentTypeForm = () => {
    switch (paymentType) {
      case 'AUTO':
        return <AutoTypeForm paymentType={paymentType} formType={formType} />;
      case 'BUYER':
        return <BuyerTypeForm paymentType={paymentType} formType={formType} />;
      case 'VIRTUAL':
        return <VirtualAccountTypeForm paymentType={paymentType} formType={formType} />;
      default:
        return null;
    }
  };

  // <------ formType : UPDATE일 경우 계약일 비활성화 ------>
  const isDisabled = formType === 'UPDATE';

  // <------ 필요성 여부 검토 필요 ------>
  // useEffect(() => {
  //   if (paymentType === 'AUTO') {
  //     setPaymentMethod('CMS');
  //   } else if (paymentType !== 'AUTO') {
  //     setPaymentMethod('');
  //   }
  // }, [paymentType]);

  return (
    <div className='flex flex-col pt-5 px-2 h-[calc(100%-120px)] '>
      <div className='flex mb-4 border-b border-ipt_border '>
        <RadioGroup
          label='결제방식'
          name='PaymentType'
          options={PaymentType}
          required={true}
          selectedOption={paymentType}
          onChange={handleChangePaymentType}
          classLabel='ml-1 mb-3'
        />
        <SelectField
          label='약정일'
          classContainer=' mx-6'
          classLabel='text-15 text-text_black font-700'
          classSelect='py-3 pr-40 rounded-lg'
          required
          value={contractInfo.contractDay}
          options={contractDays}
          onChange={e => setContractInfoItem({ contractDay: e.target.value })}
        />
        <div className='relative flex justify-center '>
          <InputCalendar
            id='contractStartDate'
            label='계약 시작일'
            placeholder='년도-월-일'
            required
            readOnly
            disabled={isDisabled}
            value={contractInfo.contractStartDate}
            handleChangeValue={handleChangeContractInfo}
          />
          <p className='mx-3 mt-10 text-text_grey'>~</p>
          <InputCalendar
            id='contractEndDate'
            label='계약 종료일'
            placeholder='년도-월-일'
            required
            readOnly
            disabled={isDisabled}
            value={contractInfo.contractEndDate}
            handleChangeValue={handleChangeContractInfo}
          />
        </div>
      </div>
      <div>{renderPaymentTypeForm()}</div>
    </div>
  );
};

export default PaymentInfoForm;
