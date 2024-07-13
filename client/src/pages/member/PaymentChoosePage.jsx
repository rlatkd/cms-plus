import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import { useStatusStore } from '@/stores/useStatusStore';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useState } from 'react';

const PaymentChoosePage = () => {
  const start = 0;
  const end = 3;
  const status = useStatusStore(state => state.status);
  const [paymentType, setPaymentType] = useState('card');
  const { handleClickPrevious, handleClickNext } = useStatusStepper(paymentType, start, end);

  return (
    <>
      <div className='flex justify-around'>
        <div
          className='border border-black p-8 hover:bg-mint'
          onClick={() => setPaymentType('card')}>
          카드
        </div>

        <div
          className='border border-black p-8 hover:bg-mint'
          onClick={() => setPaymentType('account')}>
          계좌
        </div>
      </div>

      <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
        <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
        <NextButton onClick={handleClickNext} type={'invoice'} status={status} end={end} />
      </div>
    </>
  );
};
export default PaymentChoosePage;
