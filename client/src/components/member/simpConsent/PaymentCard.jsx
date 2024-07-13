import React, { useState, useEffect } from 'react';
import Input from '@/components/common/inputs/Input';
import { useUserDataStore } from '@/stores/useUserDataStore';

const PaymentCard = () => {
  const { userData, setUserData } = useUserDataStore();
  const [localData, setLocalData] = useState({
    cardNumber: userData.cardNumber,
    expiryDate: userData.expiryDate,
    cardHolder: userData.cardHolder,
    cardBirthDate: userData.cardBirthDate,
  });

  useEffect(() => {
    setLocalData({
      cardNumber: userData.cardNumber,
      expiryDate: userData.expiryDate,
      cardHolder: userData.cardHolder,
      cardBirthDate: userData.cardBirthDate,
    });
  }, [userData]);

  const handleInputChange = e => {
    const { name, value } = e.target;
    setLocalData(prev => ({ ...prev, [name]: value }));
  };

  const handleBlur = e => {
    const { name, value } = e.target;
    setUserData({ [name]: value });
  };

  return (
    <div className='flex flex-col bg-white p-1'>
      <form className='space-y-4'>
        <Input
          label='카드번호'
          name='cardNumber'
          type='text'
          required
          placeholder="'-' 없이 숫자만 입력해주세요"
          value={localData.cardNumber}
          onChange={handleInputChange}
          onBlur={handleBlur}
        />
        <Input
          label='유효기간'
          name='expiryDate'
          type='text'
          required
          placeholder='MM/YY'
          value={localData.expiryDate}
          onChange={handleInputChange}
          onBlur={handleBlur}
        />
        <Input
          label='명의자'
          name='cardHolder'
          type='text'
          required
          placeholder='최대 15자리'
          value={localData.cardHolder}
          onChange={handleInputChange}
          onBlur={handleBlur}
        />
        <Input
          label='생년월일'
          name='cardBirthDate'
          type='text'
          required
          placeholder='생년월일 6자리'
          value={localData.cardBirthDate}
          onChange={handleInputChange}
          onBlur={handleBlur}
        />
      </form>
    </div>
  );
};

export default PaymentCard;
