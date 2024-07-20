import edit from '@/assets/edit.svg';
import { Tooltip } from 'react-tooltip';

const BillingDetailButtons = ({
  onEdit,
  onRemove,
  onSend,
  onCancelSend,
  onPay,
  onCancelPay,

  canUpdate,
  canDelete,
  canPay,
  canSendInvoice,
  canCancelInvoice,
  canPayCanceled,

  invoiceSendTime,
  paidDateTime,
}) => {
  const buttonClass = (isDisabled) => `
    flex justify-between items-center px-4 py-2 ml-4 
    font-700 rounded-md border cursor-pointer
    ${isDisabled 
      ? 'text-gray-400 border-gray-400 bg-gray-100' 
      : 'text-mint border-mint'}
  `;

  const renderButton = (label, isDisabled, onClick, tooltipId, tooltipContent) => (
    <>
      <button
        className={`${buttonClass(isDisabled)} ${tooltipId}`}
        disabled={isDisabled}
        onClick={onClick}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>{label}</p>
      </button>
      {isDisabled && (
        <Tooltip anchorSelect={`.${tooltipId}`} place='top'>
          {tooltipContent}
        </Tooltip>
      )}
    </>
  );

  const buildReasonMsg = () => {

  };

  return (
    <>
      {renderButton('청구 수정', !canUpdate, onEdit, 'editTooltip', '청구 수정은 [생성, 수납대기] 상태에서만 가능합니다.')}
      {renderButton('청구 삭제', !canDelete, onRemove, 'deleteTooltip', '청구 삭제는 [생성, 수납대기] 상태에서만 가능합니다.')}

      {invoiceSendTime == null 
        ? renderButton('청구서 발송', !canSendInvoice, onSend, 'sendTooltip', '청구서는 [생성, 미납] 상태에서만 발송 가능합니다.')
        : renderButton('청구서 발송취소', !canCancelInvoice, onCancelSend, 'cancelSendTooltip', 
            `${invoiceSendTime} 청구서 발송완료\n완납상태에서는 발송 취소가 불가능합니다.`)}

      {paidDateTime == null 
        ? renderButton('실시간 결제', !canPay, onPay, 'payTooltip', '실시간 결제는 결제방식이 자동결제 & 상태가 [생성, 수납대기] 경우에만 가능합니다.')
        : renderButton('결제취소', !canPayCanceled, onCancelPay, 'cancelPayTooltip', 
            '결제 취소는 결제수단이 [카드, 납부자결제] 경우만 가능합니다.')}
    </>
  );
};

export default BillingDetailButtons;