import Main from '@/components/member/invoice/Main';
import CheckInvoice from '@/components/member/invoice/CheckInvoice';

import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import useStatusStepper from '@/hooks/useStatusStepper';
import { useStatusStore } from '@/stores/useStatusStore';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { getBillingDetail } from '@/apis/billing';

export const convertToProductSummary = (products) => {
  if (!products || products.length < 1) {
    return '-';
  }

  const firstProductName = products[0].name;
  return firstProductName + '' + ((products.length === 1) ? '' : `외 ${products.length - 1}`);
}

const InvoicePage = () => {
  const start = 0;
  const end = 2;
  const { invoiceId } = useParams();
  const setInvoiceInfo = useInvoiceStore((state) => state.setInvoiceInfo);
  const invoiceInfo = useInvoiceStore((state) => state.invoiceInfo);
  const status = useStatusStore(state => state.status);
  const [paymentType, setPaymentType] = useState('BUYER');
  const { handleClickPrevious, handleClickNext } = useStatusStepper(paymentType, start, end);


  useEffect(() => {
    const fetchInvoiceInfo = async (billingId) => {
      const res = await getBillingDetail(billingId);
      console.log(res.data);
      setInvoiceInfo(res.data);
      setPaymentType(res.data.paymentType.code);
    }

    fetchInvoiceInfo(invoiceId);
  }, []);

  const componentMap = {
    0: Main, // 시작화면
    1: CheckInvoice, // 청구서 확인
  };

  const Content = componentMap[status] || (() => 'error');

  return (
    <>
      {/* 실제 로직에서는 버튼이 아닌 실제로 받아온 데이터를 통해서 결제방식을 확인 
          청구서 결제방식이 판별해서 paymenttype useState에 할당하자*/}
      {status === 1 ? (
        <div className='flex justify-around'>
          <div
            className='border border-black hover:bg-mint'
            onClick={() => setPaymentType('payment')}>
            임의 납부자결제
          </div>

          <div
            className='border border-black hover:bg-mint'
            onClick={() => setPaymentType('virtual')}>
            임의 가상계좌
          </div>
        </div>
      ) : (
        ''
      )}
      {invoiceInfo ? <Content /> : <></>}
      <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
        <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
        <NextButton onClick={handleClickNext} type={'invoice'} status={status} end={end} />
      </div>
    </>
  );
};

export default InvoicePage;
