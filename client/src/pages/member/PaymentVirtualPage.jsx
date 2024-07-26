import CheckVirtual from '@/components/member/virtualAccount/CheckVirtual';
import Loading from '@/components/common/member/Loading';
import Success from '@/components/common/member/Success';

import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import { useStatusStore } from '@/stores/useStatusStore';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { requestVirtualAccountPayment } from '@/apis/payment';

const PaymentVirtualPage = () => {
  const start = 0;
  const end = 4;
  const status = useStatusStore(state => state.status);
  const { handleClickPrevious, handleClickNext } = useStatusStepper('virtual', start, end);
  const invoiceInfo = useInvoiceStore(state => state.invoiceInfo);

  const componentMap = {
    2: CheckVirtual, // 가상계좌 정보
    3: () => <Loading content={'결제중...'} />, // 결제로딩 대충 로딩하다가 success로 가도록 해야됨. 결제결과는 문자로 날라감
    4: Success, // 입금완료
  };

  const Content = componentMap[status] || (() => 'error');

  const number = invoiceInfo.paymentType.accountNumber; //가상계좌번호
  const method = 'VIRTUAL';
  const phoneNumber = invoiceInfo.member.phone;

  const paymentData = {
    billingId: invoiceInfo.billingId,
    phoneNumber: phoneNumber,
    method: method,
    number: number,
  };

  const axiosVirtualAccountPayment = async () => {
    try {
      const res = await requestVirtualAccountPayment(paymentData);
      console.log(res.data);
    } catch (err) {
      console.error('axiosVirtualAccountPayment => ', err.response.data);
    }
  };

  return (
    <>
      <Content invoiceInfo={invoiceInfo} />
      <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
        <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
        <NextButton
          onClick={handleClickNext}
          type={'invoice'}
          status={status}
          end={end}
          onPayment={axiosVirtualAccountPayment}
        />
      </div>
    </>
  );
};

export default PaymentVirtualPage;
