import InputWeb from '@/components/common/inputs/InputWeb';
import TextArea from '../inputs/TextArea';
import { useMemberStore } from '@/stores/useMemberStore';
import { formatPhone, removeDashes } from '@/utils/formatPhone';
import { useEffect } from 'react';

const BasicInfoForm = ({ formType, disabled }) => {
  const { basicInfo, resetBasicInfo, setBasicInfoItem, setAddressInfoItem } = useMemberStore();

  // 사용자 입력값
  const handleChangeValue = e => {
    const { id, value } = e.target;

    if (id === 'zipcode' || id === 'address' || id === 'addressDetail') {
      setAddressInfoItem({
        [id]: value,
      });
    } else if (id === 'memberPhone' || id === 'memberHomePhone') {
      setBasicInfoItem({
        [id]: removeDashes(value === '' ? null : value),
      });
    } else {
      setBasicInfoItem({
        [id]: value === '' ? null : value,
      });
    }
  };

  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // 회원 등록일 경우 zustand를 비움
  useEffect(() => {
    if (formType === 'CREATE') resetBasicInfo();
  }, []);

  return (
    <div className='flex h-full p-5'>
      <div className='flex flex-col justify-between flex-1 '>
        <InputWeb
          id='memberName'
          label='회원명'
          placeholder='회원명(한글, 영문 대소문자 1~40)'
          type='text'
          required
          value={basicInfo.memberName}
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='memberPhone'
          label='휴대전화'
          placeholder='ex) 010-9999-9999'
          type='text'
          value={formatPhone(basicInfo.memberPhone)}
          required
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='memberEnrollDate'
          label='가입일'
          placeholder='ex) 2024-11-02'
          type='text'
          value={basicInfo.memberEnrollDate}
          required
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='memberHomePhone'
          label='유선전화'
          placeholder='ex) 02-432-7777'
          type='text'
          value={formatPhone(basicInfo.memberHomePhone)}
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='memberEmail'
          label='이메일'
          placeholder='ex) example@gmail.com'
          type='text'
          value={basicInfo.memberEmail}
          required
          disabled={disabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
      </div>
      <div className='flex-1 ml-10'>
        <div className='flex items-end mb-5'>
          <InputWeb
            id='zipcode'
            label='주소'
            placeholder='우편번호'
            type='address'
            value={basicInfo.memberAddress.zipcode}
            classContainer='mr-5'
            disabled={disabled}
            readOnly
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
          <InputWeb
            id='address'
            placeholder='주소'
            type='text'
            value={basicInfo.memberAddress.address}
            disabled={disabled}
            classContainer='w-full'
            readOnly
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
        </div>
        <InputWeb
          id='addressDetail'
          label='상세 주소'
          placeholder='상세 주소'
          type='text'
          value={basicInfo.memberAddress.addressDetail}
          disabled={disabled}
          classContainer='mb-3'
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />

        <TextArea
          id='memberMemo'
          label='메모'
          disabled={disabled}
          classTextarea='h-52'
          value={basicInfo.memberMemo}
          onChange={handleChangeValue}
        />
      </div>
    </div>
  );
};

export default BasicInfoForm;
