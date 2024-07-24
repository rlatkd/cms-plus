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
    3: { component: () => <ChooseBank billingInfo={invoiceInfo} /> }, //은행선택
    4: { component: AccountInfo }, // 계좌정보 입력
    5: { component: () => <Loading content={'결제중...'} /> }, // 결제로딩 대충 로딩하다가 success로 가도록 해야됨. 결제결과는 문자로 날라감
    6: { component: Success }, // 입금완료
  };

  const { component: Content } = componentMap[status] || {
    component: () => 'error',
  };

  //console.log('[주스탄드 상태]: ', invoiceInfo);
  //console.log('주스탄드 청구ID', invoiceInfo.billingId);

  // 현재 계좌번호를 가져와서 주스탄드에 저장하는게 구현 안 되어있음
  const number = '56293456234294'; // 이건 AccountInfo에서 입력하고 주스탄드에 저장하고 주스탄드에서 가져와야함
  const method = 'ACCOUNT'; // 이건 안 건드려도 됨
  const phoneNumber = '01026270378'; // 테스트용 (실제로는 주스탄드에서 가져옴)

  const paymentData = {
    billingId: invoiceInfo.billing.billingId,
    phoneNumber: phoneNumber,
    method: method,
    number: number,
  };

  const axiosAccountPayment = async () => {
    try {
      const res = await requestAccountPayment(paymentData);
      console.log(res.data);
    } catch (err) {
      console.error('axiosVirtualAccountPayment => ', err.response.data);
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
