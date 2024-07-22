import InputWeb from "@/components/common/inputs/InputWeb";

const BillingDetailPayment = ({ billingData }) => {
  return (
    <>
      <p className='text-text_black text-xl font-800'>결제정보</p>
      <div className='grid grid-cols-4 gap-6 my-8'>
        <InputWeb 
            id='contractId' 
            label='계약번호' 
            value={String(billingData.contractId).padStart(8, '0')}
            type='text' 
            disabled={true} 
        />
        <InputWeb 
            id='paymentType' 
            label='결제방식' 
            value={billingData.paymentType.title}
            type='text' 
            disabled={true} 
        />
        <InputWeb
          id='paymentMethod'
          label='결제수단'
          value={billingData.paymentMethod ? billingData.paymentMethod.title : '-'}
          type='text'
          disabled={true}
        />
      </div>
    </>
  );
};

export default BillingDetailPayment;
