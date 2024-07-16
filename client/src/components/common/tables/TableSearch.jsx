import searchIcon from '@/assets/search.svg';

const TableSearch = ({ search, handleSearchChange, onSearchClick }) => {
  return (
    <tr className='text-sm flex'>
      {search.map((searchItem, idx) => (
        <td
          key={idx}
          className={`text-left py-4 flex items-center border-b border-ipt_border 
          ${searchItem.key === 'checkbox' || searchItem.key === 'No' ? 'w-16' : 'flex-1'}`}>
          {searchItem.type === 'select' ? (
            <select
              className='w-10/12 h-full px-3 border border-text_black rounded-md '
              value={searchItem.value}
              onChange={e => {
                handleSearchChange(searchItem.key, e.target.value);
              }}>
              <option value=''>선택</option>
              {searchItem.options &&
                searchItem.options.map((option, idx) => (
                  <option key={idx} value={option}>
                    {option}
                  </option>
                ))}
            </select>
          ) : searchItem.type === 'hidden' ? (
            <div />
          ) : (
            <div className='relative w-10/12 h-full flex items-center '>
              <input
                className='px-3 py-1 w-full border border-text_black rounded-md 
                        focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm'
                type={searchItem.type}
                value={searchItem.value}
                onChange={e => handleSearchChange(searchItem.key, e.target.value)}
              />
              {idx > 1 && ( // 2번 컬럼부터 돋보기 버튼 보이게
                <button
                  className='absolute right-2 top-1/2 transform -translate-y-1/2'
                  onClick={onSearchClick}>
                  <img src={searchIcon} alt='search' className='w-5 h-5' />
                </button>
              )}
            </div>
          )}
        </td>
      ))}
    </tr>
  );
};

export default TableSearch;
