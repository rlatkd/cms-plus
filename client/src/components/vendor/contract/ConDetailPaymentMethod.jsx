import InputWeb from '@/components/common/inputs/InputWeb';
import ConDetailPaymentCard from './ConDetailPaymentCard';
import ConDetailPaymentCMS from './ConDetailPaymentCMS';
import ConDetailPaymentVirtual from './ConDetailPaymentVirtual';

const ConDetailPaymentMethod = ({ contractData }) => {

  const paymentType = contractData.paymentTypeInfo.paymentType.code;
  console.log(paymentType);

  const componentMap = {
    AUTO: () => {
      const paymentMethodInfo = contractData.paymentMethodInfo;
      const paymentMethod = (paymentMethodInfo) ? paymentMethodInfo.paymentMethod : '';
      const paymentMethodCode = (paymentMethod) ? paymentMethod.code : '';
      if (paymentMethodCode === 'CARD') {
        return () => <ConDetailPaymentCard contractData={contractData}/>
      } else if (paymentMethodCode === 'CMS') {
        return () => <ConDetailPaymentCMS contractData={contractData}/>
      } else {
        //TODO
        //간편동의 요청만 보낸 계약
        //결제수단 정해지지 않음
      }
    },
    VIRTUAL: () => <ConDetailPaymentVirtual contractData={contractData} />
  };

  const Content = componentMap[paymentType] || (() => 'error');

  return (
    <div className='flex-col p-5'>
      <div className='flex flex-col justify-between flex-1'>
        <Content />
      </div>
    </div>
  );
};

export default ConDetailPaymentMethod;
