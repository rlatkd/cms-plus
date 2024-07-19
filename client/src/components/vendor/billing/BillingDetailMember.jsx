import InputWeb from "@/components/common/inputs/InputWeb";

const BillingDetailMember = () => {
  return (
    <div className='flex flex-col border-b border-ipt_border my-5 mx-4'>
        <p className='text-text_black text-xl font-800'>회원정보</p>
        <div className="grid grid-cols-3 gap-4 my-8">
        <InputWeb
          id='memberId'
          label='회원번호'
          value='00111'
          type='text'
          disabled={true}
        />
        <InputWeb
          id='memberName'
          label='회원명'
          value='박민석'
          type='text'
          disabled={true}
        />
        <InputWeb
          id='memberPhone'
          label='휴대전화'
          value='010-1234-5678'
          type='text'
          disabled={true}
        />
      </div>
  </div>
  );
};

export default BillingDetailMember;
