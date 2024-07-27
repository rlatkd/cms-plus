import { useEffect } from 'react';
import confirmWarning from '@/assets/dialog/confirmWarning.svg';

const ConfirmWarning = ({ message, onClickOK, onClickCancel, title }) => {
  useEffect(() => {
    const handleEscape = e => {
      if (e.key === 'Escape') {
        onClickCancel();
      }
    };

    document.addEventListener('keydown', handleEscape);

    return () => document.removeEventListener('keydown', handleEscape);
  }, [onClickCancel]);

  return (
    <div className='fixed inset-0 mt-10 flex justify-center z-50'>
      <div className='fixed inset-0 bg-black bg-opacity-10' onClick={e => e.stopPropagation()} />
      <div className='relative shadow-dialog bg-white rounded-lg p-6 h-48 w-[550px] '>
        <div className='flex items-center'>
          <img className='w-11 mr-3' src={confirmWarning} alt='confirmWarning' />
          <h2 className='text-xl text-text_black font-900 mb-2'>{title}</h2>
        </div>
        <div className='text-text_grey font-700 ml-12 mt-2'>
          <p className='w-full break-words'>{message}</p>
        </div>
        <div className='absolute bottom-4 right-4 text-sm'>
          <button
            className='bg-white text-mint transition-all duration-200 
                        font-700 px-6 h-8 rounded-lg  border border-mint'
            onClick={onClickCancel}>
            취소
          </button>
          <button
            className='bg-mint text-white transition-all duration-200 
                        font-700 px-10 h-8 rounded-lg ml-2 hover:bg-mint_hover'
            onClick={onClickOK}
            autoFocus>
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default ConfirmWarning;
