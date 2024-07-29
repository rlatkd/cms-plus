import closeIcon from '@/assets/close.svg';
import { useEffect, useRef } from 'react';

const BaseModal = ({ icon, isShowModal, setIsShowModal, modalTitle, height, width, children }) => {
  const modalRef = useRef();

  // <----- ESC 키를 눌렀을 때 모달 닫기 ----->
  const handleEscape = e => {
    if (e.key === 'Escape') {
      setIsShowModal(false);
    }
  };

  // <----- 모달 외부 클릭 감지 ----->
  const handleClickOutside = e => {
    if (modalRef.current && !modalRef.current.contains(e.target)) {
      setIsShowModal(false);
    }
  };

  // <----- 모달창 닫기 ----->
  useEffect(() => {
    // 이벤트 리스너 추가
    document.addEventListener('keydown', handleEscape);
    document.addEventListener('mousedown', handleClickOutside);

    // 클린업 함수 => umMount시 발생
    return () => {
      document.removeEventListener('keydown', handleEscape);
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <>
      {isShowModal ? (
        <div className='fixed inset-0 z-50 flex items-center justify-center '>
          <div className='fixed inset-0 bg-black opacity-25' />
          <div
            ref={modalRef}
            className={`z-10 flex flex-col rounded-lg bg-white p-7 shadow-lg ${height} ${width} `}>
            <div className='mb-4 flex items-center justify-between border-b border-ipt_border pb-3 '>
              <div className='flex'>
                <img src={icon} alt='icon' className='h-7 w-7 bg-mint p-1 rounded-md ml-1 mr-3' />
                <p className='text-xl text-text_black font-700'>{modalTitle}</p>
              </div>
              <button className='text-black' onClick={() => setIsShowModal(false)}>
                <img src={closeIcon} alt='close' className='h-4 w-4 mr-1' />
              </button>
            </div>
            <div className='w-full h-full'>{children}</div>
          </div>
        </div>
      ) : null}
    </>
  );
};

export default BaseModal;
