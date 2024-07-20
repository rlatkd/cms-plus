import SelectField from '@/components/common/selects/SelectField';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';

const cardOptions = [
  { value: '', label: '은행을 선택해주세요' },
  { value: 'KOOKMIN', label: '국민은행' },
  { value: 'SHINHAN', label: '신한은행' },
];

const VirtualAccountTypeForm = ({ paymentType }) => {
  const { paymentTypeInfoReq_Virtual, setPaymentTypeInfoReq_Virtual } = useMemberPaymentStore();

  const handleChangeSelect = e => {
    setPaymentTypeInfoReq_Virtual({ bank: e.target.value });
  };

  const handleChangeInput = e => {
    const { id, value } = e.target;
    setPaymentTypeInfoReq_Virtual({ [id]: value });
    console.log(paymentTypeInfoReq_Virtual);
  };

  return (
    <div className='flex'>
      <SelectField
        label='은행'
        classContainer='mr-5'
        classLabel='text-15 text-text_black font-700'
        classSelect='py-3 pr-20 p-4 rounded-lg'
        options={cardOptions}
        value={paymentTypeInfoReq_Virtual.bank}
        onChange={handleChangeSelect}
      />
      <InputWeb
        id='accountOwner'
        label='예금주'
        placeholder='최대 20자리'
        type='text'
        classInput='py-3 pr-20'
        value={paymentTypeInfoReq_Virtual.accountOwner}
        onChange={handleChangeInput}
      />
    </div>
  );
};

export default VirtualAccountTypeForm;
