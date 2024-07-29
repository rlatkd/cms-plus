import { useState, useEffect } from 'react';
import { ProductSelectField2 } from '@/components/common/selects/ProductSelectField';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import InputWeb from '@/components/common/inputs/InputWeb';

const BillingDetailProduct = ({ billingProducts, products, editable, onChange, billingId }) => {
  const [localBillingProducts, setLocalBillingProducts] = useState(billingProducts);

  useEffect(() => {
    setLocalBillingProducts(billingProducts);
  }, [billingProducts]);

  const calcBillingPrice = mBillingProducts => {
    return mBillingProducts.reduce((sum, bp) => sum + bp.price * bp.quantity, 0);
  };

  const handleSelectedProductListChange = newOptions => {
    const newBillingProducts = newOptions.map(option => ({
      billingId: billingId,
      productId: option.value.productId,
      name: option.value.name,
      price: option.value.price,
      quantity: option.value.quantity || 1,
    }));
    if (newBillingProducts.length > 10) {
      alert('청구는 최대 10 개의 상품을 지녀야합니다.');
    }
    setLocalBillingProducts(newBillingProducts);
    onChange(newBillingProducts);
  };

  const handleProductChange = (idx, field, value) => {
    const newBillingProducts = [...localBillingProducts];
    newBillingProducts[idx][field] = value;
    setLocalBillingProducts(newBillingProducts);
    onChange(newBillingProducts);
  };

  const handleRemove = productId => {
    if (localBillingProducts.length === 1) {
      alert('최소 1개 이상의 상품이 필요합니다!');
      return;
    }
    const newBillingProducts = localBillingProducts.filter(
      product => product.productId !== productId
    );
    setLocalBillingProducts(newBillingProducts);
    onChange(newBillingProducts);
  };

  const handleInputChange = (idx, field, value) => {
    if (field === 'price' && value && value.length >= 8) {
      alert('상품 가격은 최대 100만원입니다.');
      return;
    } else if (field === 'quantity' && value && value.length >= 2) {
      alert('상품 수량은 최대 10개입니다.');
      return;
    }
    const numericValue = value.replace(/\D/g, '') || '';
    handleProductChange(idx, field, numericValue);
  };

  const renderEditableField = (item, idx, field) => {
    const value = item[field];

    return editable ? (
      <InputWeb
        id={field}
        type='text'
        placeholder={`${field === 'price' ? '가격' : '수량'}`}
        required
        classInput='text-center p-4 w-1/12 focus:border-mint focus:outline-none 
                    focus:ring-mint focus:ring-1 rounded-lg'
        value={value.toLocaleString()}
        onChange={e => handleInputChange(idx, field, e.target.value)}
        autoComplete='off'
        maxLength={field === 'price' ? 8 : 3}
      />
    ) : (
      <div
        className={`${editable ? 'cursor-pointer border rounded-lg focus:border-mint focus:outline-none focus:ring-mint focus:ring-1' : ''} p-4 w-3/4 text-center`}>
        {`${value.toLocaleString()}${field === 'price' ? '원' : '개'}`}
      </div>
    );
  };

  return (
    <div className='flex flex-col h-full space-y-4'>
      <div className='flex space-x-4'>
        <div className='w-full'>
          <div className='flex justify-between'>
            <div className={`w-2/6 flex-row mb-3 ${editable ? 'visible' : 'invisible'}`}>
              <label className={`block text-text_black text-15 font-700 mb-2 ml-2`}>
                상품 추가
              </label>
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
                selectedOptions={localBillingProducts.map(p => ({
                  value: p,
                  label: `${p.name} (${p.price.toLocaleString()}원)`,
                }))}
                onChange={handleSelectedProductListChange}
                disabled={!editable}
              />
            </div>

            <div className='flex items-end'>
              <p className='font-bold text-lg mr-2'>합계:</p>
              <p className='text-right font-bold text-lg border-none'>{`${calcBillingPrice(localBillingProducts).toLocaleString()}원`}</p>
            </div>
          </div>
        </div>
      </div>

      <div className='flex-1 overflow-auto'>
        <table className='w-full'>
          <thead>
            <tr className='bg-gray-100'>
              <th className='p-2 text-left'>상품명</th>
              <th className='p-2 text-left'>단가</th>
              <th className='p-2 text-left'>수량</th>
              <th className='p-2 text-left'>금액</th>
              {editable && <th className='p-2 text-left' />}
            </tr>
          </thead>
          <tbody>
            {localBillingProducts.map((product, idx) => (
              <tr key={product.productId} className='border-b'>
                <td className='p-2'>{product.name}</td>
                <td className='p-2'>{renderEditableField(product, idx, 'price')}</td>
                <td className='p-2'>{renderEditableField(product, idx, 'quantity')}</td>
                <td className='p-2'>{(product.price * product.quantity).toLocaleString()}원</td>
                {editable && (
                  <td className='p-2'>
                    <button
                      type='button'
                      onClick={() => handleRemove(product.productId)}
                      className='text-red-500 hover:text-red-700'>
                      <FontAwesomeIcon icon={faTrash} />
                    </button>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default BillingDetailProduct;
