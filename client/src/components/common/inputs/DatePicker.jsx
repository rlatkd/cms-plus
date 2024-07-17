import Arrow from '@/assets/Arrow';
import { useState } from 'react';
import triangle from '@/assets/triangle.svg';

const DatePicker = ({ selectedDate, onDateChange, isOpen, onToggle }) => {
  const [currentDate, setCurrentDate] = useState(
    selectedDate ? new Date(selectedDate) : new Date()
  );
  const [showYearSelect, setShowYearSelect] = useState(false);

  const daysInMonth = (year, month) => new Date(year, month + 1, 0).getDate();
  const firstDayOfMonth = (year, month) => new Date(year, month, 1).getDay();

  const renderCalendar = () => {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const days = daysInMonth(year, month);
    const firstDay = firstDayOfMonth(year, month);

    const calendar = [];
    for (let i = 0; i < firstDay; i++) {
      calendar.push(<div key={`empty-${i}`} className='h-8' />);
    }

    for (let day = 1; day <= days; day++) {
      calendar.push(
        <div
          key={day}
          className={`h-8 flex items-center justify-center cursor-pointer rounded-full 
            hover:bg-mint hover:text-white font-700 text-text_black text-sm ${
              selectedDate &&
              day === selectedDate.getDate() &&
              month === selectedDate.getMonth() &&
              year === selectedDate.getFullYear()
                ? 'bg-mint text-white'
                : ''
            }`}
          onClick={() => handleDateClick(day)}>
          {day}
        </div>
      );
    }

    return calendar;
  };

  const handleDateClick = day => {
    const newDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
    console.log(newDate);
    onDateChange(newDate);
    onToggle();
  };

  const handlePrevMonth = () => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1));
  };

  const handleNextMonth = () => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1));
  };

  const handleYearChange = year => {
    setCurrentDate(new Date(year, currentDate.getMonth(), 1));
    setShowYearSelect(false);
  };

  const renderYearOptions = () => {
    const currentYear = new Date().getFullYear();
    const years = [];
    for (let i = currentYear - 10; i <= currentYear + 10; i++) {
      years.push(
        <div
          key={i}
          className={`cursor-pointer p-2 hover:bg-gray-200 ${
            i === currentDate.getFullYear() ? 'bg-mint text-white' : ''
          }`}
          onClick={() => handleYearChange(i)}>
          {i}
        </div>
      );
    }
    return years;
  };

  if (!isOpen) return null;

  return (
    <div className='absolute top-full left-1/2 transform -translate-x-1/2 w-72 p-2 mt-1 text-text_black font-800 bg-white rounded-lg shadow-modal'>
      <div className='flex justify-between items-center px-4 py-2 '>
        <div className='flex items-center'>
          <div className='relative mr-3'>
            <span className='' onClick={() => setShowYearSelect(!showYearSelect)}>
              {currentDate.getFullYear()}년{' '}
              {currentDate.toLocaleString('default', { month: 'long' })}
            </span>
            {showYearSelect && (
              <div className='absolute top-full left-1/2 transform -translate-x-1/2 w-32 bg-white border border-gray-200 rounded-lg shadow-lg max-h-48 overflow-y-auto z-10'>
                {renderYearOptions()}
              </div>
            )}
          </div>
          <img
            src={triangle}
            alt='choice_year'
            className='h-4 w-4 cursor-pointer'
            onClick={() => setShowYearSelect(!showYearSelect)}
          />
        </div>

        <div className='flex items-center '>
          <Arrow rotation={'-90'} onClick={handlePrevMonth} className='cursor-pointer ' />
          <Arrow rotation={'90'} onClick={handleNextMonth} className='cursor-pointer ml-2' />
        </div>
      </div>
      <div className='grid grid-cols-7 gap-1 p-2'>
        {['일', '월', '화', '수', '목', '금', '토'].map(day => (
          <div key={day} className='text-center'>
            {day}
          </div>
        ))}
        {renderCalendar()}
      </div>
    </div>
  );
};

export default DatePicker;
