import { postFindPassword } from '@/apis/auth';
import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import { formatPhone, removeDashes } from '@/utils/format/formatPhone';
import { useEffect, useState } from 'react';

const MessageSendMethod = [
  { label: '전화번호 인증', value: 'SMS' },
  { label: '이메일 인증', value: 'EMAIL' },
];

const FindVendoPasswordModal = ({
  isShowModal,
  setIsShowModal,
  setIsShowResetPasswordModal,
  axiosRequestAuthenticationNumber,
  setFindedId,
  icon,
  modalTitle,
}) => {
  const [selectedSendMethod, setSelectedSendMethod] = useState('SMS');
  const [vendorPasswordFormData, setVendorPasswordFormData] = useState({
    method: 'SMS',
    username: '',
    phone: '',
    authenticationNumber: '',
  });

  // 사용자 입력값
  const handleChangeValue = e => {
    const { id, value } = e.target;
    if (id === 'phone') {
      setVendorPasswordFormData(prev => ({
        ...prev,
        [id]: removeDashes(value == '' ? '' : value),
      }));
    } else {
      setVendorPasswordFormData(prev => ({ ...prev, [id]: value == '' ? '' : value }));
    }
    if (id === 'username') {
      console.log('비밀번호찾기', value);
      setFindedId(value);
    }
  };

  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // <---- 메세지 전송 방법 서택 ---->
  const handleChangeMessageSendMethod = value => {
    setSelectedSendMethod(value);
  };

  // <---- method에 따른 입력폼 변경 ---->
  const settingvendorPasswordFormData = () => {
    if (selectedSendMethod === 'SMS') {
      setVendorPasswordFormData({
        method: 'SMS',
        username: '',
        phone: '',
        authenticationNumber: '',
      });
    } else {
      [];
      setVendorPasswordFormData({
        method: 'EMAIL',
        username: '',
        email: '',
        authenticationNumber: '',
      });
    }
  };

  // <---- 비밀번호 찾기 API ---->
  const axiosFindPassword = async () => {
    try {
      const res = await postFindPassword(vendorPasswordFormData);
      console.log('!----비밀번호 찾기 요청 성공----!'); // 삭제예정
      if (res.data) {
        setIsShowModal(false);
        setIsShowResetPasswordModal(true);
      }
    } catch (err) {
      // TODO
      // 아이디가 없을 시 예외 처리
      console.error('axiosFindPassword => ', err.response.data);
    }
  };

  useEffect(() => {
    setSelectedSendMethod('SMS');
  }, [isShowModal]);

  useEffect(() => {
    settingvendorPasswordFormData();
  }, [isShowModal, selectedSendMethod]);

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-510'}
      width={'w-640'}>
      <div className='w-full h-full flex flex-col justify-between'>
        <RadioGroup
          name='MessageSendMethod'
          options={MessageSendMethod}
          selectedOption={selectedSendMethod}
          onChange={handleChangeMessageSendMethod}
          classContainer='ml-2'
          classRadio='text-15 font-400'
        />
        <InputWeb
          id='username'
          label='아이디'
          type='text'
          placeholder='이름을 입력해 주세요.'
          value={vendorPasswordFormData.username}
          classInput='mb-4 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <div className='mb-4 flex items-end'>
          {selectedSendMethod === 'SMS' ? (
            <InputWeb
              id='phone'
              label='휴대전화번호'
              type='text'
              placeholder='휴대전화번호를 입력해 주세요.'
              value={formatPhone(vendorPasswordFormData.phone)}
              onChange={handleChangeValue}
              onKeyDown={handleKeyDown}
              classContainer='w-9/12'
              classInput='rounded-xl'
            />
          ) : (
            <InputWeb
              id='email'
              label='이메일'
              type='text'
              placeholder='이메일을 입력해 주세요.'
              value={vendorPasswordFormData.email ? vendorPasswordFormData.email : ''}
              onChange={handleChangeValue}
              onKeyDown={handleKeyDown}
              classContainer='w-9/12'
              classInput='rounded-xl'
            />
          )}
          <button
            className={`ml-3 w-3/12 disabled: text-white text-base font-700 h-54 rounded-xl 
              ${
                (vendorPasswordFormData.phone || vendorPasswordFormData.email) &&
                (vendorPasswordFormData.phone?.length > 0 ||
                  vendorPasswordFormData.email?.length > 0)
                  ? 'bg-mint hover:bg-mint_hover transition-all duration-200'
                  : 'bg-btn_disa'
              }`}
            disabled={!vendorPasswordFormData.phone && !vendorPasswordFormData.email}
            onClick={() => axiosRequestAuthenticationNumber(vendorPasswordFormData)}>
            인증번호 받기
          </button>
        </div>
        <InputWeb
          id='authenticationNumber'
          label='인증번호'
          type='text'
          placeholder='인증번호 6자리를 입력해 주세요.'
          value={vendorPasswordFormData.authenticationNumber}
          classInput='mb-8 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <button
          className='font-700 bg-mint px-4 py-3  text-white rounded-xl  
                    hover:bg-mint_hover  transition-all duration-200'
          onClick={axiosFindPassword}>
          비밀번호 찾기
        </button>
      </div>
    </BaseModal>
  );
};

export default FindVendoPasswordModal;
