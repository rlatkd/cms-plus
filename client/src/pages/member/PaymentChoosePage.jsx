import { useStatusStore } from '@/stores/useStatusStore';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useEffect, useState } from 'react';
import PreviousButton from '@/components/common/buttons/StatusPreButton';
import NextButton from '@/components/common/buttons/StatusNextButton';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { useNavigate, useParams } from 'react-router-dom';

const PaymentChoosePage = () => {
  const start = 0;
  const end = 3;
  const { invoiceId } = useParams();
  const { status, reset, setStatus } = useStatusStore();
  const [paymentType, setPaymentType] = useState('card');
  const { handleClickPrevious, handleClickNext } = useStatusStepper(paymentType, start, end);
  const { invoiceInfo } = useInvoiceStore();
  const navigate = useNavigate();

  const [availableMethods, setAvailableMethods] = useState([]);
  const [isCardAvailable, setIsCardAvailable] = useState(false);
  const [isAccountAvailable, setIsAccountAvailable] = useState(false);

  useEffect(() => {
    if (invoiceInfo) {
      const methods = invoiceInfo.paymentType.availableMethods;
      setAvailableMethods(methods);

      //사용 가능한 결제 방법 확인
      const cardAvail = methods.some(method => method.code === 'CARD');
      const accountAvail = methods.some(method => method.code === 'ACCOUNT');

      setIsCardAvailable(cardAvail);
      setIsAccountAvailable(accountAvail);

      if (cardAvail && !accountAvail) {
        setPaymentType('card');
      } else if (!cardAvail && accountAvail) {
        setPaymentType('account');
      }
    } else {
      reset();
      navigate(`/member/invoice/${invoiceId}`);
    }
  }, []);

  // 결제 방법이 카드와 계좌중에 하나만 있다면 카드, 계좌 중 없는 건 비활성화
  return (
    <div className='flex flex-col h-full'>
      <div className='flex-grow'>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          결제수단을
          <br />
          선택해주세요.
        </h3>
        <div className='flex gap-4 mt-4'>
          <label className={`flex-1 ${!isCardAvailable ? 'opacity-50 cursor-not-allowed' : ''}`}>
            <input
              type='radio'
              name='paymentType'
              value='card'
              className='sr-only'
              checked={paymentType === 'card'}
              onChange={() => setPaymentType('card')}
              disabled={!isCardAvailable}
            />
            <div
              className={`border rounded-lg p-4 h-24 flex items-center justify-center cursor-pointer ${paymentType === 'card' ? 'border-cyan-500 bg-cyan-50' : 'hover:border-gray-300'}`}>
              <p className={paymentType === 'card' ? 'text-cyan-500' : 'text-gray-500'}>카드</p>
            </div>
          </label>
          <label className={`flex-1 ${!isAccountAvailable ? 'opacity-50 cursor-not-allowed' : ''}`}>
            <input
              type='radio'
              name='paymentType'
              value='account'
              className='sr-only'
              checked={paymentType === 'account'}
              onChange={() => setPaymentType('account')}
              disabled={!isAccountAvailable}
            />
            <div
              className={`border rounded-lg p-4 h-24 flex items-center justify-center cursor-pointer ${paymentType === 'account' ? 'border-cyan-500 bg-cyan-50' : 'hover:border-gray-300'}`}>
              <p className={paymentType === 'account' ? 'text-cyan-500' : 'text-gray-500'}>계좌</p>
            </div>
          </label>
        </div>
      </div>

      <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
        <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
        <NextButton onClick={handleClickNext} type={'invoice'} status={status} end={end} />
      </div>
    </div>
  );
};

export default PaymentChoosePage;
