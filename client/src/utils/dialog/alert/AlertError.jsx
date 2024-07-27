import { useEffect } from 'react';
import alertError from '@/assets/dialog/alertError.svg';

const AlertError = ({ title, message, onClose }) => {
  useEffect(() => {
    const handleEscape = e => {
      if (e.key === 'Escape') {
        onClose();
      }
    };

    document.addEventListener('keydown', handleEscape);

    return () => document.removeEventListener('keydown', handleEscape);
  }, [onClose]);

  return (
    <div className='fixed inset-0 mt-10 flex justify-center z-50'>
      <div className='fixed inset-0 bg-black bg-opacity-10' onClick={e => e.stopPropagation()} />
      <div className='relative shadow-dialog bg-white rounded-lg p-6 h-48 w-[550px] '>
        <div className='flex items-center'>
          <img className='w-11 mr-3' src={alertError} alt='alertError' />
          <h2 className='text-xl text-text_black font-900 mb-2'>{title}</h2>
        </div>
        <div className='text-text_grey font-700 ml-12 mt-2'>
          <p className='w-full break-words'>{message}</p>
        </div>
        <div className='flex justify-end space-x-2'>
          <button
            className='absolute bottom-4 right-4 bg-negative text-white text-sm transition-all duration-200 font-700 px-8 h-8 rounded-lg hover:bg-negative_hover'
            onClick={onClose}
            autoFocus>
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default AlertError;
