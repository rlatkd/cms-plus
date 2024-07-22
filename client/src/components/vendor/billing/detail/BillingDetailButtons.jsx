import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faPenToSquare, 
  faTrashAlt, 
  faPaperPlane, 
  faBan, 
  faCreditCard 
} from '@fortawesome/free-solid-svg-icons';
import { Tooltip } from 'react-tooltip';
import 'react-tooltip/dist/react-tooltip.css';

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
  const buttonClass = (isDisabled, isCancel = false) => `
    flex justify-between items-center px-4 py-2 ml-2 
    font-700 rounded-md border cursor-pointer transition-all duration-200 ease-in-out
    ${isDisabled 
      ? 'text-gray-400 border-gray-400 bg-gray-100 cursor-not-allowed' 
      : isCancel
        ? 'text-red-500 border-red-500 hover:bg-red-50'
        : 'text-white bg-mint hover:bg-mint_hover'}
  `;

  const renderButton = (
    label,
    isDisabled,
    onClick,
    tooltipId,
    tooltipContent,
    alwaysShowTooltip = false,
    icon,
    isCancel = false
  ) => (
    <div className='relative inline-block'>
      <button
        className={`${buttonClass(isDisabled, isCancel)} ${tooltipId}`}
        disabled={isDisabled}
        onClick={onClick}
        data-tooltip-id={tooltipId}>
        <FontAwesomeIcon icon={icon} className='mr-2' />
        <p>{label}</p>
      </button>
      {alwaysShowTooltip && (
        <Tooltip id={tooltipId} place='top' variant='dark' className='tooltip'>
          <div className='p-2'>{tooltipContent}</div>
        </Tooltip>
      )}
    </div>
  );

  return (
    <div className="flex flex-wrap gap-2">
      {renderButton('청구 수정', !canUpdate, onEdit, 'editTooltip', '청구 수정은 [생성, 수납대기] 상태에서만 가능합니다.', !canUpdate, faPenToSquare)}
      {renderButton('청구 삭제', !canDelete, onRemove, 'deleteTooltip', '청구 삭제는 [생성, 수납대기] 상태에서만 가능합니다.', !canDelete, faTrashAlt)}

      {invoiceSendTime == null 
        ? renderButton('청구서 발송', !canSendInvoice, onSend, 'sendTooltip', '청구서 발송은 [생성, 미납] 상태에서만 가능합니다.', !canSendInvoice, faPaperPlane)
        : renderButton('청구서 발송취소', !canCancelInvoice, onCancelSend, 'cancelSendTooltip', 
            <div>
              <p>청구서 발송 시간: {invoiceSendTime}</p>
              {!canCancelInvoice && <p className="text-red-300 mt-1">완납상태에서는 발송 취소가 불가능합니다.</p>}
            </div>, true, faBan, true)}

      {paidDateTime == null 
        ? renderButton('실시간 결제', !canPay, onPay, 'payTooltip', '실시간 결제는 결제방식이 자동결제 & 상태가 [생성, 수납대기] 경우에만 가능합니다.', !canPay, faCreditCard)
        : renderButton('결제취소', !canPayCanceled, onCancelPay, 'cancelPayTooltip', 
            <div>
              <p>결제 시간: {paidDateTime}</p>
              {!canPayCanceled && <p className="text-red-300 mt-1">결제 취소는 결제수단이 [카드, 납부자결제] 경우만 가능합니다.</p>}
            </div>, true, faBan, true)}
    </div>
  );
};

export default BillingDetailButtons;