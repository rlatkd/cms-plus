import React, { useState, useEffect } from 'react';
import Input from '@/components/common/inputs/Input';
import SelectField from '@/components/common/SelectField';
import ProductItem from '@/components/common/ProductItem';
import { useUserDataStore } from '@/stores/useUserDataStore';

const ContractInfo = () => {
  const { userData, setUserData } = useUserDataStore();
  const [localData, setLocalData] = useState({
    contractName: userData.contractDTO.contractName || '',
    selectedProduct: userData.contractDTO.selectedProduct || '',
    items: userData.contractDTO.items || [],
    startDate: userData.contractDTO.startDate || '',
    endDate: userData.contractDTO.endDate || '',
    contractDay: userData.contractDTO.contractDay || 1,
  });

  useEffect(() => {
    setLocalData({
      contractName: userData.contractDTO.contractName || '',
      selectedProduct: userData.contractDTO.selectedProduct || '',
      items: userData.contractDTO.items || [],
      startDate: userData.contractDTO.startDate || '',
      endDate: userData.contractDTO.endDate || '',
      contractDay: userData.contractDTO.contractDay || 1,
    });
  }, [userData.contractDTO]);

  const productOptions = [
    { value: '', label: '상품을 선택해주세요' },
    { value: '10', label: '상품 10(3,000원)' },
    { value: '20', label: '상품 20(4,000원)' },
  ];

  const handleInputChange = e => {
    const { name, value } = e.target;
    setLocalData(prev => ({ ...prev, [name]: value }));
  };

  const handleBlur = e => {
    const { name, value } = e.target;
    setUserData({ contractDTO: { ...userData.contractDTO, [name]: value } });
  };

  const handleProductChange = e => {
    const productId = e.target.value;
    if (productId) {
      const selectedProduct = productOptions.find(option => option.value === productId);
      const newProduct = {
        productId: parseInt(productId),
        productName: selectedProduct.label.split('(')[0],
        price: selectedProduct.label.includes('3,000') ? 3000 : 4000,
        quantity: 1,
      };

      if (!localData.items.some(item => item.productId === newProduct.productId)) {
        const newItems = [...localData.items, newProduct];
        setLocalData(prev => ({
          ...prev,
          selectedProduct: productId,
          items: newItems,
        }));
        setUserData({
          contractDTO: {
            ...userData.contractDTO,
            selectedProduct: productId,
            items: newItems,
          },
        });
      }
    } else {
      setLocalData(prev => ({ ...prev, selectedProduct: '' }));
      setUserData({ contractDTO: { ...userData.contractDTO, selectedProduct: '' } });
    }
  };

  const updateQuantity = (index, change) => {
    const newItems = [...localData.items];
    newItems[index].quantity = Math.max(1, newItems[index].quantity + change);
    setLocalData(prev => ({ ...prev, items: newItems }));
    setUserData({ contractDTO: { ...userData.contractDTO, items: newItems } });
  };

  const removeItem = index => {
    const newItems = localData.items.filter((_, i) => i !== index);
    setLocalData(prev => ({ ...prev, items: newItems }));
    setUserData({ contractDTO: { ...userData.contractDTO, items: newItems } });
  };

  const handleDateChange = (e, field) => {
    const newDate = e.target.value;
    setLocalData(prev => ({ ...prev, [field]: newDate }));
    setUserData({ contractDTO: { ...userData.contractDTO, [field]: newDate } });

    if (field === 'startDate' && new Date(newDate) > new Date(localData.endDate)) {
      setLocalData(prev => ({ ...prev, endDate: newDate }));
      setUserData({ contractDTO: { ...userData.contractDTO, endDate: newDate } });
    } else if (field === 'endDate' && new Date(newDate) < new Date(localData.startDate)) {
      setLocalData(prev => ({ ...prev, startDate: newDate }));
      setUserData({ contractDTO: { ...userData.contractDTO, startDate: newDate } });
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

      <Input
        label='계약명'
        name='contractName'
        type='text'
        required
        placeholder='최대 20자리'
        className='pb-6'
        value={localData.contractName}
        onChange={handleInputChange}
        onBlur={handleBlur}
        maxLength={20}
      />

      <SelectField
        label='상품'
        required
        options={productOptions}
        value={localData.selectedProduct}
        onChange={handleProductChange}
      />

      {localData.items.map((item, index) => (
        <ProductItem
          key={index}
          item={item}
          onUpdateQuantity={change => updateQuantity(index, change)}
          onRemove={() => removeItem(index)}
        />
      ))}

      <div className='mb-4 text-right text-sm font-semibold'>
        합계: {localData.items.reduce((sum, item) => sum + item.quantity * item.price, 0)}원
      </div>

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
            value={localData.startDate}
            onChange={e => handleDateChange(e, 'startDate')}
            min='2024-01-01'
            max='2024-12-31'
            className='flex-1'
          />
          <span className='mx-2 flex-shrink-0 text-gray-500'>~</span>
          <Input
            type='date'
            value={localData.endDate}
            onChange={e => handleDateChange(e, 'endDate')}
            min={localData.startDate}
            max='2024-12-31'
            className='flex-1'
          />
        </div>
      </div>

      <SelectField
        label='약정일'
        required
        options={[...Array(31)].map((_, i) => ({ value: i + 1, label: `${i + 1}일` }))}
        value={localData.contractDay}
        onChange={e => {
          const value = parseInt(e.target.value);
          setLocalData(prev => ({ ...prev, contractDay: value }));
          setUserData({ contractDTO: { ...userData.contractDTO, contractDay: value } });
        }}
      />
    </div>
  );
};

export default ContractInfo;
