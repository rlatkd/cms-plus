import React, { useState } from 'react';
import Input from '@/components/common/Input';
import SelectField from '@/components/common/SelectField';
import ProductItem from '@/components/common/ProductItem';

const ContractInfo = () => {
  const [selectedProduct, setSelectedProduct] = useState('상품명1(3,000원)');
  const [items, setItems] = useState([{ name: '상품명1(3,000원)', quantity: 1, price: 3000 }]);
  const [startDate, setStartDate] = useState('2024-06-31');
  const [endDate, setEndDate] = useState('2024-07-01');

  const handleProductChange = e => {
    const newProduct = e.target.value;
    setSelectedProduct(newProduct);
    if (!items.some(item => item.name === newProduct)) {
      setItems([
        ...items,
        { name: newProduct, quantity: 1, price: newProduct.includes('3,000') ? 3000 : 4000 },
      ]);
    }
  };

  const updateQuantity = (index, change) => {
    const newItems = [...items];
    newItems[index].quantity = Math.max(1, newItems[index].quantity + change);
    setItems(newItems);
  };

  const removeItem = index => {
    setItems(items.filter((_, i) => i !== index));
  };

  const totalPrice = items.reduce((sum, item) => sum + item.quantity * item.price, 0);

  const handleStartDateChange = e => {
    const newStartDate = e.target.value;
    setStartDate(newStartDate);
    if (new Date(newStartDate) > new Date(endDate)) {
      setEndDate(newStartDate);
    }
  };

  const handleEndDateChange = e => {
    const newEndDate = e.target.value;
    setEndDate(newEndDate);
    if (new Date(newEndDate) < new Date(startDate)) {
      setStartDate(newEndDate);
    }
  };

  return (
    <div className='flex flex-col bg-white p-1'>
      <div className='w-full text-left'>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          회원님의
          <br />
          계약정보를 확인해주세요.
        </h3>
      </div>

      <SelectField
        label='상품'
        required
        options={[
          { value: '상품명1(3,000원)', label: '상품명1(3,000원)' },
          { value: '상품명2(4,000원)', label: '상품명2(4,000원)' },
        ]}
        value={selectedProduct}
        onChange={handleProductChange}
      />

      {items.map((item, index) => (
        <ProductItem
          key={index}
          item={item}
          onUpdateQuantity={change => updateQuantity(index, change)}
          onRemove={() => removeItem(index)}
        />
      ))}

      <div className='mb-4 text-right text-sm font-semibold'>합계: {totalPrice}원</div>

      <div className='mb-4'>
        <label className='mb-1 block text-sm font-medium text-gray-700'>
          <span
            className={`block text-sm font-medium text-slate-700 after:ml-0.5 after:text-red-500 after:content-['*']`}>
            기간
          </span>
        </label>
        <div className='flex w-full items-center'>
          <Input
            type='date'
            value={startDate}
            onChange={handleStartDateChange}
            min='2024-01-01'
            max='2024-12-31'
            className='flex-1'
          />
          <span className='mx-2 flex-shrink-0 text-gray-500'>~</span>
          <Input
            type='date'
            value={endDate}
            onChange={handleEndDateChange}
            min={startDate}
            max='2024-12-31'
            className='flex-1'
          />
        </div>
      </div>

      <SelectField
        label='약정일'
        required
        options={[...Array(31)].map((_, i) => ({ value: i + 1, label: `${i + 1}일` }))}
      />
    </div>
  );
};

export default ContractInfo;
