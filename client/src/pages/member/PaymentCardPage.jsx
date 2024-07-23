import CardInfo from '@/components/member/card/CardInfo';
import ChooseCard from '@/components/member/card/ChooseCard';
import Loading from '@/components/common/member/Loading';
import Success from '@/components/common/member/Success';

import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import useStatusStepper from '@/hooks/useStatusStepper';
import { useStatusStore } from '@/stores/useStatusStore';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { requestCardPayment } from '@/apis/payment';
import { useState } from 'react';

const PaymentCardPage = () => {
  const start = 0;
  const end = 6;
  const status = useStatusStore(state => state.status);
  const { handleClickPrevious, handleClickNext } = useStatusStepper('card', start, end);
  const invoiceInfo = useInvoiceStore(state => state.invoiceInfo);

  const componentMap = {
    3: ChooseCard, //카드사선택
    4: CardInfo, // 카드정보 입력
    5: () => <Loading content={'결제중...'} />, // 결제로딩 대충 로딩하다가 success로 가도록 해야됨. 결제결과는 문자로 날라감
    6: Success, // 입금완료
  };

  const Content = componentMap[status] || (() => 'error');

  console.log('[주스탄드 상태]: ', invoiceInfo);
  console.log('주스탄드 청구ID', invoiceInfo.billingId);

  // 현재 카드번호를 가져와서 주스탄드에 저장하는게 구현 안 되어있음
  const number = '56293456234294'; // 이건 CardInfo에서 입력하고 주스탄드에 저장하고 주스탄드에서 가져와야함
  const method = 'CARD'; // 이건 안 건드려도 됨
  const phoneNumber = '01026270378'; // 테스트용 (실제로는 주스탄드에서 가져옴)

  const paymentData = {
    billingId: invoiceInfo.billingId,
    phoneNumber: phoneNumber,
    method: method,
    number: number,
  };

  const axiosCardPayment = async () => {
    try {
      const res = await requestCardPayment(paymentData);
      console.log(res.data);
    } catch (err) {
      console.error('axiosVirtualAccountPayment => ', err.response.data);
    }
  };

  return (
    <>
      <Content />
      <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
        <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
        <NextButton
          onClick={handleClickNext}
          type={'card'}
          status={status}
          end={end}
          onPayment={axiosCardPayment}
        />
      </div>
    </>
  );
};

export default PaymentCardPage;
