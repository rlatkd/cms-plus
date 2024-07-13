import { useNavigate } from 'react-router-dom';
import InputWeb from './common/inputs/InputWeb';
import { getCheckUsername, postJoin } from '@/apis/auth';
import { useState } from 'react';

const SignupForm = () => {
  const navigate = useNavigate();
  const [checkedUsername, setCheckedUsername] = useState('');
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

  // TODO
  // 회원명 정규식
  // 아이디 정규식
  // 비밀번호 정규식
  // 비밀번호 확인 검정 알림
  // 이메일 정규식
  // 휴대전화번호 정규식
  // 유선 전화번호 정규식
  // 부서명 정규식

  // 공백입력 막기
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // 사용자 입력값
  const handleChangeValue = e => {
    const { id, value } = e.target;

    setVendorFormData(prev => ({ ...prev, [id]: value == '' ? null : value }));
  };

  // 아이디 중복확인
  const handleCheckUsername = async () => {
    if (vendorFormData.username.length <= 4) {
      alert('잘못된 형식입니다.');
      return;
    }

    let isChecked = true;
    try {
      isChecked = (await getCheckUsername(vendorFormData.username)).data;
      console.log('!----아이디 중복확인 성공----!'); // 삭제예정
    } catch (err) {
      console.error('axiosJoin => ', err.response.data);
    }

    // false면 중복된 아이디 없다.
    if (isChecked === false) {
      setCheckedUsername(vendorFormData.username);
      alert('Tmp : 사용가능한 아이디 입니다.');
    } else {
      setCheckedUsername('');
      alert('Tmp : 사용 불가능한 아이디 입니다.');
    }
  };

  // 회원가입
  const handleSubmit = async () => {
    // 아이디 중복확인 여부 확인
    if (vendorFormData.username !== checkedUsername) {
      alert('Tmp : 아이디 중복확인을 진행해주세요');
      return;
    }

    // 비밀번호 확인
    if (vendorFormData.password !== vendorFormData.passwordCheck) {
      alert('Tmp : 비밀번호가 다릅니다.');
      return;
    }
    const { passwordCheck, ...dataWithoutPasswordCheck } = vendorFormData;

    if (!isSignupBtnActive()) {
      alert('Tmp : 입력값을 채워주세요');
      return;
    }

    axiosJoin(dataWithoutPasswordCheck);
  };

  // 회원가입 버튼 활성화
  const isSignupBtnActive = () => {
    const { name, username, password, email, phone, department } = vendorFormData;

    return name && username && password && email && phone && department;
  };

  // 회원가입 API
  const axiosJoin = async data => {
    try {
      const res = await postJoin(data);
      console.log('!----회원가입 성공----!'); // 삭제예정
      navigate('/login');
    } catch (err) {
      console.error('axiosJoin => ', err.response.data);
    }
  };

  return (
    <div className='absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 h-full flex flex-col justify-around items-center py-16'>
      <p className='text-white font-900 text-3xl'>Hyosung CMS #</p>
      <div className='text-white font-700 text-sm flex flex-col items-center'>
        <p>안녕하세요 효성CMS+입니다.</p>
        <p>아이디 만들기는 효성 FMS의 미리 계약된 고객만</p>
        <p>진행하실 수 있습니다.</p>
      </div>
      <div className='h-580 w-680 shadow-modal bg-white rounded-xl p-6 flex flex-col items-center justify-around relative'>
        <p className='text-text_black text-xl  font-800'>아이디 만들기</p>
        <div className='w-full justify-between flex'>
          <InputWeb
            id='name'
            label='회원명'
            type='text'
            placeholder='회원명(한글, 영문 대소문자 1~40)'
            required
            classContainer='w-1/2 mr-5'
            classLabel='text-sm'
            classInput='py-3'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
          <div className='w-1/2 flex items-end'>
            <InputWeb
              id='username'
              label='아이디'
              type='text'
              placeholder='아이디(영문, 숫자 조합 5~20)'
              required
              classContainer='w-full'
              classLabel='text-sm'
              classInput='py-3'
              onChange={handleChangeValue}
              onKeyDown={handleKeyDown}
            />
            <button
              className={`ml-3  w-32 rounded-lg text-white text-sm font-700 h-46
                ${vendorFormData.username.length > 4 ? 'bg-mint hover:bg-mint_hover' : 'bg-btn_disa'}  `}
              onClick={handleCheckUsername}>
              중복확인
            </button>
          </div>
        </div>
        <div className='w-full justify-between flex  '>
          <InputWeb
            id='password'
            label='비밀번호'
            type={showPassword ? 'text' : 'password'}
            placeholder='비밀번호(숫자, 영문, 특수문자 조합 8 ~ 16)'
            required
            classContainer='w-1/2 mr-5'
            classLabel='text-sm'
            classInput='py-3'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            autoComplete='off'
          />
          <InputWeb
            id='passwordCheck'
            label='비밀번호 확인'
            type={showPassword ? 'text' : 'password'}
            placeholder='비밀번호(숫자, 영문, 특수문자 조합 8 ~ 16)'
            required
            classContainer='w-1/2'
            classLabel='text-sm'
            classInput='py-3'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            autoComplete='off'
          />
        </div>
        <div className='w-full justify-between flex'>
          <InputWeb
            id='email'
            label='이메일'
            type='text'
            placeholder='ex) example@gmail.com'
            required
            classContainer='w-1/2 mr-5'
            classLabel='text-sm'
            classInput='py-3'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
          <InputWeb
            id='phone'
            label='휴대전화번호'
            type='text'
            placeholder='ex) 010-9999-9999'
            required
            classContainer='w-1/2'
            classLabel='text-sm'
            classInput='py-3'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
        </div>
        <div className='w-full justify-between flex'>
          <InputWeb
            id='department'
            label='부서명'
            type='text'
            placeholder='부서명(모든 조합 1~40)'
            required
            classContainer='w-1/2 mr-5'
            classLabel='text-sm'
            classInput='py-3'
            onChange={handleChangeValue}
          />
          <InputWeb
            id='homePhone'
            label='유선전화번호'
            type='text'
            placeholder='ex) 02-432-7777'
            classContainer='w-1/2'
            classLabel='text-sm'
            classInput='py-3'
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
          />
        </div>

        <div className='text-sm font-700'>
          <span className='text-text_black'>이미 아이디가 있으신가요? </span>
          <span
            className='text-mint underline cursor-pointer'
            onClick={() => {
              navigate('/login');
            }}>
            로그인
          </span>
        </div>
        <button
          className={`px-6 py-3 rounded-lg text-white text-sm font-700 absolute right-6 bottom-8 cursor-pointer
            ${isSignupBtnActive() ? 'bg-mint hover:bg-mint_hover' : 'bg-btn_disa '}`}
          onClick={handleSubmit}>
          아이디 만들기
        </button>
      </div>
    </div>
  );
};

export default SignupForm;
