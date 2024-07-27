import Checkbox from '../inputs/CheckBox';

const TableCol = ({ cols, isSelectedAll, handleClickCheckBoxAll }) => (
  <thead>
    <tr className='bg-table_col text-text_black shadow-column flex items-center '>
      <th className='py-4 w-16 flex justify-center'>
        {/* <input
          className='w-4 h-4'
          type='checkbox'
          checked={isSelectedAll()}
          onChange={handleClickCheckBoxAll}
        /> */}
        <Checkbox
          classBox='h-4 w-4 rounded-sm'
          checked={isSelectedAll()}
          onChange={e => handleClickCheckBoxAll(e)}
        />
      </th>
      {cols.map((col, idx) => (
        <th
          key={idx}
          className={`flex justify-center  ${col.key === 'order' ? 'w-16 pl-2' : `${col.width}`} 
            font-900`}>
          {col.label}
        </th>
      ))}
    </tr>
  </thead>
);

export default TableCol;
