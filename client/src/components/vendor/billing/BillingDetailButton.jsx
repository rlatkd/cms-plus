import edit from '@/assets/edit.svg';
import remove from '@/assets/remove.svg';

const BillingDetailButton = () => {
  const handleButtonClick = () => {};

  return (
    <div className='flex justify-end items-center border-b border-ipt_border px-2 pt-1 pb-3'>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
        font-700 rounded-md border border-mint cursor-pointer'
        onClick={handleButtonClick}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>청구수정</p>
      </button>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
        font-700 rounded-md border border-mint cursor-pointer'
        onClick={handleButtonClick}>
        <img src={remove} alt='remove' className='mr-2 ' />
        <p>청구삭제</p>
      </button>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
        font-700 rounded-md border border-mint cursor-pointer'
        onClick={handleButtonClick}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>청구서발송</p>
      </button>
      <button
        className='flex justify-between items-center px-4 py-2 ml-4 text-mint
        font-700 rounded-md border border-mint cursor-pointer'
        onClick={handleButtonClick}>
        <img src={edit} alt='edit' className='mr-2 ' />
        <p>실시간결제</p>
      </button>
    </div>
  );
};

export default BillingDetailButton;
