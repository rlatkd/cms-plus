import InputWeb from '@/components/common/inputs/InputWeb';

const ConDetailPaymentCMS = ({ contractData }) => {
  const paymentMethodInfo = contractData.paymentMethodInfo;
  const paymentMethod = (paymentMethodInfo) ? paymentMethodInfo.paymentMethod : {title: '', code: ''};

  return (
    <>
      <div className='flex items-end mb-5 mt-3'>
        <InputWeb
          id='paymentType'
          label='결제수단'
          value={paymentMethod.title}
          type='text'
          classContainer='w-full mr-6'
          disabled={true}
        />
        <InputWeb
          id='accountNumber'
          label='계좌번호'
          value={paymentMethodInfo.accountNumber}
          type='text'
          classContainer='w-full'
          disabled={true}
        />
      </div>
      <div className='flex items-end mb-5 '>
      <InputWeb
          id='accountOwner'
          label='소유주명'
          value={paymentMethodInfo.accountOwner}
          type='text'
          classContainer='w-full mr-6'
          disabled={true}
        />
        <InputWeb
          id='accountOwnerBirth'
          label='생년월일'
          value={paymentMethodInfo.accountOwnerBirth}
          type='text'
          classContainer='w-full'
          disabled={true}
        />
      </div>
    </>
  );
};

export default ConDetailPaymentCMS;
