import Input from '@/components/common/Input';

const PaymentCard = () => {
  return (
    <div flex flex-col p-1 bg-white>
      <form className='space-y-4'>
        <Input
          label='카드번호'
          name='cardNumber'
          type='text'
          required
          placeholder="'-' 없이 숫자만 입력해주세요"
        />
        <Input label='유효기간' name='expiryDate' type='text' required placeholder='MM/YY' />
        <Input label='명의자' name='cardHolder' type='text' required placeholder='최대 15자리' />
        <Input
          label='생년월일'
          name='birthDate'
          type='text'
          required
          placeholder='생년월일 6자리'
        />
        <button className='mt-4 w-full rounded-lg border border-teal-400 bg-white py-2.5 text-sm font-normal text-teal-400 transition-colors hover:bg-teal-50'>
          카드 인증하기
        </button>
      </form>
    </div>
  );
};

export default PaymentCard;
