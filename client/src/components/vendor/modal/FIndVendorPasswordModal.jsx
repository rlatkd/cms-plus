import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useState } from 'react';

const FindVendoPasswordModal = ({
  icon,
  isShowModal,
  setIsShowModal,
  setIsShowResetPasswordModal,
  modalTitle,
}) => {
  const [vendorPasswordFormData, setVendorPasswordFormData] = useState({
    usernamePassword: null,
    phonePassword: '',
    certificationPassword: null,
  });

  // 사용자 입력값
  const handleChangeValue = e => {
    const { id, value } = e.target;
    setVendorPasswordFormData(prev => ({ ...prev, [id]: value == '' ? null : value }));
  };

  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  const handleMoveResetPassword = () => {
    // TODO
    // 인증이 완료된 경우 재설정 페이지로 이동하도록 설정

    setIsShowModal(false);
    setIsShowResetPasswordModal(true);
  };

  // TODO
  // 인증번호 받는 함수정의
  // 비밀번호 찾기 완료 함수정의

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-480'}
      width={'w-580'}>
      <div className='w-full h-full flex flex-col justify-between'>
        <InputWeb
          id='usernamePassword'
          label='아이디'
          type='text'
          placeholder='이름을 입력해 주세요.'
          classInput='mb-4 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <div className='mb-4 flex items-end'>
          <InputWeb
            id='phonePassword'
            label='휴대전화번호'
            type='text'
            placeholder='휴대전화번호를 입력해 주세요.'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            classContainer='w-9/12'
            classInput='rounded-xl'
          />
          <button
            className={`ml-3 w-3/12 text-white text-base font-700 h-54 rounded-xl 
                ${
                  vendorPasswordFormData.phonePassword !== null &&
                  vendorPasswordFormData.phonePassword.length > 0
                    ? 'bg-mint hover:bg-mint_hover transition-all duration-200'
                    : 'bg-btn_disa'
                }  `}>
            인증번호 받기
          </button>
        </div>
        <InputWeb
          id='certificationPassword'
          label='인증번호'
          type='text'
          placeholder='인증번호 6자리를 입력해 주세요.'
          classInput='mb-8 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <button
          className='font-700 bg-mint px-4 py-3  text-white rounded-xl  
                    hover:bg-mint_hover  transition-all duration-200'
          onClick={handleMoveResetPassword}>
          비밀번호 찾기
        </button>
      </div>
    </BaseModal>
  );
};

export default FindVendoPasswordModal;
