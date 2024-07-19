import InputWeb from "@/components/common/inputs/InputWeb";

const BillingDetailPayment = () => {
  return (
    <div className='flex flex-col border-b border-ipt_border my-5 mt-8 mx-4'>
      <p className='text-text_black text-xl font-800'>결제정보</p>
      <div className='grid grid-cols-3 gap-4 my-8'>
        <InputWeb 
            id='contractId' 
            label='계약번호' 
            value='00111' 
            type='text' 
            disabled={true} 
        />
        <InputWeb 
            id='paymentType' 
            label='결제방식' 
            value='자동결제' 
            type='text' 
            disabled={true} 
        />
        <InputWeb
          id='paymentMethod'
          label='결제수단'
          value='카드'
          type='text'
          disabled={true}
        />
      </div>
    </div>
  );
};

export default BillingDetailPayment;
