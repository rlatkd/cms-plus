import Input from '@/components/common/inputs/Input';
import AddressInput from '@/components/common/inputs/AddressInput';

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
        />
        <Input
          label='휴대전화'
          name='phone'
          type='tel'
          required
          placeholder="'-' 없이, 최대 12자리"
          value={userData.memberDTO.phone || ''}
          onChange={handleChange}
          maxLength={13}
          tabIndex={0}
        />
        <Input
          label='유선전화'
          name='homePhone'
          type='tel'
          placeholder="'-' 없이, 최대 12자리"
          value={userData.memberDTO.homePhone || ''}
          onChange={handleChange}
          maxLength={12}
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
        />
        <AddressInput userData={userData} setUserData={setUserData} />
      </form>
    </div>
  );
};

export default BasicInfo;
