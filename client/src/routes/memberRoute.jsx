import { Navigate } from 'react-router-dom';
import SimpConsent from '@/pages/member/SimpConsentPage'; // 간편동의 등록
import Invoice from '@/pages/member/InvoicePage'; // 청구서 상세조회
import PaymentChoose from '@/pages/member/PaymentChoosePage'; // 청구서 결제수단 선택
import PaymentCard from '@/pages/member/PaymentCardPage'; // 납부자결제 (카드)
import PaymentAccount from '@/pages/member/PaymentAccountPage'; // 납부자결제 (계좌)
import PaymentVirtual from '@/pages/member/PaymentVirtualPage'; // 가상계좌결제

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
      path: 'invoice/payment',
      element: <PaymentChoose />,
    },
    {
      path: 'invoice/payment/card',
      element: <PaymentCard />,
    },
    {
      path: 'invoice/payment/account',
      element: <PaymentAccount />,
    },
    {
      path: 'invoice/payment/virtual',
      element: <PaymentVirtual />,
    },
  ];
};

export default memberRoute;
