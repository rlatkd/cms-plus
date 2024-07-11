import { useState } from 'react';
import TableRow from './TableRow';
import TableCol from './TableCol';
import TableSearch from './TableSearch';

const Table = ({ cols, search, items, handleSearchChange, show }) => {
  const [selection, setSelection] = useState([]);
  const itemKey = cols[0];

  // 체크박스 하나 선택
  const handleClickCheckBox = value => {
    let newSelection = [...selection];
    if (newSelection.includes(value)) {
      newSelection = newSelection.filter(item => item !== value);
    } else {
      newSelection.push(value);
    }
    setSelection(newSelection);
  };

  // 모든 체크박스 선택
  const handleClickCheckBoxAll = e => {
    if (e.target.checked) {
      const allCheckedSelection = items.map(item => item[itemKey]);
      setSelection(allCheckedSelection);
    } else {
      setSelection([]);
    }
  };

  const isSelectedAll = () => {
    return selection.length === items.length;
  };

  return (
    <table className=' w-full '>
      <TableCol
        cols={cols}
        isSelectedAll={isSelectedAll}
        handleClickCheckBoxAll={handleClickCheckBoxAll}
      />
      <tbody>
        <TableSearch search={search} handleSearchChange={handleSearchChange} show={show} />
        {items.map((item, idx) => (
          <TableRow
            key={idx}
            item={item}
            cols={cols}
            itemKey={itemKey}
            selection={selection}
            handleClickCheckBox={handleClickCheckBox}
          />
        ))}
      </tbody>
    </table>
  );
};

export default Table;
