import InputWeb from '@/components/common/inputs/InputWeb';
import ConDetailPaymentCard from './ConDetailPaymentCard';
import ConDetailPaymentCMS from './ConDetailPaymentCMS';
import ConDetailPaymentVirtual from './ConDetailPaymentVirtual';

const ConDetailPaymentMethod = ({ contractData }) => {

  const paymentTypeInfo = contractData.paymentTypeInfo;
  const paymentType = paymentTypeInfo.paymentType.code;


  const componentMap = {
    AUTO: () => {
      const paymentMethodInfo = contractData.paymentMethodInfo;
      const paymentMethod = (paymentMethodInfo) ? paymentMethodInfo.paymentMethod : null;
      const paymentMethodCode = (paymentMethod) ? paymentMethod.code : '';
      
      let res;
      if (paymentMethodCode === 'CARD') {
        res = <ConDetailPaymentCard contractData={contractData}/>
      } else if (paymentMethodCode === 'CMS') {
        res = <ConDetailPaymentCMS contractData={contractData}/>
      }
      
      return (
        <>
          {res}
          <InputWeb
          id='simpConsentInfo'
          label='간편동의여부'
          value={`${paymentTypeInfo.consentStatus.title} (${paymentTypeInfo.simpleConsentReqDateTime})`}
          type='text'
          classContainer='w-1/2'
          disabled={true}
        />
        </>
      );
    },
    VIRTUAL: () => <ConDetailPaymentVirtual contractData={contractData} />
  };

  const Content = componentMap[paymentType] || (() => '');

  return (
    <div className='flex-col p-5'>
      <div className='flex flex-col justify-between flex-1'>
        <Content />
      </div>
    </div>
  );
};

export default ConDetailPaymentMethod;
