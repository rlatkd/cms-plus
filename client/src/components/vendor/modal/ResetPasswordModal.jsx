import { postResetPassword } from '@/apis/auth';
import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';
import useAlert from '@/hooks/useAlert';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { validateField } from '@/utils/validators';
import { useContext, useEffect, useState } from 'react';

const ResetPasswordModal = ({ icon, isShowModal, findedId, setIsShowModal, modalTitle }) => {
  const [passwordFormData, setPasswordFormData] = useState({
    username: '',
    newPassword: '',
    newPasswordCheck: '',
  });

  const onAlert = useAlert();

  // <---- 사용자 입력값 ---->
  const handleChangeValue = e => {
    const { id, value } = e.target;
    setPasswordFormData(prev => ({ ...prev, [id]: value == '' ? '' : value }));
  };
  // <---- 공백입력 막기 ---->
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // <---- 비밀번호 재설정 API ---->
  const axiosResetPassword = async () => {
    try {
      const { newPasswordCheck, ...data } = passwordFormData;
      const res = await postResetPassword(data);
      console.log('!----비밀번호 찾기 요청 성공----!'); // 삭제예정
      setIsShowModal(false);
      onAlert({ msg: '비밀번호 재설정이 완료되었습니다!', type: 'success' });
    } catch (err) {
      console.error('axiosFindPassword => ', err.response);
    }
  };

  // <----- 비밀번호 재설정폼 유효성 검사 ----->
  const isValidatePasswordForm = () => {
    const isValidName = validateField('password', passwordFormData.newPassword);
    const isValidEmail = passwordFormData.newPassword === passwordFormData.newPasswordCheck;
    return isValidName && isValidEmail;
  };

  useEffect(() => {
    setPasswordFormData(prev => ({ ...prev, username: findedId }));
  }, [findedId]);

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-400'}
      width={'w-580'}>
      <div className='w-full h-full flex flex-col justify-between'>
        <InputWeb
          id='newPassword'
          label='새 비밀번호'
          type='password'
          placeholder='새 비밀번호를 입력해 주세요.'
          value={passwordFormData.newPassword}
          classContainer='mb-4'
          classInput='rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
          maxLength={16}
          isValid={validateField('password', passwordFormData.newPassword)}
          errorMsg='올바른 형식 아닙니다.'
        />
        <InputWeb
          id='newPasswordCheck'
          label='새 비밀번호 확인'
          type='password'
          placeholder='새 비밀번호를 다시 한번 입력해 주세요.'
          value={passwordFormData.newPasswordCheck}
          classInput='rounded-xl'
          classContainer='mb-8'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
          maxLength={16}
          isValid={passwordFormData.newPassword === passwordFormData.newPasswordCheck}
          errorMsg='비밀번호가 일치하지 않습니다.'
        />
        <button
          disabled={!isValidatePasswordForm()}
          className={`font-700 px-4 py-3  text-white rounded-xl transition-all duration-200  
          ${isValidatePasswordForm() ? 'bg-mint hover:bg-mint_hover ' : 'bg-btn_disa '}`}
          onClick={axiosResetPassword}>
          비밀번호 재설정
        </button>
      </div>
    </BaseModal>
  );
};

export default ResetPasswordModal;
