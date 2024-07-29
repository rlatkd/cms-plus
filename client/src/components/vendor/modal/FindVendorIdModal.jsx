import { postFindIdentifier } from '@/apis/auth';
import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';
import RadioGroup from '@/components/common/inputs/RadioGroup';
import { formatPhone, removeDashes } from '@/utils/format/formatPhone';
import { useEffect, useState } from 'react';

const MessageSendMethod = [
  { label: '전화번호 인증', value: 'SMS' },
  { label: '이메일 인증', value: 'EMAIL' },
];

const FindVendorIdModal = ({
  isShowModal,
  setIsShowModal,
  setFindedId,
  setIsShowSuccessFindIdModal,
  axiosRequestAuthenticationNumber,
  icon,
  modalTitle,
}) => {
  const [selectedSendMethod, setSelectedSendMethod] = useState('SMS');
  const [vendorIdFormData, setVendorIdFormData] = useState({
    method: 'SMS',
    name: '',
    phone: '',
    authenticationNumber: '',
  });

  // <---- 사용자 입력값 ---->
  const handleChangeValue = e => {
    const { id, value } = e.target;
    if (id === 'phone') {
      setVendorIdFormData(prev => ({ ...prev, [id]: removeDashes(value == '' ? '' : value) }));
    } else {
      setVendorIdFormData(prev => ({ ...prev, [id]: value == '' ? '' : value }));
    }
  };

  // <---- 공백입력 막기 ---->
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // <---- 메세지 전송 방법 서택 ---->
  const handleChangeMessageSendMethod = value => {
    setSelectedSendMethod(value);
  };

  // <---- method에 따른 입력폼 변경 ---->
  const settingvendorIdFormData = () => {
    if (selectedSendMethod === 'SMS') {
      setVendorIdFormData({
        method: 'SMS',
        name: '',
        phone: '',
        authenticationNumber: '',
      });
    } else {
      setVendorIdFormData({ method: 'EMAIL', name: '', email: '', authenticationNumber: '' });
    }
  };

  // <---- 아이디 찾기 API ---->
  const axiosFindIdentifier = async () => {
    try {
      console.log(vendorIdFormData);
      const res = await postFindIdentifier(vendorIdFormData);
      console.log('!----아이디 찾기 요청 성공----!'); // 삭제예정
      await setFindedId(res.data.username);
      setIsShowSuccessFindIdModal(true);
      setIsShowModal(false);
    } catch (err) {
      // TODO
      // 아이디가 없을 시 예외 처리
      console.error('axiosFindIdentifier => ', err.response);
    }
  };

  useEffect(() => {
    setSelectedSendMethod('SMS');
  }, [isShowModal]);

  useEffect(() => {
    settingvendorIdFormData();
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
          id='name'
          label='이름'
          type='text'
          placeholder='이름을 입력해 주세요.'
          value={vendorIdFormData.name}
          classInput='mb-4 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
          maxLength={40}
        />
        <div className='mb-4 flex items-end'>
          {selectedSendMethod === 'SMS' ? (
            <InputWeb
              id='phone'
              label='휴대전화번호'
              type='text'
              placeholder='휴대전화번호를 입력해 주세요.'
              value={formatPhone(vendorIdFormData.phone)}
              onChange={handleChangeValue}
              onKeyDown={handleKeyDown}
              classContainer='w-9/12'
              classInput='rounded-xl'
              maxLength={11}
            />
          ) : (
            <InputWeb
              id='email'
              label='이메일'
              type='text'
              placeholder='이메일을 입력해 주세요.'
              value={vendorIdFormData.email ? vendorIdFormData.email : ''}
              onChange={handleChangeValue}
              onKeyDown={handleKeyDown}
              classContainer='w-9/12'
              classInput='rounded-xl'
              maxLength={40}
            />
          )}
          <button
            className={`ml-3 w-3/12 disabled: text-white text-base font-700 h-54 rounded-xl 
              ${
                (vendorIdFormData.phone || vendorIdFormData.email) &&
                (vendorIdFormData.phone?.length > 0 || vendorIdFormData.email?.length > 0)
                  ? 'bg-mint hover:bg-mint_hover transition-all duration-200'
                  : 'bg-btn_disa'
              }`}
            disabled={!vendorIdFormData.phone && !vendorIdFormData.email}
            onClick={() => axiosRequestAuthenticationNumber(vendorIdFormData)}>
            인증번호 받기
          </button>
        </div>
        <InputWeb
          id='authenticationNumber'
          label='인증번호'
          type='text'
          placeholder='인증번호 6자리를 입력해 주세요.'
          value={vendorIdFormData.authenticationNumber}
          classInput='mb-5 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
          maxLength={6}
        />
        <button
          className='font-700 bg-mint px-4 py-3  text-white rounded-xl 
              transition-all duration-200 hover:bg-mint_hover'
          onClick={axiosFindIdentifier}>
          아이디 찾기
        </button>
      </div>
    </BaseModal>
  );
};

export default FindVendorIdModal;
