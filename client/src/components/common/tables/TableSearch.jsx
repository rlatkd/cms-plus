import searchIcon from '@/assets/search.svg';
import calender from '@/assets/calender.svg';
import triangle from '@/assets/triangle.svg';

const TableSearch = ({ search, handleChangeSearch, handlehClickSearch }) => {
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
                  handlehClickSearch();
                }}>
                <option value=''>선택</option>
                {searchItem.options &&
                  searchItem.options.map((option, idx) => (
                    <option key={idx} value={option}>
                      {option}
                    </option>
                  ))}
              </select>
              <div className='pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-text_grey'>
                <svg
                  className='fill-text_grey h-6 w-6'
                  xmlns='http://www.w3.org/2000/svg'
                  viewBox='0 0 20 20'>
                  <path d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z' />
                </svg>
              </div>
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
                onClick={handlehClickSearch}>
                <img
                  src={
                    (searchItem.type === 'text' && searchIcon) ||
                    (searchItem.type === 'calendar' && calender) ||
                    (searchItem.type === 'num' && triangle)
                  }
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
