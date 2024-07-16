const TableRow = ({ item, cols, itemKey, selection, handleClickCheckBox, onRowClick }) => (
  <tr
    className={`text-text_black hover:bg-gray-200 cursor-pointer flex items-center border-b border-ipt_border
      ${selection.includes(item[itemKey]) && 'bg-gray-200'}
    `}
    onClick={() => onRowClick(item)} // onRowClick 이벤트 추가
  >
    <td className='w-16 flex justify-center'>
      <input
        className='w-4 h-4'
        type='checkbox'
        checked={selection.includes(item[itemKey])}
        onChange={() => handleClickCheckBox(item[itemKey])}
        onClick={e => e.stopPropagation()} // 체크박스 클릭할 때 row 클릭되는거 막음
      />
    </td>

    {cols.map(col => (
      <td
        key={col}
        className={` ${col === 'No' ? 'w-16 pl-3' : 'flex-1'} 
                  py-4 text-left text-sm font-700 `}>
        {item[col]}
      </td>
    ))}
  </tr>
);

export default TableRow;
