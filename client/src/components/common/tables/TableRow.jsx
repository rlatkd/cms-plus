const TableRow = ({
  index,
  row,
  cols,
  currentPage,
  selection,
  handleClickCheckBox,
  onRowClick,
}) => (
  <tr
    className={`text-text_black hover:bg-gray-200 cursor-pointer flex items-center border-b border-ipt_border 
      ${selection.includes(row) && 'bg-gray-200'}
    `}
    onClick={() => onRowClick(row)} // onRowClick 이벤트 추가
  >
    <td className='w-16 flex justify-center'>
      <input
        className='w-4 h-4'
        type='checkbox'
        checked={selection.includes(row)}
        onChange={() => handleClickCheckBox(row)}
        onClick={e => e.stopPropagation()} // 체크박스 클릭할 때 row 클릭되는거 막음
      />
    </td>

    {cols.map((col, idx) => (
      <td
        key={idx}
        className={` ${col.key === 'order' ? 'w-16' : `${col.width}`} 
                  py-4  text-sm font-700 flex justify-center`}>
        {col.key !== 'order' ? row[col.key] : index + 1 + (currentPage - 1) * 10}
      </td>
    ))}
  </tr>
);

export default TableRow;
