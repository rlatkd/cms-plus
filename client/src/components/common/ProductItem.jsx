import React from 'react';

const ProductItem = ({ item, onUpdateQuantity, onRemove }) => (
  <div className='flex items-center justify-between mb-2 text-sm border-b pb-2'>
    <span>{item.name}</span>
    <div className='flex items-center'>
      <div className='flex items-center border rounded-md mr-2'>
        <button onClick={() => onUpdateQuantity(-1)} className='px-2 py-1 bg-gray-100'>
          -
        </button>
        <span className='px-4 py-1'>{item.quantity}</span>
        <button onClick={() => onUpdateQuantity(1)} className='px-2 py-1 bg-gray-100'>
          +
        </button>
      </div>
      <span className='mr-2'>{item.quantity * item.price}원</span>
      <button onClick={onRemove} className='text-gray-400'>
        ×
      </button>
    </div>
  </div>
);

export default ProductItem;
