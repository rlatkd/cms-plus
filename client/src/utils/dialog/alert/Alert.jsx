import { useEffect } from 'react';

const Alert = ({ message, onClose }) => {
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
    <div className='fixed inset-0 flex items-center justify-center z-50'>
      <div className='fixed inset-0 bg-black bg-opacity-50' onClick={e => e.stopPropagation()} />
      <div className='relative bg-white rounded-lg shadow-lg p-6 w-96'>
        <h2 className='text-xl font-bold mb-4'>Alert</h2>
        <div className='text mb-4'>{message}</div>
        <div className='flex justify-end space-x-2'>
          <button
            className='bg-transparent text-blue-500 border-2 border-blue-500 uppercase 
            transition-all duration-200 font-bold px-4 py-2 rounded 
            hover:bg-blue-500 hover:text-white focus:border-blue-700'
            onClick={onClose}
            autoFocus>
            ok
          </button>
        </div>
      </div>
    </div>
  );
};

export default Alert;
