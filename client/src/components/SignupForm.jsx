import { useNavigate } from 'react-router-dom';
import InputWeb from './common/inputs/InputWeb';
import { getCheckUsername, postJoin } from '@/apis/auth';
import { useState } from 'react';
import { formatPhone, removeDashes } from '@/utils/format/formatPhone';
import { validateField } from '@/utils/validators';
import useAlert from '@/hooks/useAlert';

const SignupForm = () => {
  const [checkedUsername, setCheckedUsername] = useState('');
  const [vendorFormData, setVendorFormData] = useState({
    name: '',
    username: '',
    password: '',
    passwordCheck: '',
    email: '',
    phone: '',
    department: '',
    homePhone: '',
  });
  const navigate = useNavigate();
  const onAlert = useAlert();

  // <----- 사용자 입력값 ----->
  const handleChangeValue = e => {
    const { id, value } = e.target;
    if (id === 'phone' || id === 'homePhone') {
      setVendorFormData(prev => ({ ...prev, [id]: removeDashes(value == '' ? '' : value) }));
    } else {
      setVendorFormData(prev => ({ ...prev, [id]: value == '' ? '' : value }));
    }
  };

  // <----- 공백입력 막기 ----->
  const handleKeyDown = e => {
    e.key === ' ' && e.preventDefault();
  };

  // <----- 아이디 중복확인 ----->
  const handleCheckUsername = async () => {
    let isChecked = true;
    try {
      const res = await getCheckUsername(vendorFormData.username);
      isChecked = res.data;
      console.log('!----아이디 중복확인 성공----!'); // 삭제예정
      // false면 중복된 아이디 없다.
      if (isChecked === false) {
        setCheckedUsername(vendorFormData.username);
        onAlert({ msg: '사용 가능한 아이디입니다.', type: 'success' });
      } else {
        setCheckedUsername('');
        onAlert({ msg: '이미 사용 중인 아이디입니다.', type: 'error' });
      }
    } catch (err) {
      console.error('axiosJoin => ', err.response);
    }
  };

  // <----- 회원가입 ----->
  const handleSubmit = async () => {
    // 아이디 중복확인 여부 확인
    if (vendorFormData.username !== checkedUsername) {
      onAlert({ msg: '아이디 중복확인을 진행해주세요.', type: 'error' });
      return;
    }

    const { passwordCheck, ...dataWithoutPasswordCheck } = vendorFormData;
    axiosJoin(dataWithoutPasswordCheck);
  };

  // <----- 회원가입 API ----->
  const axiosJoin = async data => {
    try {
      const res = await postJoin(data);
      console.log('!----회원가입 성공----!'); // 삭제예정
      onAlert({ msg: '회원가입에 성공하셨습니다!', type: 'success', title: '회원가입 성공' });
      navigate('/login');
    } catch (err) {
      // 에러 alert 추가하기
      onAlert({ type: 'error', err: err });
      console.error('axiosJoin => ', err.response);
    }
  };

  // <----- 회원가입 버튼 활성화 ----->
  const isSignupBtnActive = () => {
    // 각 필드의 유효성 검사진행
    const isValidName = validateField('name', vendorFormData.name);
    const isValidUsername = validateField('username', vendorFormData.username);
    const isValidPassword = validateField('password', vendorFormData.password);
    const isValidPasswordCheck = vendorFormData.password === vendorFormData.passwordCheck;
    const isValidEmail = validateField('email', vendorFormData.email);
    const isValidPhone = validateField('phone', vendorFormData.phone);
    const isValidDepartment = validateField('department', vendorFormData.department);
    const isValidHomePhone =
      !vendorFormData.homePhone ||
      (vendorFormData.homePhone && validateField('homePhone', vendorFormData.homePhone));

    // 모든 필드가 유효한지 확인
    return (
      isValidName &&
      isValidUsername &&
      isValidPassword &&
      isValidPasswordCheck &&
      isValidEmail &&
      isValidPhone &&
      isValidDepartment &&
      isValidHomePhone
    );
  };

  return (
    <div className='absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 h-full flex flex-col justify-around items-center py-16'>
      <p className='text-white font-900 text-3xl'>Hyosung CMS #</p>
      <div className='text-white font-700 text-sm flex flex-col items-center '>
        <p>안녕하세요 효성CMS+입니다.</p>
        <p className='my-1'>아이디 만들기는 효성 FMS의 미리 계약된 고객만</p>
        <p>진행하실 수 있습니다.</p>
      </div>
      <div className='h-580 w-720 shadow-modal bg-white rounded-xl p-6 flex flex-col items-center justify-around relative'>
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
            classInput='py-3 placeholder:text-xs'
            value={vendorFormData.name}
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            maxLength={40}
            isValid={validateField('name', vendorFormData.name)}
            errorMsg='올바른 형식 아닙니다.'
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
              classInput='py-3 placeholder:text-xs'
              value={vendorFormData.username}
              onChange={handleChangeValue}
              onKeyDown={handleKeyDown}
              maxLength={20}
              isValid={validateField('username', vendorFormData.username)}
              errorMsg='올바른 형식 아닙니다.'
            />
            <button
              className={`ml-3  w-32 rounded-lg text-white text-sm font-700 h-46
                ${
                  validateField('username', vendorFormData.username)
                    ? 'bg-mint hover:bg-mint_hover transition-all duration-200 '
                    : 'bg-btn_disa'
                }  `}
              tabIndex='-1'
              onClick={handleCheckUsername}>
              중복확인
            </button>
          </div>
        </div>
        <div className='w-full justify-between flex  '>
          <InputWeb
            id='password'
            label='비밀번호'
            type='password'
            placeholder='비밀번호(숫자, 영문, 특수문자 조합 8 ~ 16)'
            required
            classContainer='w-1/2 mr-5'
            classLabel='text-sm'
            classInput='py-3  placeholder:text-xs'
            value={vendorFormData.password}
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            autoComplete='off' // !CHECK! 주석해제필요
            maxLength={16}
            isValid={validateField('password', vendorFormData.password)}
            errorMsg='올바른 형식 아닙니다.'
          />
          <InputWeb
            id='passwordCheck'
            label='비밀번호 확인'
            type='password'
            placeholder='비밀번호(숫자, 영문, 특수문자 조합 8 ~ 16)'
            required
            classContainer='w-1/2'
            classLabel='text-sm'
            classInput='py-3  placeholder:text-xs'
            value={vendorFormData.passwordCheck}
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            autoComplete='off' // !CHECK! 주석해제필요
            maxLength={16}
            isValid={vendorFormData.password === vendorFormData.passwordCheck}
            errorMsg='비밀번호가 일치하지 않습니다.'
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
            classInput='py-3  placeholder:text-xs'
            value={vendorFormData.email}
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            maxLength={40}
            isValid={validateField('email', vendorFormData.email)}
            errorMsg='올바른 형식 아닙니다.'
          />
          <InputWeb
            id='phone'
            label='휴대전화번호'
            type='text'
            placeholder='숫자만 입력해주세요.'
            required
            classContainer='w-1/2'
            classLabel='text-sm'
            classInput='py-3  placeholder:text-xs'
            value={formatPhone(vendorFormData.phone)}
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            maxLength={11}
            isValid={validateField('phone', vendorFormData.phone)}
            errorMsg='올바른 형식 아닙니다.'
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
            classInput='py-3  placeholder:text-xs'
            value={vendorFormData.department}
            onChange={handleChangeValue}
            maxLength={40}
            isValid={validateField('department', vendorFormData.department)}
            errorMsg='올바른 형식 아닙니다.'
          />
          <InputWeb
            id='homePhone'
            label='유선전화번호'
            type='text'
            placeholder='숫자만 입력해주세요.'
            classContainer='w-1/2'
            classLabel='text-sm'
            classInput='py-3  placeholder:text-xs'
            value={formatPhone(vendorFormData.homePhone)}
            onChange={handleChangeValue}
            onKeyDown={handleKeyDown}
            maxLength={10}
            isValid={validateField('homePhone', vendorFormData.homePhone)}
            errorMsg='올바른 형식 아닙니다.'
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
          disabled={!isSignupBtnActive()}
          className={`px-6 py-3 rounded-lg text-white text-sm font-700 absolute right-6 bottom-8 
            ${isSignupBtnActive() ? 'bg-mint hover:bg-mint_hover transition-all duration-200' : 'bg-btn_disa '}`}
          onClick={handleSubmit}>
          아이디 만들기
        </button>
      </div>
    </div>
  );
};

export default SignupForm;
