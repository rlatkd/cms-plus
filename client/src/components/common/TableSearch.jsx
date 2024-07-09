const TableSearch = ({ search, handleSearchChange, show }) => {
  return (
    <tr className='text-sm'>
      {search.map(searchItem => (
        <th className='text-left py-3'>
          {searchItem.type === 'select' ? (
            <select
              className='px-3 py-1  border border-text_grey rounded-md'
              value={searchItem.value}
              onChange={e => {
                handleSearchChange(searchItem.key, e.target.value);
                show();
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
            <input
              className='px-3 py-1 w-28 border border-text_grey rounded-md'
              type={searchItem.type}
              placeholder='입력'
              value={searchItem.value}
              onChange={e => handleSearchChange(searchItem.key, e.target.value)}
              onKeyDown={e => e.key === 'Enter' && show()}
            />
          )}
        </th>
      ))}
    </tr>
  );
};

export default TableSearch;
