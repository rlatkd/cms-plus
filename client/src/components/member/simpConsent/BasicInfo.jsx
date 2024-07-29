import Input from '@/components/common/inputs/Input';
import AddressInput from '@/components/common/inputs/AddressInput';
import { validateField } from '@/utils/validators';

const BasicInfo = ({ userData, setUserData }) => {
  const handleChange = e => {
    const { name, value } = e.target;
    setUserData({
      memberDTO: { [name]: value },
    });
  };

  return (
    <div className='flex flex-col bg-white p-1'>
      <div className='w-full text-left'>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          회원님의
          <br />
          기본정보를 확인해주세요.
        </h3>
      </div>

      <form className='space-y-4'>
        <Input
          label='회원명'
          name='name'
          type='text'
          required
          placeholder='최대 40자'
          value={userData.memberDTO.name || ''}
          onChange={handleChange}
          maxLength={40}
          tabIndex={0}
          isValid={!userData.memberDTO.name || validateField('name', userData.memberDTO.name)}
          errorMsg='올바른 회원명을 입력해주세요.'
        />
        <Input
          label='휴대전화'
          name='phone'
          type='tel'
          required
          placeholder="'-' 없이, 최대 11자리"
          value={userData.memberDTO.phone || ''}
          onChange={handleChange}
          maxLength={11}
          tabIndex={0}
          isValid={!userData.memberDTO.phone || validateField('phone', userData.memberDTO.phone)}
          errorMsg='올바른 휴대전화 번호를 입력해주세요.'
        />
        <Input
          label='유선전화'
          name='homePhone'
          type='tel'
          placeholder="'-' 없이, 최대 10자리"
          value={userData.memberDTO.homePhone || ''}
          onChange={handleChange}
          maxLength={10}
          isValid={ userData.memberDTO.homePhone === '' || validateField('homePhone', userData.memberDTO.homePhone)}
          errorMsg='올바른 유선전화 번호를 입력해주세요.'
        />
        <Input
          label='이메일'
          name='email'
          type='email'
          required
          placeholder='cms@gmail.com'
          value={userData.memberDTO.email || ''}
          onChange={handleChange}
          maxLength={50}
          isValid={!userData.memberDTO.email || validateField('email', userData.memberDTO.email)}
          errorMsg='올바른 이메일 번호를 입력해주세요.'
        />
        <AddressInput userData={userData} setUserData={setUserData} />
      </form>
    </div>
  );
};

export default BasicInfo;
