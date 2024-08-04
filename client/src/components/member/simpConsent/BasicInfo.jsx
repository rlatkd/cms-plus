import Input from '@/components/common/inputs/Input';
import AddressInput from '@/components/common/inputs/AddressInput';
import { validateField } from '@/utils/validators';
import { formatPhone, removeDashes } from '@/utils/format/formatPhone';

const BasicInfo = ({ userData, setUserData, contractId, name }) => {
  
  const handleChange = e => {
    const { name, value } = e.target;
    let formattedValue = value;

    if (name === 'phone' || name === 'homePhone') {
      // 입력 시 대시 제거
      const cleanedValue = removeDashes(value);
      // 포맷팅된 값
      formattedValue = formatPhone(cleanedValue);
    }

    setUserData({
      memberDTO: {
        [name]:
          name === 'phone' || name === 'homePhone' ? removeDashes(formattedValue) : formattedValue,
      },
    });
  };

  return (
    <div className='flex flex-col bg-white p-1'>
      <div className='w-full text-left'>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          {name}님의
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
          disabled={!!contractId}
        />
        <Input
          label='휴대전화'
          name='phone'
          type='tel'
          required
          placeholder='최대 11자리'
          value={formatPhone(userData.memberDTO.phone) || ''}
          onChange={handleChange}
          maxLength={13}
          tabIndex={0}
          isValid={!userData.memberDTO.phone || validateField('phone', userData.memberDTO.phone)}
          errorMsg='올바른 휴대전화 번호를 입력해주세요.'
          disabled={!!contractId}
        />
        <Input
          label='유선전화'
          name='homePhone'
          type='tel'
          placeholder='최대 10자리'
          value={formatPhone(userData.memberDTO.homePhone) || ''}
          onChange={handleChange}
          maxLength={12}
          isValid={
            userData.memberDTO.homePhone === '' ||
            validateField('homePhone', userData.memberDTO.homePhone)
          }
          errorMsg='올바른 유선전화 번호를 입력해주세요.'
          disabled={!!contractId}
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
          disabled={!!contractId}
        />
        <AddressInput disabled={!!contractId} />
      </form>
    </div>
  );
};

export default BasicInfo;
