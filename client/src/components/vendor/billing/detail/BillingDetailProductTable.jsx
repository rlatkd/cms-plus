import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons';
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

  const handleClickCheckBox = value => {
    setSelection(prev =>
      prev.includes(value) ? prev.filter(item => item !== value) : [...prev, value]
    );
  };

  const handleClickCheckBoxAll = e => {
    setSelection(e.target.checked ? [...data] : []);
  };

  const isSelectedAll = () => selection.length === data.length && selection.length !== 0;

  const handleEditClick = (idx, field) => {
    if (editable) {
      setEditingState(prev => ({ ...prev, [idx]: { ...prev[idx], [field]: true } }));
    }
  };

  const handleBlur = (idx, field) => {
    setEditingState(prev => ({ ...prev, [idx]: { ...prev[idx], [field]: false } }));
  };

  const renderEditableField = (item, idx, field) => {
    const isEditing = editable && editingState[idx]?.[field];
    const value = item[field];

    return isEditing ? (
      <input
        type='number'
        value={value}
        onChange={e => handleInputChange(idx, field, e.target.value)}
        onBlur={() => handleBlur(idx, field)}
        className='text-center w-3/4 p-4 focus:border-mint focus:outline-none 
                    focus:ring-mint focus:ring-1 rounded-lg'
        autoFocus
      />
    ) : (
      <div
        className={`${editable ? 'border rounded-lg focus:border-mint focus:outline-none focus:ring-mint focus:ring-1' : ''} p-4 w-3/4 text-center`}>
        {`${value.toLocaleString()}${field === 'price' ? '원' : '개'}`}
      </div>
    );
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
          {data.map((item, idx) => (
            <tr
              key={item.billingProductId}
              className={`text-text_black flex items-center border-b border-ipt_border ${selection.includes(item) ? 'bg-gray-200' : ''}`}>
              <td className='w-16 py-4 text-sm font-700 flex justify-center'>
                <input
                  className='w-4 h-4'
                  type='checkbox'
                  checked={selection.includes(item)}
                  onChange={() => handleClickCheckBox(item)}
                  onClick={e => e.stopPropagation()}
                />
              </td>
              <td className='w-16 py-4 text-sm font-700 flex justify-center'>{idx + 1}</td>
              <td className='w-2/12 py-4 text-sm font-700 flex justify-center'>{item.name}</td>
              <td
                className='w-2/12 py-4 text-sm font-700 flex justify-center'
                onClick={() => handleEditClick(idx, 'price')}>
                {renderEditableField(item, idx, 'price')}
              </td>
              <td
                className='w-2/12 py-4 text-sm font-700 flex justify-center'
                onClick={() => handleEditClick(idx, 'quantity')}>
                {renderEditableField(item, idx, 'quantity')}
              </td>
              <td className='w-2/12 py-4 text-sm font-700 flex justify-center'>
                {`${(item.quantity * item.price).toLocaleString()}원`}
              </td>
              {editable && (
                <td className='w-1/12 py-4 text-sm font-700 flex justify-center'>
                  <button onClick={() => handleRemove(idx)}>
                    <div className='flex'>
                      <FontAwesomeIcon icon={faTrashAlt} className='text-red-500 text-xl mr-3' />
                      <p className='text-red-500 text-l font-extrabold'>삭제</p>
                    </div>
                  </button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BillingDetailProductTable;
