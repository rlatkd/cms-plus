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

const PaymentAccountPage = () => {
  const start = 0;
  const end = 6;
  const status = useStatusStore(state => state.status);
  const { handleClickPrevious, handleClickNext } = useStatusStepper('account', start, end);
  const invoiceInfo = useInvoiceStore(state => state.invoiceInfo);

  const componentMap = {
    3: () => <ChooseBank billingInfo={invoiceInfo} />, //은행선택
    4: AccountInfo, // 계좌정보 입력
    5: () => <Loading content={'결제중...'} />, // 결제로딩
    6: Success, // 입금완료
  };

  const Content = componentMap[status] || (() => 'error');

  console.log('[주스탄드 상태]: ', invoiceInfo);
  console.log('주스탄드 청구ID', invoiceInfo.billingId);

  // 현재 계좌번호를 가져와서 주스탄드에 저장하는게 구현 안 되어있음
  const number = '56293456234294'; // 이건 AccountInfo에서 입력하고 주스탄드에 저장하고 주스탄드에서 가져와야함
  const method = 'ACCOUNT'; // 이건 안 건드려도 됨

  const paymentData = {
    billingId: invoiceInfo.billingId,
    method: method,
    number: number, // 여기서 써야되는데
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
      <Content />
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
