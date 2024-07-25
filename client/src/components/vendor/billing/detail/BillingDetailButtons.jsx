import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faPenToSquare,
  faTrashAlt,
  faPaperPlane,
  faBan,
  faCreditCard,
} from '@fortawesome/free-solid-svg-icons';
import { Tooltip } from 'react-tooltip';
import 'react-tooltip/dist/react-tooltip.css';
import dayjs from 'dayjs';

const BillingDetailButtons = ({
  onEdit,
  onRemove,
  onSend,
  onCancelSend,
  onPay,
  onCancelPay,
  fieldToState,
  invoiceSendTime,
  paidDateTime,
}) => {
  const formatDateTime = dateTime => {
    return dayjs(dateTime).format('YYYY-MM-DD HH:mm:ss');
  };

  const buttonClass = (isDisabled, isCancel = false) => `
    flex justify-between items-center px-4 py-2 ml-2 
    font-700 rounded-md border cursor-pointer transition-all duration-200 ease-in-out
    ${
      isDisabled
        ? 'text-gray-400 border-gray-400 bg-gray-100 cursor-not-allowed'
        : isCancel
          ? 'text-red-500 border-red-500 hover:bg-red-50'
          : 'text-white bg-mint hover:bg-mint_hover'
    }
  `;

  const renderButton = (label, field, onClick, tooltipId, icon, isCancel = false) => {
    const state = fieldToState[field];
    if (!state) return;
    const isDisabled = !state.enabled;

    const showTooltip = isDisabled || isCancel;

    return (
      <div className='relative inline-block'>
        <button
          className={`${buttonClass(isDisabled, isCancel)} ${tooltipId}`}
          disabled={isDisabled}
          onClick={onClick}
          data-tooltip-id={tooltipId}>
          <FontAwesomeIcon icon={icon} className='mr-2' />
          <p>{label}</p>
        </button>
        {showTooltip && (
          <Tooltip id={tooltipId} place='top' variant='dark' className='tooltip'>
            <div className='p-2'>
              {isDisabled ? (
                <p>{state.reason || '현재 이 작업을 수행할 수 없습니다.'}</p>
              ) : (
                <>
                  {field === 'CANCEL_INVOICE' && (
                    <p>
                      청구서 발송 시간:{' '}
                      <span className='text-yellow-300 font-bold'>
                        {formatDateTime(invoiceSendTime)}
                      </span>
                    </p>
                  )}
                  {field === 'CANCEL_PAYMENT' && (
                    <p>
                      결제 시간:{' '}
                      <span className='text-yellow-300 font-bold'>
                        {formatDateTime(paidDateTime)}
                      </span>
                    </p>
                  )}
                </>
              )}
            </div>
          </Tooltip>
        )}
      </div>
    );
  };

  return (
    <div className='flex flex-wrap gap-2'>
      {renderButton('청구 수정', 'UPDATE', onEdit, 'editTooltip', faPenToSquare)}
      {renderButton('청구 삭제', 'DELETE', onRemove, 'deleteTooltip', faTrashAlt)}

      {invoiceSendTime == null
        ? renderButton('청구서 발송', 'SEND_INVOICE', onSend, 'sendTooltip', faPaperPlane)
        : renderButton(
            '청구서 발송취소',
            'CANCEL_INVOICE',
            onCancelSend,
            'cancelSendTooltip',
            faBan,
            true
          )}

      {paidDateTime == null
        ? renderButton('실시간 결제', 'PAY_REALTIME', onPay, 'payTooltip', faCreditCard)
        : renderButton('결제취소', 'CANCEL_PAYMENT', onCancelPay, 'cancelPayTooltip', faBan, true)}
    </div>
  );
};

export default BillingDetailButtons;
