import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';

const SuccessFindIdModal = ({
  icon,
  isShowModal,
  setIsShowModal,
  modalTitle,
  findedId,
  setVendorFormData,
}) => {
  const handleMoveLogin = () => {
    console.log(findedId);
    setVendorFormData(prev => ({ ...prev, username: findedId }));
    setIsShowModal(false);
  };

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-360'}
      width={'w-420'}>
      <div className='w-full h-full flex flex-col justify-end '>
        <div className='flex flex-col justify-center items-center h-full pt-8 px-12'>
          <p className='text-lg text-text_grey font-700 break-words text-center mb-6 '>
            입력하신 정보와 일치하는 계정은 다음과 같습니다.
          </p>
          <InputWeb
            id='username'
            type='text'
            placeholder='아이디를 입력해 주세요.'
            classContainer='w-full'
            readOnly
            value={findedId}
          />
        </div>
        <button
          className='font-700 bg-mint px-4 py-3  text-white rounded-lg  
                    transition-all duration-200 hover:bg-mint_hover'
          onClick={handleMoveLogin}>
          로그인
        </button>
      </div>
    </BaseModal>
  );
};

export default SuccessFindIdModal;
