import { useStatusStore } from '@/stores/useStatusStore';
import { useNavigate } from 'react-router-dom';

const useStatusStepper = (type, start, end) => {
  const status = useStatusStore(state => state.status);
  const increment = useStatusStore(state => state.increment);
  const decrement = useStatusStore(state => state.decrement);
  const reset = useStatusStore(state => state.reset);
  const navigate = useNavigate();

  const handleClickPrevious = () => {
    if (status > start) {
      decrement();
    }
    if (type === 'virtual' && status === 2) {
      navigate(-1);
      return;
    } else if ((type === 'card' || type === 'account') && (status === 2 || status === 3)) {
      navigate(-1);
      return;
    }
  };

  const handleClickNext = () => {
    if (status < end) {
      increment();
    } else if (status === end) {
      reset();
      // 삭제 예정
      if (type !== 'simpconsent') {
        navigate('/member/invoice/3');
      }
    }
    switch (type) {
      case 'payment':
        if (status === 1) navigate('/member/invoice/payment');
        break;
      case 'virtual':
        if (status === 1) navigate('/member/invoice/payment/virtual');
        break;
      case 'card':
        if (status === 2) navigate('/member/invoice/payment/card');
        break;
      case 'account':
        if (status === 2) navigate('/member/invoice/payment/account');
        break;
      case 'memberregister':
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
