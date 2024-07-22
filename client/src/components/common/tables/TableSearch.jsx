import searchIcon from '@/assets/search.svg';
import calender from '@/assets/calender.svg';
import triangle from '@/assets/triangle.svg';
import { useState } from 'react';
import DatePicker from '@/components/common/inputs/DatePicker';
import Arrow from '@/assets/Arrow';

const TableSearch = ({ search, handleChangeSearch, handleClickSearch }) => {
  const [selectedDate, setSelectedDate] = useState('');
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);

  return (
    <tr className='text-sm flex'>
      {search.map((searchItem, idx) => (
        <td
          key={idx}
          className={`py-4 flex items-center justify-center border-b border-ipt_border   
          ${searchItem.key === 'checkbox' || searchItem.key === 'order' ? 'w-16' : `${searchItem.width}`} `}>
          {searchItem.type === 'hidden' ? (
            <div />
          ) : searchItem.type === 'select' ? (
            <div className='relative w-9/12 flex items-center  '>
              <select
                className='block appearance-none px-3 py-1 w-full border border-text_black rounded-md  
                        focus:border-text_black focus:outline-none focus:ring-1 focus:ring-text_black sm:text-sm'
                value={searchItem.value}
                onChange={e => {
                  handleChangeSearch(searchItem.key, e.target.value);
                }}>
                <option value=''>선택</option>
                {searchItem.options &&
                  searchItem.options.map((option, idx) => (
                    <option key={idx} value={option.key}>
                      {option.value}
                    </option>
                  ))}
              </select>

              <div className='pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-text_grey'>
                <Arrow fill='#7B809A' tabIndex='-1' />
              </div>
            </div>
          ) : searchItem.type === 'calendar' ? (
            <div className='relative w-9/12 flex items-center  '>
              <input
                className='px-3 py-1 w-full border border-text_black rounded-md 
                        focus:border-text_black focus:outline-none focus:ring-1 focus:ring-text_black sm:text-sm'
                type='text'
                value={selectedDate ? selectedDate.toISOString().split('T')[0] : ''}
                readOnly
              />

              <button
                className='absolute right-2 top-1/2 transform -translate-y-1/2'
                onClick={() => {
                  setSelectedDate('');
                  handleChangeSearch(searchItem.key, '');
                  setIsCalendarOpen(!isCalendarOpen);
                }}
                tabIndex='-1'>
                <img src={calender} alt='search' className='w-5 h-5' />
              </button>

              <DatePicker
                selectedDate={selectedDate}
                onDateChange={date => {
                  setSelectedDate(date);
                  handleChangeSearch(searchItem.key, date.toISOString().split('T')[0]);
                }}
                isOpen={isCalendarOpen}
                onToggle={() => setIsCalendarOpen(!isCalendarOpen)}
              />
            </div>
          ) : (
            <div className='relative w-9/12 flex items-center  '>
              <input
                className='px-3 py-1 w-full border border-text_black rounded-md 
                        focus:border-text_black focus:outline-none focus:ring-1 focus:ring-text_black sm:text-sm'
                type={searchItem.type}
                value={searchItem.value}
                onChange={e => handleChangeSearch(searchItem.key, e.target.value)}
              />

              <button
                className='absolute right-2 top-1/2 transform -translate-y-1/2'
                onClick={handleClickSearch}
                tabIndex='-1'>
                <img
                  src={searchItem.type === 'text' ? searchIcon : triangle}
                  alt='search'
                  className='w-5 h-5'
                />
              </button>
            </div>
          )}
        </td>
      ))}
    </tr>
  );
};

export default TableSearch;
