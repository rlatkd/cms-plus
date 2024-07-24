import InputWeb from '@/components/common/inputs/InputWeb';

const ConDetailPaymentType = ({ contractData }) => {
  return (
    <div className='flex-col p-5'>
      <div className='flex flex-col justify-between flex-1'>
        <div className='flex items-end mb-5 mt-3'>
          <InputWeb
            id='paymentType'
            label='결제방식'
            value={contractData.paymentTypeInfo.paymentType.title || ''}
            type='text'
            classContainer='w-full mr-6'
            disabled={true}
          />
          <InputWeb
            id='contractDay'
            label='약정일'
            value={contractData.contractDay || ''}
            type='text'
            classContainer='w-full'
            disabled={true}
          />
        </div>
        <div className='flex items-end mb-5 '>
          <InputWeb
            id='contractStartDate'
            label='계약기간'
            value={contractData.contractStartDate || ''}
            type='text'
            classContainer='w-full mr-3'
            disabled={true}
          />
          <InputWeb
            id='contractEndDate'
            label=''
            value={contractData.contractEndDate || ''}
            type='text'
            classContainer='w-full'
            disabled={true}
          />
        </div>
      </div>
    </div>
  );
};

export default ConDetailPaymentType;
