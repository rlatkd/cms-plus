const TableRow = ({ item, cols, itemKey, selection, handleClickCheckBox }) => (
  <tr
    className={`text-text_black hover:bg-gray-200 cursor-pointer
      ${selection.includes(item[itemKey]) && 'bg-gray-200'}
    `}>
    <td className='p-4'>
      <input
        type='checkbox'
        checked={selection.includes(item[itemKey])}
        onChange={() => handleClickCheckBox(item[itemKey])}
      />
    </td>

    {cols.map(col => (
      <td key={col} className='p-4'>
        {item[col]}
      </td>
    ))}
  </tr>
);

export default TableRow;
