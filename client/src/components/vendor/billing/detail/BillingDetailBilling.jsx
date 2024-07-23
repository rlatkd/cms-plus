import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendar } from '@fortawesome/free-solid-svg-icons';
import DatePicker from '@/components/common/inputs/DatePicker';
import InputWeb from '@/components/common/inputs/InputWeb';
import TextArea from '@/components/common/inputs/TextArea';
import { formatDate } from '@/utils/format/formatDate';
import { formatId } from '@/utils/format/formatId';

const BillingDetailBilling = ({
  billingData,
  billingReq,
  editable,
  onBillingMemoChange,
  onBillingDateChange,
}) => {
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);

  const makeStatusText = billingData => {
    const { billingStatus, billingCreatedDate, invoiceSendDateTime, paidDateTime } = billingData;
    switch (billingStatus.code) {
      case 'CREATED':
        return `${billingStatus.title} (${billingCreatedDate})`;
      case 'WAITING_PAYMENT':
      case 'NON-PAID':
        return `${billingStatus.title} (청구서 발송: ${invoiceSendDateTime || '미발송'})`;
      case 'PAID':
        return `${billingStatus.title} (결제: ${paidDateTime})`;
      default:
        return billingStatus.title;
    }
  };

  const handleDateChange = date => {
    onBillingDateChange(formatDate(date));
    setIsCalendarOpen(false);
  };

  return (
    <>
      <p className='text-text_black text-xl font-800'>청구정보</p>
      <div className='grid grid-cols-4 gap-6 my-8'>
        <InputWeb
          id='billingId'
          label='청구번호'
          value={formatId(billingData.billingId)}
          type='text'
          disabled={true}
        />
        <InputWeb
          id='invoiceName'
          label='청구서명'
          value={billingData.billingName}
          type='text'
          disabled={true}
        />

        <InputWeb
          id='billingStatus'
          label='청구 상태'
          value={makeStatusText(billingData)}
          readOnly
          disabled={true}
        />
      </div>

      <div className='grid grid-cols-4 gap-6 my-5'>
        <InputWeb
          id='billingType'
          label='청구타입'
          value={billingData.billingType.title}
          type='text'
          disabled={true}
        />
        <InputWeb
          id='billingCreatedDateTime'
          label='청구 생성일'
          value={billingData.billingCreatedDate}
          type='text'
          disabled={true}
        />
        <div className='relative flex items-cente'>
          <DatePicker
            selectedDate={new Date(billingReq.billingDate)}
            onDateChange={handleDateChange}
            isOpen={isCalendarOpen}
            onToggle={() => setIsCalendarOpen(!isCalendarOpen)}
          />
          <InputWeb
            id='billingDate'
            label='결제일'
            value={billingReq.billingDate}
            type='text'
            disabled={!editable}
            readOnly={true}
            onChange={e => onBillingDateChange(e.target.value)}
            classInput='w-full'
            classContainer='w-full'
          />
          {editable && (
            <div
              className='cursor-pointer absolute right-3 top-11'
              onClick={() => setIsCalendarOpen(!isCalendarOpen)}>
              <FontAwesomeIcon icon={faCalendar} className='h-5 w-5 text-gray-400' />
            </div>
          )}
        </div>
      </div>

      <TextArea
        id='billingMemo'
        label='청구서 메시지'
        value={billingReq.billingMemo}
        onChange={e => onBillingMemoChange(e.target.value)}
        disabled={!editable}
        classContainer='my-5 w-1/2'
        classTextarea='w-1/2 h-48'
      />
    </>
  );
};

export default BillingDetailBilling;
