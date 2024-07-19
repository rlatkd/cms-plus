import InputWeb from "@/components/common/inputs/InputWeb";
import TextArea from "@/components/common/inputs/TextArea";

const BillingDetailBilling = () => {
  return (
    <div className='flex flex-col border-ipt_border mx-4'>
      <p className='text-text_black text-xl font-800'>청구정보</p>
      <div className='grid grid-cols-3 gap-4 my-5'>
        <InputWeb 
            id='billingId' 
            label='청구번호' 
            value='00111' 
            type='text' 
            disabled={true} 
        />
        <InputWeb
            id='invoiceName' 
            label='청구서명' 
            value='2024년 07월 청구서' 
            type='text' 
            disabled={true} 
        />
        <InputWeb 
            id='createdDateTime' 
            label='청구생성일' 
            value='2024-07-01' 
            type='text' 
            disabled={true} 
        />
      </div>
      <div className='grid grid-cols-3 gap-4 my-5'>
        <InputWeb
            id='billingDate' 
            label='결제일' 
            value='2024-01-11' 
            type='text' 
            disabled={true} 
        />
        <InputWeb 
            id='billingStatus' 
            label='수납상태' 
            value='완납' 
            type='text' 
            disabled={true} 
        />
        <InputWeb 
            id='billingType' 
            label='청구타입' 
            value='추가청구' 
            type='text' 
            disabled={true} 
        />
      </div>
      <TextArea 
        id='billingMemo'
        label='청구서 메시지'
        value='감사합니다.'
        disabled={true}
        classContainer='my-5 w-1/2'
        classTextarea='w-1/2 h-48'
      />
    </div>
  );
};

export default BillingDetailBilling;
