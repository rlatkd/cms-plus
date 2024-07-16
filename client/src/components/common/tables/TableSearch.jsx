import searchIcon from '@/assets/search.svg';
import calender from '@/assets/calender.svg';
import triangle from '@/assets/triangle.svg';

const TableSearch = ({ search, handleChangeSearch, handlehClickSearch }) => {
  return (
    <tr className='text-sm flex'>
      {search.map((searchItem, idx) => (
        <td
          key={idx}
          className={`text-left py-4 flex items-center justify-center border-b border-ipt_border  
          ${searchItem.key === 'checkbox' || searchItem.key === 'order' ? 'w-16' : `${searchItem.width}`} `}>
          {searchItem.type === 'hidden' ? (
            <div />
          ) : searchItem.type === 'select' ? (
            <select
              className='px-3  w-9/12 border border-text_black rounded-md '
              value={searchItem.value}
              onChange={e => {
                handleChangeSearch(searchItem.key, e.target.value);
              }}>
              <option value=''>선택</option>
              {searchItem.options &&
                searchItem.options.map((option, idx) => (
                  <option key={idx} value={option}>
                    {option}
                  </option>
                ))}
            </select>
          ) : (
            <div className='relative w-9/12 flex items-center '>
              <input
                className='px-3 py-1 w-full border border-text_black rounded-md 
                        focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm'
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
