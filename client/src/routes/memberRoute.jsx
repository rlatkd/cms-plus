import { Navigate } from 'react-router-dom';
import SimpConsent from '@/pages/member/SimpConsentPage'; // 간편동의 등록
import Invoice from '@/pages/member/InvoicePage'; // 청구서 상세조회
import PaymentChoose from '@/pages/member/PaymentChoosePage'; // 청구서 결제수단 선택
import PaymentCard from '@/pages/member/PaymentCardPage'; // 납부자결제 (카드)
import PaymentAccount from '@/pages/member/PaymentAccountPage'; // 납부자결제 (계좌)
import PaymentVirtual from '@/pages/member/PaymentVirtualPage'; // 가상계좌결제
import PaymentAuto from '@/pages/member/PaymentAutoPage'; //자동결제
import Complete from '@/components/common/member/Complete';

const memberRoute = () => {
  return [
    {
      path: '',
      element: <Navigate replace to='simpconsent' />,
    },
    {
      path: 'simpconsent/:memberid?',
      element: <SimpConsent />,
    },
    {
      path: 'invoice/:invoiceId',
      element: <Invoice />,
    },
    {
      path: 'invoice/:invoiceId/payment',
      element: <PaymentChoose />,
    },
    {
      path: 'invoice/:invoiceId/payment/card',
      element: <PaymentCard />,
    },
    {
      path: 'invoice/:invoiceId/payment/account',
      element: <PaymentAccount />,
    },
    {
      path: 'invoice/:invoiceId/payment/virtual',
      element: <PaymentVirtual />,
    },
    {
      path: 'invoice/:invoiceId/payment/auto',
      element: <PaymentAuto />,
    },
    {
      path: 'invoice/:invoiceId/payment/complete',
      element: <Complete />,
    },
  ];
};

export default memberRoute;
