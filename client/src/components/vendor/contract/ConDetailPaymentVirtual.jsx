import InputWeb from '@/components/common/inputs/InputWeb';

const ConDetailPaymentVirtual = ({ contractData }) => {
  const paymentTypeInfo = contractData.paymentTypeInfo;

  return (
    <>
      <div className='flex items-end mb-5 mt-3'>
        <InputWeb
          id='bank'
          label='은행'
          value={paymentTypeInfo.bank.title}
          type='text'
          classContainer='w-1/2'
          disabled={true}
        />
      </div>
      <div className='flex items-end mb-5 '>
        <InputWeb
          id='accountOwner'
          label='예금주'
          value={paymentTypeInfo.accountOwner}
          type='text'
          classContainer='w-full mr-3'
          disabled={true}
        />
        <InputWeb
          id='accountNumber'
          label='계좌번호'
          value={paymentTypeInfo.accountNumber}
          type='text'
          classContainer='w-full'
          disabled={true}
        />
      </div>
    </>
  );
};

export default ConDetailPaymentVirtual;
