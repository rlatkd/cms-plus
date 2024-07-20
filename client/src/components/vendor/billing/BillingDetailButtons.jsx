import edit from '@/assets/edit.svg';

const BillingDetailButtons = ({ onEdit, onRemove, onSend, onPay  }) => {
  const handle = () => {};

  return (
    <>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
font-700 rounded-md border border-mint cursor-pointer'
        onClick={onEdit}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>청구 수정</p>
      </button>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
font-700 rounded-md border border-mint cursor-pointer'
        onClick={onRemove}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>청구 삭제</p>
      </button>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
font-700 rounded-md border border-mint cursor-pointer'
        onClick={onSend}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>청구서 발송</p>
      </button>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
font-700 rounded-md border border-mint cursor-pointer'
        onClick={onPay}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>실시간 결제</p>
      </button>
    </>
  );
};

export default BillingDetailButtons;
