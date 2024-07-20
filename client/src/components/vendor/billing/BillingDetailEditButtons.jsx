import edit from '@/assets/edit.svg';

const BillingDetailEditButtons = ({ onSave, onCancel }) => {
  return (
    <>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
font-700 rounded-md border border-mint cursor-pointer'
        onClick={onCancel}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>취소</p>
      </button>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
font-700 rounded-md border border-mint cursor-pointer'
        onClick={onSave}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>수정</p>
      </button>
    </>
  );
};

export default BillingDetailEditButtons;
