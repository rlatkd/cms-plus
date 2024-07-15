import { useEffect } from 'react';

const AlertWidth = ({ message, onClose }) => {
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
      <div className='relative shadow-dialog  bg-white rounded-lg p-6 h-48 w-480 flex flex-col justify-between '>
        <h2 className='text-xl text-text_black font-900 mb-4'>효성 CMS#</h2>
        <div className='text-text_black font-400 mb-3'>{message}</div>
        <div className='flex justify-end space-x-2'>
          <button
            className='bg-mint text-white text-sm transition-all duration-200 
                        font-700 px-8 py-2 rounded-lg hover:bg-mint_hover'
            onClick={onClose}
            autoFocus>
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default AlertWidth;
