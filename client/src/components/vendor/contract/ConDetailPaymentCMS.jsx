import InputWeb from '@/components/common/inputs/InputWeb';

const ConDetailPaymentCMS = ({ contractData }) => {

  const paymentType = contractData.paymentTypeInfo.paymentType.code;
  console.log(paymentType);

  return (
    <>
      <div className='flex items-end mb-5 mt-3'>
        <InputWeb
          id='paymentType'
          label='결제방식'
          placeholder=''
          type='text'
          classContainer='w-full mr-6'
          disabled={true}
        />
        <InputWeb
          id='contractDay'
          label='약정일'
          placeholder={contractData.contractDay}
          type='text'
          classContainer='w-full'
          disabled={true}
        />
      </div>
      <div className='flex items-end mb-5 '>
        <InputWeb
          id='contractStartDate'
          label='계약기간'
          placeholder={`${contractData.contractStartDate}`}
          type='text'
          classContainer='w-full mr-3'
          disabled={true}
        />
        <InputWeb
          id='contractEndDate'
          label=''
          placeholder={`${contractData.contractEndDate}`}
          type='text'
          classContainer='w-full'
          disabled={true}
        />
      </div>
    </>
  );
};

export default ConDetailPaymentCMS;
