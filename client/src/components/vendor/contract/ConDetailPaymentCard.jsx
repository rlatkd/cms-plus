import InputWeb from '@/components/common/inputs/InputWeb';

const ConDetailPaymentCard = ({ contractData }) => {
  const paymentMethodInfo = contractData.paymentMethodInfo;
  const paymentMethod = (paymentMethodInfo) ? paymentMethodInfo.paymentMethod : '';
  const paymentMethodCode = (paymentMethod) ? paymentMethod.code : '';
  console.log(paymentMethod);

  return (
    <>
      <div className='flex items-end mb-5 mt-3'>
        <InputWeb
          id='paymentType'
          label='결제수단'
          placeholder={paymentMethod.title}
          type='text'
          classContainer='w-full mr-6'
          disabled={true}
        />
        <InputWeb
          id='contractDay'
          label='카드번호'
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

export default ConDetailPaymentCard;
