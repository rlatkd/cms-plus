import { useStatusStore } from '@/stores/useStatusStore';
import { useNavigate } from 'react-router-dom';
import { useInvoiceStore } from '@/stores/useInvoiceStore';

const CASES = {
  BUYER: 'BUYER', // 납부자 청구서조회 페이지
  CARD: 'CARD', // 납부자 카드 결제 페이지
  ACCOUNT: 'ACCOUNT', // 납부자 계좌 결제 페이지
  VIRTUAL: 'VIRTUAL', // 납부자 가상계좌 페이지
  MEMBERREGISTER: 'MEMBERREGISTER', // 회원 등록 페이지
  AUTO: 'AUTO', //자동결제
};

const useStatusStepper = (type, start, end) => {
  const invoiceInfo = useInvoiceStore(state => state.invoiceInfo);

  const { status, increment, decrement, reset } = useStatusStore();

  const navigate = useNavigate();
  type = type.toUpperCase();

  const handleClickPrevious = () => {
    if (status > start) {
      decrement();
    }

    // 가상계좌 2p -> 1p
    if (type === CASES.VIRTUAL && status === 2) {
      navigate(-1);
      return;
    }
    // 카드, 계좌 3p -> 2p, 2p -> 1p
    if ((type === CASES.CARD || type === CASES.ACCOUNT) && (status === 2 || status === 3)) {
      navigate(-1);
      return;
    }
  };

  const handleClickNext = () => {
    if (status === 1 && invoiceInfo) {
      if (invoiceInfo.billingStatus === '완납') {
        //billingStatus가 '완납'일때 complete페이지로 이동
        navigate(`/member/invoice/${invoiceInfo.billingId}/payment/complete`);
        reset();
        return;
      }
      // '완납'이 아닐 경우, status 1에 대한 기존 로직 계속 진행
      if (type === CASES.BUYER) {
        navigate(`/member/invoice/${invoiceInfo.billingId}/payment`);
      }
    }
    if (status < end) {
      increment();
    }
    if (status === end) {
      reset();
      // // 삭제 예정
      // if (type !== 'simpconsent') {
      //   navigate('/member/invoice/3');
      // }
    }

    switch (type) {
      case CASES.BUYER:
        if (status === 1) navigate(`/member/invoice/${invoiceInfo.billingId}/payment`);
        break;
      case CASES.VIRTUAL:
        if (status === 1) navigate(`/member/invoice/${invoiceInfo.billingId}/payment/virtual`);
        break;
      case CASES.CARD:
        if (status === 2) navigate(`/member/invoice/${invoiceInfo.billingId}/payment/card`);
        break;
      case CASES.ACCOUNT:
        if (status === 2) navigate(`/member/invoice/${invoiceInfo.billingId}/payment/account`);
        break;
      case CASES.MEMBERREGISTER:
        if (status === 3) {
          reset();
          navigate('/vendor/members');
        }
        break;
      default:
        break;
    }
  };

  return {
    handleClickPrevious,
    handleClickNext,
  };
};

export default useStatusStepper;
