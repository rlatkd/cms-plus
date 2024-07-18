import React, { useState, useEffect } from 'react';
import Input from '@/components/common/inputs/Input';
import { useUserDataStore } from '@/stores/useUserDataStore';

const PaymentCard = () => {
  const { userData, setUserData } = useUserDataStore();
  const [localData, setLocalData] = useState({
    cardNumber: userData.paymentDTO.cardNumber || '',
    expiryDate: userData.paymentDTO.expiryDate || '',
    cardHolder: userData.paymentDTO.cardHolder || '',
    cardOwnerBirth: userData.paymentDTO.cardOwnerBirth || '',
  });

  useEffect(() => {
    setLocalData({
      cardNumber: userData.paymentDTO.cardNumber || '',
      expiryDate: userData.paymentDTO.expiryDate || '',
      cardHolder: userData.paymentDTO.cardHolder || '',
      cardOwnerBirth: userData.paymentDTO.cardOwnerBirth || '',
    });
  }, [userData.paymentDTO]);

  const handleInputChange = e => {
    const { name, value } = e.target;
    let formattedValue = value;

    if (name === 'cardOwnerBirth') {
      formattedValue = formatBirthDate(value);
    } else if (name === 'expiryDate') {
      formattedValue = formatExpiryDate(value);
    } else if (name === 'cardNumber') {
      formattedValue = formatCardNumber(value);
    }

    setLocalData(prev => ({ ...prev, [name]: formattedValue }));
  };

  const handleBlur = e => {
    const { name, value } = e.target;
    setUserData({ paymentDTO: { ...userData.paymentDTO, [name]: value } });
  };

  const formatBirthDate = value => {
    const cleaned = value.replace(/\D/g, '');
    let formatted = cleaned;

    if (cleaned.length > 4) {
      formatted = `${cleaned.slice(0, 4)}-${cleaned.slice(4)}`;
    }
    if (cleaned.length > 6) {
      formatted = `${formatted.slice(0, 7)}-${formatted.slice(7)}`;
    }

    return formatted.slice(0, 10);
  };

  const formatExpiryDate = value => {
    const cleaned = value.replace(/\D/g, '');
    let formatted = cleaned;

    if (cleaned.length >= 2) {
      formatted = `${cleaned.slice(0, 2)}/${cleaned.slice(2)}`;
    }

    return formatted.slice(0, 5);
  };

  const formatCardNumber = value => {
    const cleaned = value.replace(/\D/g, '');
    const chunks = [];

    for (let i = 0; i < cleaned.length; i += 4) {
      chunks.push(cleaned.slice(i, i + 4));
    }

    return chunks.join('-').slice(0, 19);
  };

  return (
    <div className='flex flex-col bg-white p-1'>
      <form className='space-y-4'>
        <Input
          label='카드번호'
          name='cardNumber'
          type='text'
          required
          placeholder='카드번호 16자리'
          value={localData.cardNumber}
          onChange={handleInputChange}
          onBlur={handleBlur}
          maxLength={19}
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
          maxLength={5}
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
          maxLength={15}
        />
        <Input
          label='생년월일'
          name='cardOwnerBirth'
          type='text'
          required
          placeholder='YYYY-MM-DD (예: 1990-01-01)'
          value={localData.cardOwnerBirth}
          onChange={handleInputChange}
          onBlur={handleBlur}
          maxLength={10}
        />
      </form>
      <button className='mt-4 w-full rounded-lg border border-teal-400 bg-white py-2 text-sm font-normal text-teal-400 transition-colors hover:bg-teal-50'>
        카드 인증하기
      </button>
    </div>
  );
};

export default PaymentCard;
