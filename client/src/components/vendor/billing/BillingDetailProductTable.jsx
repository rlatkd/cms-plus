import Remove from '@/assets/Remove';
import TableCol from '@/components/common/tables/TableCol';
import { useState } from 'react';

const BillingDetailProductTable = ({
  cols = [],
  data = [],
  editable = true,
  handleInputChange,
  handleRemove,
}) => {
  const [selection, setSelection] = useState([]);
  const [editingState, setEditingState] = useState({});

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
  };

  // 모든 체크박스 선택
  const handleClickCheckBoxAll = e => {
    if (e.target.checked) {
      const allCheckedSelection = [...data];
      setSelection(allCheckedSelection);
    } else {
      setSelection([]);
    }
  };

  const isSelectedAll = () => {
    return selection.length != 0 && selection.length === data.length;
  };

  const handleEditClick = (idx, field) => {
    if (editable) {
      setEditingState(prev => ({ ...prev, [idx]: { ...prev[idx], [field]: true } }));
    }
  };

  const handleBlur = (idx, field) => {
    setEditingState(prev => ({ ...prev, [idx]: { ...prev[idx], [field]: false } }));
  };

  return (
    <div className=''>
      <table className='w-full'>
        <TableCol
          cols={cols}
          isSelectedAll={isSelectedAll}
          handleClickCheckBoxAll={handleClickCheckBoxAll}
        />
        <tbody className='bg-white divide-y divide-gray-200'>
          {data &&
            data.map((item, idx) => {
              return (
                <tr
                  key={idx}
                  className={`text-text_black flex items-center border-b border-ipt_border ${selection.includes(item) && 'bg-gray-200'}`}>
                  <td className={`w-16 py-4 text-sm font-700 flex justify-center`}>
                    <input
                      className='w-4 h-4'
                      type='checkbox'
                      checked={selection.includes(item)}
                      onChange={() => handleClickCheckBox(item)}
                      onClick={e => e.stopPropagation()}
                    />
                  </td>
                  <td className={`w-16 py-4 text-sm font-700 flex justify-center`}> {idx + 1} </td>
                  <td className={`w-2/12 py-4 text-sm font-700 flex justify-center`}>
                    {' '}
                    {item.name}{' '}
                  </td>
                  <td
                    className={`w-2/12 py-4 text-sm font-700 flex justify-center`}
                    onClick={() => handleEditClick(idx, 'price')}>
                    {editable && editingState[idx]?.price ? (
                      <input
                        type='number'
                        value={item.price}
                        onChange={e => handleInputChange(idx, 'price', e.target.value)}
                        onBlur={() => handleBlur(idx, 'price')}
                        className='text-center w-3/4 p-4'
                        autoFocus
                      />
                    ) : (
                      <div className={`${editable && 'border rounded-lg border-ipt_border'} p-4 w-3/4 text-center`}>{`${item.price.toLocaleString()}원`}</div>
                    )}
                  </td>
                  <td
                    className={`w-2/12 py-4 text-sm font-700 flex justify-center`}
                    onClick={() => handleEditClick(idx, 'quantity')}>
                    {editable && editingState[idx]?.quantity ? (
                      <input
                        type='number'
                        value={item.quantity}
                        onChange={e => handleInputChange(idx, 'quantity', e.target.value)}
                        onBlur={() => handleBlur(idx, 'quantity')}
                        className='text-center w-3/4 p-4'
                        autoFocus
                      />
                    ) : (
                        <div className={`${editable && 'border rounded-lg border-ipt_border'} p-4 w-3/4 text-center`}>{`${item.quantity.toLocaleString()}개`}</div>
                    )}
                  </td>
                  <td className={`w-2/12 py-4 text-sm font-700 flex justify-center`}>
                    {`${(item.quantity * item.price).toLocaleString()}원`}
                  </td>
                  {editable && (
                    <td className={`w-1/12 py-4 text-sm font-700 flex justify-center`}>
                      <button onClick={() => handleRemove(idx)}>
                        <Remove />
                      </button>
                    </td>
                  )}
                </tr>
              );
            })}
        </tbody>
      </table>
    </div>
  );
};

export default BillingDetailProductTable;
