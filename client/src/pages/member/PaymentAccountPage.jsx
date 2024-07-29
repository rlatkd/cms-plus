import ChooseBank from '@/components/member/account/ChooseBank';
import AccountInfo from '@/components/member/account/AccountInfo';
import Loading from '@/components/common/member/Loading';
import Success from '@/components/common/member/Success';

import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import { useStatusStore } from '@/stores/useStatusStore';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { requestAccountPayment } from '@/apis/payment';
import { useState } from 'react';

const PaymentAccountPage = () => {
  const start = 0;
  const end = 6;
  const status = useStatusStore(state => state.status);
  const { handleClickPrevious, handleClickNext } = useStatusStepper('account', start, end);
  const invoiceInfo = useInvoiceStore(state => state.invoiceInfo);

  const [accountInfo, setAccountInfo] = useState({
    accountNumber: '',
    accountOwner: '',
    accountOwnerBirth: '',
  });

  const componentMap = {
    3: () => <ChooseBank billingInfo={invoiceInfo} />, //은행선택
    4: AccountInfo, // 계좌정보 입력
    5: () => <Loading content={'결제중...'} />, // 결제로딩 대충 로딩하다가 success로 가도록 해야됨. 결제결과는 문자로 날라감
    6: Success, // 입금완료
  };

  const Content = componentMap[status] || (() => 'error');

  const number = accountInfo.accountNumber; //계좌번호
  const method = 'ACCOUNT';
  const phoneNumber = invoiceInfo.member.phone;

  const paymentData = {
    billingId: invoiceInfo.billingId,
    phoneNumber: phoneNumber,
    method: method,
    number: number,
  };

  const axiosAccountPayment = async () => {
    try {
      const res = await requestAccountPayment(paymentData);
      console.log(res.data);
    } catch (err) {
      console.error('axiosVirtualAccountPayment => ', err.response);
    }
  };

  return (
    <>
      <Content accountInfo={accountInfo} setAccountInfo={setAccountInfo} />
      <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
        <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
        <NextButton
          onClick={handleClickNext}
          type={'account'}
          status={status}
          end={end}
          onPayment={axiosAccountPayment}
        />
      </div>
    </>
  );
};

export default PaymentAccountPage;
