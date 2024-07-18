import { useState } from 'react';
import InputWeb from '@/components/common/inputs/InputWeb';
import TextArea from '../inputs/TextArea';

const BasicInfoForm = ({ disabled }) => {
  const [vendorFormData, setVendorFormData] = useState({
    name: null,
    username: '',
    password: null,
    passwordCheck: null,
    email: null,
    phone: null,
    department: null,
    homePhone: null,
  });

  // 사용자 입력값
  const handleChangeValue = e => {
    const { id, value } = e.target;
    setVendorFormData(prev => ({ ...prev, [id]: value == '' ? null : value }));
  };
  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };
  return (
    <div className='flex h-full p-5'>
      <div className='flex flex-col justify-between flex-1 mr-14'>
        <InputWeb
          id='name'
          label='회원명'
          placeholder='회원명(한글, 영문 대소문자 1~40)'
          type='text'
          required
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='phone'
          label='휴대전화'
          placeholder='ex) 010-9999-9999'
          type='text'
          required
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='enrollDate'
          label='가입일'
          placeholder='ex) 2024-11-02'
          type='text'
          required
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='homePhone'
          label='유선전화'
          placeholder='ex) 02-432-7777'
          type='text'
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='email'
          label='이메일'
          placeholder='ex) example@gmail.com'
          type='text'
          required
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
      </div>
      <div className='flex-1'>
        <div className='flex items-end mb-5'>
          <InputWeb
            id='zipCode'
            label='주소'
            placeholder='우편번호'
            type='address'
            readOnly
            classContainer='mr-5'
            disabled={disabled}
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
          <InputWeb
            id='address'
            placeholder='주소'
            type='text'
            readOnly
            disabled={disabled}
            classContainer='w-full'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
        </div>
        <InputWeb
          id='address_detail'
          label='상세 주소'
          placeholder='상세 주소'
          type='text'
          disabled={disabled}
          classContainer='mb-3'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />

        <TextArea id='memo' label='메모' />
      </div>
    </div>
  );
};

export default BasicInfoForm;
