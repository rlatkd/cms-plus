import { useEffect } from 'react';

const PostCodeModal = ({ isOpen, onClose, handleSelectAddress }) => {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
    script.async = true;
    document.head.appendChild(script);

    script.onload = () => {
      if (isOpen && window.daum) {
        const container = document.getElementById('postcode-container');
        if (container) {
          new window.daum.Postcode({
            oncomplete: function (data) {
              handleSelectAddress(data);
              onClose();
            },
            width: '450px',
            height: '475px',
            left: window.innerWidth / 2 - 450 / 2,
            top: window.innerHeight / 2 - 470 / 2,
          }).embed(container);
        }
      }
    };

    return () => {
      document.head.removeChild(script);
    };
  }, [isOpen, handleSelectAddress, onClose]);

  if (!isOpen) return null;

  return (
    <div className='fixed inset-0 z-50 flex items-center justify-center'>
      <div className='relative bg-white p-6 rounded-lg shadow-lg z-50'>
        <div id='postcode-container' style={{ width: '450px', height: '475px' }} />
      </div>
      <div className='absolute inset-0 bg-black bg-opacity-25' onClick={onClose} />
    </div>
  );
};

export default PostCodeModal;
