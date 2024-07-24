import React, { useCallback } from 'react';
import Input from '@/components/common/inputs/Input';
import SelectField from '@/components/common/selects/SelectField';
import { verifyCMS } from '@/apis/validation';

const bankOptions = [
  { value: '', label: '은행을 선택해주세요' },
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

const PaymentCMS = ({ localData, onInputChange, onVerificationComplete, isVerified }) => {
  const handleCMSVerification = useCallback(async () => {
    try {
      const cmsData = {
        paymentMethod: 'CMS',
        accountNumber: localData.accountNumber,
        accountOwner: localData.accountHolder,
        accountOwnerBirth: localData.accountOwnerBirth,
      };

      const result = await verifyCMS(cmsData);

      if (result === true) {
        onVerificationComplete(true);
        alert('계좌 인증이 성공적으로 완료되었습니다.');
      } else {
        onVerificationComplete(false);
        alert('계좌 인증에 실패했습니다. 다시 시도해주세요.');
      }
    } catch (error) {
      console.error('CMS verification error:', error);
      onVerificationComplete(false);
      alert('계좌 인증에 실패했습니다. 다시 시도해주세요.');
    }
  }, [localData, onVerificationComplete]);

  const handleInputChange = useCallback(
    e => {
      const { name, value } = e.target;
      let formattedValue = value;

      if (name === 'accountOwnerBirth') {
        formattedValue = formatBirthDate(value);
      }

      onInputChange(name, formattedValue);
    },
    [onInputChange]
  );

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
        />
        <Input
          label='예금주명'
          name='accountHolder'
          type='text'
          required
          placeholder='최대 20자리'
          value={localData.accountHolder}
          onChange={handleInputChange}
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
          maxLength={20}
        />
      </form>
      <button
        className={`mt-4 w-full rounded-lg border py-2 text-sm font-normal transition-colors ${
          isVerified
            ? 'border-green-400 bg-green-50 text-green-400'
            : 'border-teal-400 bg-white text-teal-400 hover:bg-teal-50'
        }`}
        onClick={handleCMSVerification}
        disabled={isVerified}>
        {isVerified ? '인증 완료' : '계좌 인증하기'}
      </button>
    </div>
  );
};

export default React.memo(PaymentCMS);
