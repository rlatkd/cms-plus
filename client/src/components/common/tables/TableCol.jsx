const TableCol = ({ cols, isSelectedAll, handleClickCheckBoxAll }) => (
  <thead>
    <tr className='bg-table_col text-text_black shadow-column flex items-center '>
      <th className='text-left py-4 w-16 flex justify-center '>
        <input
          className='w-4 h-4'
          type='checkbox'
          checked={isSelectedAll()}
          onChange={handleClickCheckBoxAll}
        />
      </th>
      {cols.map((col, idx) => (
        <th
          key={idx}
          className={` ${col === 'No' ? 'w-16 pl-2' : 'flex-1'} 
              text-left font-900  `}>
          {col}
        </th>
      ))}
    </tr>
  </thead>
);

export default TableCol;
