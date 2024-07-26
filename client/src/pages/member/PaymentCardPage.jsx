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

  const [cardInfo, setCardInfo] = useState({
    cardNumber: '',
    expiryDate: '',
    cardOwner: '',
    cardOwnerBirth: '',
  });

  const componentMap = {
    3: ChooseCard, //카드사 선택
    4: CardInfo, //카드정보 입력
    5: () => <Loading content={'결제중...'} />, //결제중
    6: Success, //입금완료
  };

  const Content = componentMap[status] || (() => 'error');

  const number = cardInfo.cardNumber; //카드번호
  const method = 'CARD';
  const phoneNumber = invoiceInfo.member.phone;

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
      <Content cardInfo={cardInfo} setCardInfo={setCardInfo} />
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
