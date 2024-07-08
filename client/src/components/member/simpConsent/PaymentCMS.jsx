import Input from '@/components/common/Input';
import SelectField from '@/components/common/SelectField';

const PaymentCMS = () => {
  const bankOptions = [
    { value: 'shinhan', label: '신한은행' },
    { value: 'kb', label: '국민은행' },
    { value: 'woori', label: '우리은행' },
    { value: 'woori', label: '기업은행' },
    { value: 'woori', label: '수협은행' },
    { value: 'woori', label: 'NH농협은행' },
    { value: 'woori', label: '부산은행' },
    { value: 'woori', label: '하나은행' },
    { value: 'woori', label: '광주은행' },
    { value: 'woori', label: '우체국' },
    { value: 'woori', label: 'iM뱅크' },
    { value: 'woori', label: '경남은행' },
  ];

  return (
    <div flex flex-col p-1 bg-white>
      <form className='space-y-4'>
        <SelectField label='은행' name='bank' required options={bankOptions} />
        <Input
          label='예금주명'
          name='accountHolder'
          type='text'
          required
          placeholder='최대 20자리'
        />
        <Input
          label='생년월일'
          name='birthDate'
          type='text'
          required
          placeholder='생년월일 6자리'
        />
        <Input
          label='계좌번호'
          name='accountNumber'
          type='text'
          required
          placeholder='최대 20자리'
        />
        <button className='mt-4 w-full rounded-lg border border-teal-400 bg-white py-2.5 text-sm font-normal text-teal-400 transition-colors hover:bg-teal-50'>
          계좌 인증하기
        </button>
      </form>
    </div>
  );
};

export default PaymentCMS;
