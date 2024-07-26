import React from 'react';

const ProductItem = ({ item, onUpdateQuantity, onRemove }) => {
  return (
    <div className='flex flex-col'>
      <div className='flex items-center justify-between mb-2'>
        <div className='flex-1'>
          <span className='text-sm font-medium'>{item.productName}</span>
        </div>
        <div className='flex text-sm items-center'>
          <button onClick={() => onUpdateQuantity(-1)} className='px-2 py-1  bg-gray-200 rounded-l'>
            -
          </button>
          <span className='px-4 py-1 bg-gray-100'>{item.quantity}</span>
          <button onClick={() => onUpdateQuantity(1)} className='px-2 py-1 bg-gray-200 rounded-r'>
            +
          </button>
        </div>
        <span className='text-xs ml-4 mr-2'>{(item.quantity * item.price).toLocaleString()}원</span>
        <button onClick={onRemove} className='text-gray-400'>
          ×
        </button>
      </div>
      <div className='border-b border-gray-200 w-full mb-2' />
    </div>
  );
};

export default ProductItem;
