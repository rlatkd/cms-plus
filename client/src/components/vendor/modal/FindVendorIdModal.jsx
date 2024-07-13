import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useState } from 'react';

const FindVendorIdModal = ({ icon, isShowModal, setIsShowModal, modalTitle }) => {
  const [vendorIdFormData, setVendorIdFormData] = useState({
    nameId: null,
    phoneId: '',
    certificationId: null,
  });

  // 사용자 입력값
  const handleChangeValue = e => {
    const { id, value } = e.target;
    setVendorIdFormData(prev => ({ ...prev, [id]: value == '' ? null : value }));
  };

  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // TODO
  // 인증번호 받는 함수정의
  // 아이디 찾기 완료 함수정의

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
          id='nameId'
          label='이름'
          type='text'
          placeholder='이름을 입력해 주세요.'
          classInput='mb-4 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <div className='mb-4 flex items-end'>
          <InputWeb
            id='phoneId'
            label='휴대전화번호'
            type='text'
            placeholder='휴대전화번호를 입력해 주세요.'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            classContainer='w-9/12'
            classInput='rounded-xl'
          />
          <button
            className={`ml-3 w-3/12 disabled: text-white text-base font-700 h-54 rounded-xl 
                ${
                  vendorIdFormData.phoneId !== null && vendorIdFormData.phoneId.length > 0
                    ? 'bg-mint hover:bg-mint_hover'
                    : 'bg-btn_disa'
                }  `}>
            인증번호 받기
          </button>
        </div>
        <InputWeb
          id='certificationId'
          label='인증번호'
          type='text'
          placeholder='인증번호 6자리를 입력해 주세요.'
          classInput='mb-8 rounded-xl'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <button className='font-700 bg-mint px-4 py-3  text-white rounded-xl  hover:bg-mint_hover'>
          아이디 찾기
        </button>
      </div>
    </BaseModal>
  );
};

export default FindVendorIdModal;
