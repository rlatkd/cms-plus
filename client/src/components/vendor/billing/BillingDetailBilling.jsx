import InputWeb from "@/components/common/inputs/InputWeb";
import TextArea from "@/components/common/inputs/TextArea";
import { formatId } from "@/utils/formatId";

const BillingDetailBilling = ({ billingData, billingReq, editable, onChange: onBillingMemoChange, onBillingDateChange }) => {
  return (
    <>
      <p className='text-text_black text-xl font-800'>청구정보</p>
      <div className='grid grid-cols-4 gap-6 my-5'>
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
        {/* TODO 달력 */}
        <InputWeb
            id='billingDate' 
            label='결제일' 
            value={billingReq.billingDate}
            type='text' 
            disabled={!editable}
            onChange={e => onBillingDateChange(e.target.value)} 
        />
      </div>
      <div className='grid grid-cols-4 gap-6 my-5'>
        <InputWeb 
            id='billingStatus' 
            label='수납상태' 
            value={billingData.billingStatus.title}
            type='text' 
            disabled={true} 
        />
        <InputWeb 
            id='billingType' 
            label='청구타입' 
            value={billingData.billingType.title} 
            type='text' 
            disabled={true} 
        />
      </div>
      <TextArea 
        id='billingMemo'
        label='청구서 메시지'
        value={billingReq.billingMemo}
        disabled={!editable}
        classContainer='my-5 w-1/2'
        classTextarea='w-1/2 h-48'
        onChange={e => onBillingMemoChange(e.target.value)}
      />
    </>
  );
};

export default BillingDetailBilling;
