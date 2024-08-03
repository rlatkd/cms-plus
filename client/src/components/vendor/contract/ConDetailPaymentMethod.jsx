import InputWeb from '@/components/common/inputs/InputWeb';
import ConDetailPaymentCard from './ConDetailPaymentCard';
import ConDetailPaymentCMS from './ConDetailPaymentCMS';
import ConDetailPaymentVirtual from './ConDetailPaymentVirtual';
import formatDateTime from '@/utils/format/formatDateTime';

const ConDetailPaymentMethod = ({ contractData }) => {
  const paymentTypeInfo = contractData?.paymentTypeInfo ?? {};
  const paymentType = paymentTypeInfo?.paymentType?.code ?? '';

  const componentMap = {
    AUTO: () => {
      const paymentMethodInfo = contractData?.paymentMethodInfo ?? {};
      const paymentMethodCode = paymentMethodInfo?.paymentMethod?.code ?? '';

      let PaymentComponent = null;
      if (paymentMethodCode === 'CARD') {
        PaymentComponent = ConDetailPaymentCard;
      } else if (paymentMethodCode === 'CMS') {
        PaymentComponent = ConDetailPaymentCMS;
      }

      return (
        <>
          {PaymentComponent && <PaymentComponent contractData={contractData} />}
          <InputWeb
            id='simpConsentInfo'
            label='동의여부'
            value={
              paymentTypeInfo.consentStatus?.title && paymentTypeInfo.simpleConsentReqDateTime
                ? `${paymentTypeInfo.consentStatus.title} (${formatDateTime(paymentTypeInfo.simpleConsentReqDateTime)})`
                : '정보 없음'
            }
            type='text'
            classContainer='w-1/2 pr-3'
            disabled={true}
          />
        </>
      );
    },
    VIRTUAL: () => <ConDetailPaymentVirtual contractData={contractData} />,
  };

  const Content = componentMap[paymentType] ?? (() => null);

  return (
    <div className='flex-col p-4'>
      <div className='flex flex-col justify-between flex-1'>
        <Content />
      </div>
    </div>
  );
};

export default ConDetailPaymentMethod;
