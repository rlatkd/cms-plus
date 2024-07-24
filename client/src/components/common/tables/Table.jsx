import { useState } from 'react';
import TableRow from './TableRow';
import TableCol from './TableCol';
import TableSearch from './TableSearch';

const Table = ({
  cols,
  search,
  rows,
  currentPage,
  handleChangeSearch,
  onRowClick,
  handleClickSearch,
  handleChangeSelection = newSelection => {},
}) => {
  const [selection, setSelection] = useState([]);
  const itemKey = cols[0].key;

  // 체크박스 하나 선택
  const handleClickCheckBox = value => {
    let newSelection = [...selection];

    console.log('selection', selection);
    if (newSelection.includes(value)) {
      newSelection = newSelection.filter(item => item !== value);
    } else {
      newSelection.push(value);
    }
    setSelection(newSelection);
    handleChangeSelection(newSelection);
  };

  // 모든 체크박스 선택
  const handleClickCheckBoxAll = e => {
    if (e.target.checked) {
      const allCheckedSelection = rows.map(item => item);
      setSelection(allCheckedSelection);
      handleChangeSelection(allCheckedSelection);
    } else {
      setSelection([]);
      handleChangeSelection([]);
    }
  };

  const isSelectedAll = () => {
    return selection.length != 0 && selection.length === rows.length;
  };

  return (
    <table className='w-full h-full'>
      <TableCol
        cols={cols}
        isSelectedAll={isSelectedAll}
        handleClickCheckBoxAll={handleClickCheckBoxAll}
      />
      <tbody>
        {search && (
          <TableSearch
            search={search}
            handleChangeSearch={handleChangeSearch}
            handleClickSearch={handleClickSearch}
          />
        )}
        {rows.map((row, index) => (
          <TableRow
            key={index}
            index={index}
            row={row}
            cols={cols}
            currentPage={currentPage}
            itemKey={itemKey}
            selection={selection}
            handleClickCheckBox={handleClickCheckBox}
            onRowClick={onRowClick} // onRowClick 이벤트 전달
          />
        ))}
      </tbody>
    </table>
  );
};

export default Table;
