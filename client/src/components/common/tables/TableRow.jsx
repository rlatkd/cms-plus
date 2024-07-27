import Checkbox from '../inputs/CheckBox';

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
    className={`text-text_black hover:bg-ipt_disa cursor-pointer flex items-center border-b border-ipt_border 
      ${selection.includes(row) && 'bg'}
    `}>
    <td className='w-16 flex justify-center'>
      {/* <input
        className='w-4 h-4'
        type='checkbox'
        checked={selection.includes(row)}
        onChange={() => handleClickCheckBox(row)}
        onClick={e => e.stopPropagation()} // 체크박스 클릭할 때 row 클릭되는거 막음
      /> */}
      <Checkbox
        classBox='h-4 w-4 rounded-sm '
        checked={selection.includes(row)}
        onChange={() => handleClickCheckBox(row)}
        onClick={e => e.stopPropagation()}
      />
    </td>

    {cols.map((col, idx) => (
      <td
        key={idx}
        onClick={() => onRowClick(row)} // onRowClick 이벤트 추가
        className={` ${col.key === 'order' ? 'w-16' : `${col.width}`} 
                  py-4  text-sm font-700 flex justify-center`}>
        {col.key !== 'order' ? row[col.key] : index + 1 + (currentPage - 1) * 10}
      </td>
    ))}
  </tr>
);

export default TableRow;
