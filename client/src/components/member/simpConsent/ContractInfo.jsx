import React, {
  useState,
  useEffect,
  forwardRef,
  useImperativeHandle,
  useCallback,
  useMemo,
} from 'react';
import Input from '@/components/common/inputs/Input';
import SelectField from '@/components/common/selects/SelectField';
import ProductItem from '@/components/common/ProductItem';
import { useUserDataStore } from '@/stores/useUserDataStore';
import { getAvailableOptions } from '@/apis/simpleConsent';
import InputCalendar from '@/components/common/inputs/InputCalendar';

const ContractInfo = forwardRef((props, ref) => {
  const { userData, setUserData } = useUserDataStore();
  const [availableProducts, setAvailableProducts] = useState([]);
  const [localData, setLocalData] = useState({
    contractName: '',
    selectedProduct: '',
    items: [],
    startDate: '',
    endDate: '',
    contractDay: 1,
  });

  // 초기 userData 로드
  useEffect(() => {
    setLocalData({
      contractName: userData.contractDTO.contractName || '',
      selectedProduct: userData.contractDTO.selectedProduct || '',
      items: userData.contractDTO.items || [],
      startDate: userData.contractDTO.startDate || '',
      endDate: userData.contractDTO.endDate || '',
      contractDay: userData.contractDTO.contractDay || 1,
    });
  }, [userData]);

  useEffect(() => {
    const fetchAvailableOptions = async () => {
      try {
        const options = await getAvailableOptions();
        setAvailableProducts(options.availableProducts);
      } catch (error) {
        console.error('상품 리스트 업데이트 실패:', error);
      }
    };

    fetchAvailableOptions();
  }, []);

  const updateUserData = useCallback(() => {
    setUserData({
      contractDTO: {
        ...localData,
      },
    });
  }, [localData, setUserData]);

  useImperativeHandle(ref, () => ({
    validateContractInfo: () => {
      const missingFields = [];

      if (!localData.contractName) missingFields.push('계약명');
      if (localData.items.length === 0) missingFields.push('선택된 상품');
      if (!localData.startDate) missingFields.push('시작 날짜');
      if (!localData.endDate) missingFields.push('종료 날짜');
      if (!localData.contractDay) missingFields.push('약정일');

      // 유효성 검사 후 userData 업데이트
      updateUserData();

      return missingFields;
    },
  }));

  const handleInputChange = useCallback(e => {
    const { name, value } = e.target;
    setLocalData(prev => ({ ...prev, [name]: value }));
  }, []);

  const handleProductChange = useCallback(
    e => {
      const productId = e.target.value;
      if (productId) {
        const selectedProduct = availableProducts.find(p => p.productId.toString() === productId);
        const newProduct = {
          productId: selectedProduct.productId,
          productName: selectedProduct.productName,
          price: selectedProduct.productPrice,
          quantity: 1,
        };

        if (!localData.items.some(item => item.productId === newProduct.productId)) {
          setLocalData(prev => ({
            ...prev,
            selectedProduct: productId,
            items: [...prev.items, newProduct],
          }));
        }
      } else {
        setLocalData(prev => ({ ...prev, selectedProduct: '' }));
      }
    },
    [availableProducts, localData.items]
  );

  const updateQuantity = useCallback((index, change) => {
    setLocalData(prev => {
      const newItems = [...prev.items];
      newItems[index].quantity = Math.max(1, newItems[index].quantity + change);
      return { ...prev, items: newItems };
    });
  }, []);

  const removeItem = useCallback(index => {
    setLocalData(prev => ({
      ...prev,
      items: prev.items.filter((_, i) => i !== index),
    }));
  }, []);

  const handleDateChange = useCallback(
    (e, field) => {
      const newDate = e.target.value;
      setLocalData(prev => ({ ...prev, [field]: newDate }));

      if (field === 'startDate' && new Date(newDate) > new Date(localData.endDate)) {
        setLocalData(prev => ({ ...prev, endDate: newDate }));
      } else if (field === 'endDate' && new Date(newDate) < new Date(localData.startDate)) {
        setLocalData(prev => ({ ...prev, startDate: newDate }));
      }
    },
    [localData.endDate, localData.startDate]
  );

  const productOptions = useMemo(
    () => [
      { value: '', label: '상품을 선택해주세요' },
      ...availableProducts.map(product => ({
        value: product.productId.toString(),
        label: `${product.productName}(${product.productPrice.toLocaleString()}원)`,
      })),
    ],
    [availableProducts]
  );

  const totalPrice = useMemo(
    () => localData.items.reduce((sum, item) => sum + item.quantity * item.price, 0),
    [localData.items]
  );

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
        합계: {totalPrice.toLocaleString()}원
      </div>

      <div className='mb-4'>
        <label className='mb-1 block text-sm font-medium text-gray-700'>
          <span
            className={`block text-sm font-medium text-slate-700 after:ml-0.5 after:text-red-500 after:content-['*']`}>
            기간
          </span>
        </label>
        <div className='flex w-full items-center'>
          <InputCalendar
            id='startDate'
            value={localData.startDate}
            handleChangeValue={e => handleDateChange(e, 'startDate')}
            placeholder='시작 날짜'
            width='100%'
          />
          <span className='mx-2 flex-shrink-0 text-gray-500'>~</span>
          <InputCalendar
            id='endDate'
            value={localData.endDate}
            handleChangeValue={e => handleDateChange(e, 'endDate')}
            placeholder='종료 날짜'
            width='100%'
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
        }}
      />
    </div>
  );
});

//forwardRef를 사용하여 정의된 컴포넌트에 displayName을 명시적으로 설정
ContractInfo.displayName = 'ContractInfo';

export default ContractInfo;
