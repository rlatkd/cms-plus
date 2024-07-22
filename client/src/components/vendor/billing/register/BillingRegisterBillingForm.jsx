import React, { useState } from 'react';
import { ProductSelectField2 } from '@/components/common/selects/ProductSelectField';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faCalendar } from '@fortawesome/free-solid-svg-icons';
import DatePicker from '@/components/common/inputs/DatePicker';
import InputWeb from '@/components/common/inputs/InputWeb';
import SelectField from '@/components/common/selects/SelectField';
import { formatDate } from '@/utils/format/formatDate';

const typeOtions = [
  { value: 'REGULAR', label: '정기' },
  { value: 'IRREGULAR', label: '추가' },
];

const BillingForm = ({
  billingData,
  handleBillingDataChange,
  products,
  handleProductAdd,
  handleProductChange,
  handleProductRemove,
}) => {
  const [isCalendarOpen, setIsCalendarOpen] = useState(false);
  const [editingState, setEditingState] = useState({});

  const handleDateChange = date => {
    handleBillingDataChange('paymentDate', formatDate(date));
    setIsCalendarOpen(false);
  };

  const handleEditClick = (idx, field) => {
    setEditingState(prev => ({ ...prev, [idx]: { ...prev[idx], [field]: true } }));
  };

  const calcBillingPrice = mBillingProducts => {
    return mBillingProducts.reduce((sum, bp) => {
      return sum + bp.price * bp.quantity;
    }, 0);
  };

  const handleBlur = (idx, field) => {
    setEditingState(prev => ({ ...prev, [idx]: { ...prev[idx], [field]: false } }));
  };

  const renderEditableField = (item, idx, field) => {
    const isEditing = editingState[idx]?.[field];
    const value = item[field];

    return isEditing ? (
      <input
        type='number'
        value={value}
        onChange={e => handleProductChange(idx, field, e.target.value)}
        onBlur={() => handleBlur(idx, field)}
        className='text-center w-3/4 p-4 focus:border-mint focus:outline-none 
                    focus:ring-mint focus:ring-1 rounded-lg'
        autoFocus
      />
    ) : (
      <div
        className={`${true ? 'border rounded-lg focus:border-mint focus:outline-none focus:ring-mint focus:ring-1' : ''} p-4 w-3/4 text-center`}>
        {`${value.toLocaleString()}${field === 'price' ? '원' : '개'}`}
      </div>
    );
  };

  return (
    <form className='space-y-4'>
      <div className='flex space-x-4'>
        <div className='w-1/2'>
          <SelectField
            label='청구타입'
            name='billingType'
            required
            options={typeOtions}
            value={billingData.billingType}
            onChange={e => handleBillingDataChange('billingType', e.target.value)}
            onBlur={e => handleBillingDataChange('billingType', e.target.value)}
          />
        </div>
        <div className='relative flex items-center w-1/2'>
          <DatePicker
            selectedDate={new Date(billingData.paymentDate)}
            onDateChange={handleDateChange}
            isOpen={isCalendarOpen}
            onToggle={() => setIsCalendarOpen(!isCalendarOpen)}
          />
          <InputWeb
            id='billingDate'
            label='결제일'
            value={billingData.paymentDate}
            type='text'
            readOnly={true}
            classInput='w-full'
            classContainer='w-full'
          />
          <div
            className='cursor-pointer absolute right-3 top-11'
            onClick={() => setIsCalendarOpen(!isCalendarOpen)}>
            <FontAwesomeIcon icon={faCalendar} className='h-5 w-5 text-gray-400' />
          </div>
        </div>
      </div>
      <div className='flex justify-between mb-5'>
        <div className='w-2/6 flex-row'>
          <label className={`block text-text_black text-15 font-700 mb-2 ml-2`}>상품 추가</label>
          <ProductSelectField2
            label='상품을 선택하세요'
            options={products.map(p => ({
              value: {
                productId: p.productId,
                name: p.name,
                price: p.price,
                quantity: 1,
              },
              label: `${p.name}(${p.price.toLocaleString()}원)`,
            }))}
            selectedOptions={billingData.products.map(p => ({
              value: p,
              label: `${p.name} (${p.price.toLocaleString()}원)`,
            }))}
            onChange={newOptions => {
              handleBillingDataChange(
                'products',
                newOptions.map(option => option.value)
              );
            }}
          />
        </div>
        <div className='flex items-end'>
          <p className='font-bold text-lg'>합계:</p>
          <p className='text-right font-bold text-lg border-none'>{`${calcBillingPrice(billingData.products).toLocaleString()}원`}</p>
        </div>
      </div>
      <div className='flex flex-col h-full justify-between'>
        <table className='w-full'>
          <thead>
            <tr className='bg-gray-100'>
              <th className='p-2 text-left'>상품명</th>
              <th className='p-2 text-left'>단가</th>
              <th className='p-2 text-left'>수량</th>
              <th className='p-2 text-left'>금액</th>
              <th className='p-2 text-left'></th>
            </tr>
          </thead>
          <tbody>
            {billingData.products.map((product, idx) => (
              <tr key={product.productId} className='border-b'>
                <td className='p-2'>{product.name}</td>
                <td className='p-2' onClick={() => handleEditClick(idx, 'price')}>
                  {renderEditableField(product, idx, 'price')}
                </td>
                <td className='p-2' onClick={() => handleEditClick(idx, 'quantity')}>
                  {renderEditableField(product, idx, 'quantity')}
                </td>
                <td className='p-2'>{(product.price * product.quantity).toLocaleString()}원</td>
                <td className='p-2'>
                  <button
                    type='button'
                    onClick={() => handleProductRemove(product.productId)}
                    className='text-red-500 hover:text-red-700'>
                    <FontAwesomeIcon icon={faTrash} />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </form>
  );
};

export default BillingForm;
