import closeIcon from '@/assets/close.svg';

const BaseModal = ({ icon, isShowModal, setIsShowModal, modalTitle, height, width, children }) => {
  return (
    <>
      {isShowModal ? (
        <div className='fixed inset-0 z-50 flex items-center justify-center '>
          <div className='fixed inset-0 bg-black opacity-25' />
          <div
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
