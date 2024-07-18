import React, { useState, useEffect } from 'react';
import Input from '@/components/common/inputs/Input';
import SelectField from '@/components/common/SelectField';
import { useUserDataStore } from '@/stores/useUserDataStore';

const bankOptions = [
  { value: 'SHINHAN', label: '신한은행' },
  { value: 'KB', label: '국민은행' },
  { value: 'WOORI', label: '우리은행' },
  { value: 'IBK', label: '기업은행' },
  { value: 'SUHYUP', label: '수협은행' },
  { value: 'NH', label: 'NH농협은행' },
  { value: 'BUSAN', label: '부산은행' },
  { value: 'HANA', label: '하나은행' },
  { value: 'GWANGJU', label: '광주은행' },
  { value: 'POST', label: '우체국' },
  { value: 'IM', label: 'iM뱅크' },
  { value: 'KNB', label: '경남은행' },
];

const PaymentCMS = () => {
  const { userData, setUserData } = useUserDataStore();
  const [localData, setLocalData] = useState({
    bank: userData.paymentDTO.bank || '',
    accountHolder: userData.paymentDTO.accountHolder || '',
    accountOwnerBirth: userData.paymentDTO.accountOwnerBirth || '',
    accountNumber: userData.paymentDTO.accountNumber || '',
  });

  useEffect(() => {
    setLocalData({
      bank: userData.paymentDTO.bank || '',
      accountHolder: userData.paymentDTO.accountHolder || '',
      accountOwnerBirth: userData.paymentDTO.accountOwnerBirth || '',
      accountNumber: userData.paymentDTO.accountNumber || '',
    });
  }, [userData.paymentDTO]);

  const handleInputChange = e => {
    const { name, value } = e.target;
    if (name === 'accountOwnerBirth') {
      const formattedValue = formatBirthDate(value);
      setLocalData(prev => ({ ...prev, [name]: formattedValue }));
    } else {
      setLocalData(prev => ({ ...prev, [name]: value }));
    }
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

  return (
    <div className='flex flex-col bg-white p-1'>
      <form className='space-y-4'>
        <SelectField
          label='은행'
          name='bank'
          required
          options={bankOptions}
          value={localData.bank}
          onChange={handleInputChange}
          onBlur={handleBlur}
        />
        <Input
          label='예금주명'
          name='accountHolder'
          type='text'
          required
          placeholder='최대 20자리'
          value={localData.accountHolder}
          onChange={handleInputChange}
          onBlur={handleBlur}
          maxLength={20}
        />
        <Input
          label='생년월일'
          name='accountOwnerBirth'
          type='text'
          required
          placeholder='YYYY-MM-DD (예: 1990-01-01)'
          value={localData.accountOwnerBirth}
          onChange={handleInputChange}
          onBlur={handleBlur}
          maxLength={10}
        />
        <Input
          label='계좌번호'
          name='accountNumber'
          type='text'
          required
          placeholder='최대 20자리'
          value={localData.accountNumber}
          onChange={handleInputChange}
          onBlur={handleBlur}
          maxLength={20}
        />
      </form>

      <button className='mt-4 w-full rounded-lg border border-teal-400 bg-white py-2 text-sm font-normal text-teal-400 transition-colors hover:bg-teal-50'>
        계좌 인증하기
      </button>
    </div>
  );
};

export default PaymentCMS;
