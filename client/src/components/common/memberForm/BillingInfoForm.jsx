import InputWeb from '@/components/common/inputs/InputWeb';
import { useMemberBasicStore } from '@/stores/useMemberBasicStore';
import { formatPhone } from '@/utils/formatPhone';
import RadioGroup from '@/components/common//inputs/RadioGroup';
import { useMemberBillingStore } from '@/stores/useMemberBillingStore';

const invoiceSendMethods = [
  { label: '휴대전화', value: 'SMS' },
  { label: '이메일', value: 'EMAIL' },
];
const autoInvoiceSends = [
  { label: '자동', value: true },
  { label: '수동', value: false },
];
const autoBillings = [
  { label: '자동', value: true },
  { label: '수동', value: false },
];

const BillingInfoForm = ({ formType }) => {
  const { basicInfo } = useMemberBasicStore();
  const { billingInfo, setBillingInfoItem } = useMemberBillingStore();

  // <------ Radio 원하는 선택지 서택 ------>
  const handleChangeValue = (value, name) => {
    setBillingInfoItem({ [name]: value });
  };

  return (
    <div className='flex flex-col pt-5 px-2 h-[calc(100%-120px)] '>
      <div className='flex w-2/3 mb-10 '>
        <InputWeb
          id='memberPhone'
          label='납부자 휴대전화'
          placeholder='ex) 010-9999-9999'
          type='text'
          required
          disabled
          value={formatPhone(basicInfo.memberPhone)}
          classContainer='flex-1 mr-6'
        />

        <InputWeb
          id='memberEmail'
          label='납부자 이메일'
          placeholder='ex) example@gmail.com'
          type='text'
          required
          disabled
          value={basicInfo.memberEmail}
          classContainer='flex-1'
        />
      </div>
      <div className='mb-4'>
        <RadioGroup
          label='청구서 발송 수단'
          name='invoiceSendMethod'
          options={invoiceSendMethods}
          selectedOption={billingInfo.invoiceSendMethod}
          onChange={value => handleChangeValue(value, 'invoiceSendMethod')}
          required={true}
          classLabel='ml-1 mb-3'
        />
      </div>
      <div className='mb-4'>
        <RadioGroup
          label='청구 자동 생성'
          name='autoInvoiceSend'
          options={autoInvoiceSends}
          selectedOption={billingInfo.autoInvoiceSend}
          onChange={value => handleChangeValue(value, 'autoInvoiceSend')}
          required={true}
          classLabel='ml-1 mb-3'
        />
      </div>
      <div className='mb-4'>
        <RadioGroup
          label='청구서 자동 발송'
          name='autoBilling'
          options={autoBillings}
          selectedOption={billingInfo.autoBilling}
          onChange={value => handleChangeValue(value, 'autoBilling')}
          required={true}
          classLabel='ml-1 mb-3'
        />
      </div>
    </div>
  );
};

export default BillingInfoForm;
