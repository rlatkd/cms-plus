import InputWeb from '@/components/common/inputs/InputWeb';
import { formatPhone } from '@/utils/formatPhone';

const BillingDetailMember = ({ billingData }) => {
  return (
    <>
      <p className='text-text_black text-xl font-800'>회원정보</p>
      <div className='grid grid-cols-4 gap-6 my-8'>
        <InputWeb
          id='memberId'
          label='회원번호'
          value={String(billingData.memberId).padStart(8, '0')}
          type='text'
          disabled={true}
        />
        <InputWeb
          id='memberName'
          label='회원명'
          value={billingData.memberName}
          type='text'
          disabled={true}
        />
        <InputWeb
          id='memberPhone'
          label='휴대전화'
          value={formatPhone(billingData.memberPhone)}
          type='text'
          disabled={true}
        />
      </div>
    </>
  );
};

export default BillingDetailMember;
