import InputWeb from '@/components/common/inputs/InputWeb';
import TextArea from '../inputs/TextArea';
import { formatPhone, removeDashes } from '@/utils/formatPhone';
import { useMemberBasicStore } from '@/stores/useMemberBasicStore';

// formType : CREATE, UPDATE, DETAIL
const BasicInfoForm = ({ formType }) => {
  console.log(formType);
  const { basicInfo, setBasicInfoItem, setAddressInfoItem } = useMemberBasicStore();

  // <------ 인풋 필드 입력값 변경 ------>
  const handleChangeValue = e => {
    const { id, value } = e.target;

    if (id === 'zipcode' || id === 'address' || id === 'addressDetail') {
      setAddressInfoItem({
        [id]: value,
      });
    } else if (id === 'memberPhone' || id === 'memberHomePhone') {
      setBasicInfoItem({
        [id]: removeDashes(value === '' ? '' : value),
      });
    } else {
      setBasicInfoItem({
        [id]: value === '' ? '' : value,
      });
    }
  };

  // TODO
  // <------ 정규표현식 예외처리 ------>

  // <------ 공백입력 불가 ------>
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // <------ formType : DETAIL일 경우 입력창 비활성화 ------>
  const isDisabled = formType === 'DETAIL';

  return (
    <div className='flex flex-col pt-5 px-2 desktop:flex-row desktop:h-[calc(100%-100px)] extra_desktop:h-[520px]'>
      <div className='flex flex-col justify-between flex-1 '>
        <InputWeb
          id='memberName'
          label='회원명'
          placeholder='회원명(한글, 영문 대소문자 1~40)'
          type='text'
          required
          value={basicInfo.memberName}
          disabled={isDisabled}
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
          disabled={isDisabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        {/* TODO */}
        {/* 데이터 피커 적용 제대로 */}
        <InputWeb
          id='memberEnrollDate'
          label='가입일'
          placeholder='ex) 2024-11-02'
          type='text'
          value={basicInfo.memberEnrollDate}
          required
          disabled={isDisabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
        <InputWeb
          id='memberHomePhone'
          label='유선전화'
          placeholder='ex) 02-432-7777'
          type='text'
          value={formatPhone(basicInfo.memberHomePhone)}
          disabled={isDisabled}
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
          disabled={isDisabled}
          onChange={handleChangeValue}
          onKeyDown={handleKeyDown}
        />
      </div>
      {/* TODO */}
      {/* 주소 입력 받기 */}
      <div className='flex-1 desktop:ml-10'>
        <div className='flex items-end mb-5'>
          <InputWeb
            id='zipcode'
            label='주소'
            placeholder='우편번호'
            type='address'
            value={basicInfo.memberAddress.zipcode}
            classContainer='mr-5'
            disabled={isDisabled}
            readOnly
            onChange={handleChangeValue}
          />
          <InputWeb
            id='address'
            placeholder='주소'
            type='text'
            value={basicInfo.memberAddress.address}
            disabled={isDisabled}
            classContainer='w-full'
            readOnly
            onChange={handleChangeValue}
          />
        </div>
        <InputWeb
          id='addressDetail'
          label='상세 주소'
          placeholder='상세 주소'
          type='text'
          value={basicInfo.memberAddress.addressDetail}
          disabled={isDisabled}
          classContainer='mb-3'
          onChange={handleChangeValue}
        />

        <TextArea
          id='memberMemo'
          label='메모'
          disabled={isDisabled}
          classTextarea='h-52'
          value={basicInfo.memberMemo}
          onChange={handleChangeValue}
        />
      </div>
    </div>
  );
};

export default BasicInfoForm;
