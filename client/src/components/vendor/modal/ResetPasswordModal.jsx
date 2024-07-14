import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useState } from 'react';

const ResetPasswordModal = ({ icon, isShowModal, setIsShowModal, modalTitle }) => {
  const [passwordFormData, setPasswordFormData] = useState({
    newPassword: null,
    newPasswordCheck: null,
  });

  // 사용자 입력값
  const handleChangeValue = e => {
    const { id, value } = e.target;
    setPasswordFormData(prev => ({ ...prev, [id]: value == '' ? null : value }));
  };

  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // 비밀번호 재설정 완료 함수정의 ( 비밀번호 확인 까지 )

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-380'}
      width={'w-580'}>
      <div className='w-full h-full flex flex-col justify-between'>
        <InputWeb
          id='newPassword'
          label='새 비밀번호'
          type='password'
          placeholder='새 비밀번호를 입력해 주세요.'
          classContainer='mb-4'
          classInput='rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='newPasswordCheck'
          label='새 비밀번호 확인'
          type='password'
          placeholder='새 비밀번호를 다시 한번 입력해 주세요.'
          classInput='rounded-xl'
          classContainer='mb-8'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <button
          className='font-700 bg-mint px-4 py-3  text-white rounded-xl  
            transition-all duration-200 hover:bg-mint_hover'>
          비밀번호 재설정
        </button>
      </div>
    </BaseModal>
  );
};

export default ResetPasswordModal;
