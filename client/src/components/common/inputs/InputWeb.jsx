import Search from '@/assets/Search';
import { useState, useRef } from 'react';
import PostCodeModal from '@/components/vendor/modal/PostCodeModal';
import openeye from '@/assets/openeye.svg';
import closeeye from '@/assets/closeeye.svg';
import Timer from '../Timer';

const InputWeb = ({
  id,
  label,
  required,
  disabled,
  readOnly,
  type = 'text',
  value,
  placeholder,
  classContainer = '',
  classLabel = '',
  classInput = '',
  isValid = true, // 유효성여부 boolean
  errorMsg, // 유효성탈락 메세지
  time,
  setTime,
  handleChangeAddress,
  ...props
}) => {
  const [showPassword, setShowPassword] = useState(false);
  const [isAddressModalOpen, setIsAddressModalOpen] = useState(false);
  const inputRef = useRef(null);

  // <----- 비밀번호 표시 여부 ----->
  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  // <----- 주소 찾기 입력창 ----->
  const handleSearchAddress = () => {
    if (disabled) return;
    setIsAddressModalOpen(true);
  };

  // <----- 주소 선택 시 핸들러 ----->
  const handleSelectAddress = data => {
    handleChangeAddress('zipcode', data.zonecode);
    handleChangeAddress('address', data.address);
    setIsAddressModalOpen(false);
  };

  return (
    <div className={`${classContainer}`}>
      {label && (
        <label
          className={`${classLabel} block text-text_black text-15 font-700 mb-2 ml-2 
                    ${required ? "after:ml-1 after:text-red-500 after:content-['*']" : ''}`}
          htmlFor={id}>
          {label}
        </label>
      )}
      <div className='relative'>
        <input
          ref={inputRef}
          className={`${classInput} placeholder:text-text_grey text-black border rounded-lg w-full  focus:outline-none
                    ${disabled && 'bg-ipt_disa '} border-ipt_border focus:ring-1 placeholder:text-sm text-sm p-4 
                    ${value && !isValid ? ` ring-negative border-negative` : 'focus:ring-mint focus:border-mint'} `}
          id={id}
          value={value}
          disabled={disabled}
          readOnly={readOnly}
          type={type !== 'password' ? type : showPassword ? 'text' : 'password'}
          placeholder={placeholder}
          {...props}
        />

        {/* 유효성검사 */}
        {(value || value !== 0) && !isValid && (
          <p className='absolute text-negative text-[13px] ml-2 -bottom-6'>{errorMsg}</p>
        )}

        {/* 비밀번호 입력창 */}
        {type === 'password' && (
          <img
            src={showPassword ? openeye : closeeye}
            alt='Toggle password visibility'
            className='absolute top-1/2 transform -translate-y-1/2 right-3 cursor-pointer w-6'
            onClick={handleTogglePassword}
            tabIndex='-1'
          />
        )}

        {/* 주소입력창 */}
        {type === 'address' && (
          <div onClick={handleSearchAddress}>
            <Search
              classSearch='absolute top-1/2 transform -translate-y-1/2 right-3 cursor-pointer w-6 h-6'
              fill={'#C7CCD0'}
              tabIndex='-1'
            />
            <PostCodeModal
              isOpen={isAddressModalOpen}
              onClose={() => setIsAddressModalOpen(false)}
              handleSelectAddress={handleSelectAddress}
            />
          </div>
        )}

        {/* 인증번호 타이머 */}
        {time !== 0 && time && (
          <Timer
            time={time}
            setTime={setTime}
            style='absolute top-4 right-5 text-negative text-15 font-700'
          />
        )}
      </div>
    </div>
  );
};

export default InputWeb;
