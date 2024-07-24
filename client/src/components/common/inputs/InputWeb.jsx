import Search from '@/assets/Search';
import { useState, useRef } from 'react';
import PostCodeModal from '@/components/vendor/modal/PostCodeModal';

const InputWeb = ({
  id,
  label,
  required,
  disabled,
  readOnly,
  type = 'text',
  placeholder,
  classContainer = '',
  classLabel = '',
  classInput = '',
  handleChangeAddress,
  ...props
}) => {
  const [showPassword, setShowPassword] = useState(false);

  const [isAddressModalOpen, setIsAddressModalOpen] = useState(false);
  const inputRef = useRef(null);

  // <------ 비밀번호 표시 여부 ------>
  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  // <------ 주소 찾기 입력창 ------>
  const handleSearchAddress = () => {
    if (disabled) return;
    setIsAddressModalOpen(true);
  };

  // <------ 주소 선택 시 핸들러 ------>
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
      <div className='relative '>
        <input
          ref={inputRef}
          className={`${classInput} placeholder:text-text_grey text-black border
                    ${disabled && 'bg-ipt_disa '} border-ipt_border focus:border-mint focus:outline-none 
                    focus:ring-mint focus:ring-1 placeholder:text-sm text-sm p-4 rounded-lg w-full`}
          id={id}
          disabled={disabled}
          readOnly={readOnly}
          type={type !== 'password' ? type : showPassword ? 'text' : 'password'}
          placeholder={placeholder}
          {...props}
        />
        {type === 'password' && (
          <img
            src={showPassword ? '/src/assets/openeye.svg' : '/src/assets/closeeye.svg'}
            alt='Toggle password visibility'
            className='absolute top-1/2 transform -translate-y-1/2 right-3 cursor-pointer w-6'
            onClick={handleTogglePassword}
            tabIndex='-1'
          />
        )}
        {type === 'address' && (
          <div onClick={handleSearchAddress}>
            <Search
              classSearch='absolute top-1/2 transform -translate-y-1/2 right-3 cursor-pointer w-6 h-6'
              fill={'#C7CCD0'}
              tabIndex='-1'
            />
          </div>
        )}
        <PostCodeModal
          isOpen={isAddressModalOpen}
          onClose={() => setIsAddressModalOpen(false)}
          handleSelectAddress={handleSelectAddress}
        />
      </div>
    </div>
  );
};

export default InputWeb;

// {type === 'calendar' && (
//   <div onClick={handleToggleCalendar}>
//     <button
//       className='absolute right-2 top-1/2 transform -translate-y-1/2'
//       onClick={() => {
//         setSelectedDate('');
//         setIsCalendarOpen(!isCalendarOpen);
//       }}
//       tabIndex='-1'>
//       <img src={calender} alt='search' className='w-5 h-5' />
//     </button>
//     <DatePicker
//       selectedDate={selectedDate}
//       onDateChange={date => {
//         setSelectedDate(date);
//       }}
//       isOpen={isCalendarOpen}
//       onToggle={() => setIsCalendarOpen(!isCalendarOpen)}
//     />
//   </div>
// )}

// <------ DatePicker 열기/닫기 ------>
// const handleToggleCalendar = () => {
//   setIsCalendarOpen(!isCalendarOpen);
// };

// const [selectedDate, setSelectedDate] = useState('');
// const [isCalendarOpen, setIsCalendarOpen] = useState(false);
