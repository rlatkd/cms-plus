import React, { useCallback } from 'react';
import Input from '@/components/common/inputs/Input';
import { verifyCard } from '@/apis/validation';

const PaymentCard = ({ paymentData, onInputChange, onVerificationComplete, isVerified }) => {
  const handleCardVerification = useCallback(async () => {
    try {
      const cardData = {
        paymentMethod: 'CARD',
        cardNumber: paymentData.cardNumber,
        cardOwner: paymentData.cardHolder,
        cardOwnerBirth: paymentData.cardOwnerBirth,
      };

      const result = await verifyCard(cardData);

      if (result === true) {
        onVerificationComplete(true);
        // 부모 컴포넌트의 상태 업데이트
        onInputChange('isVerified', true);
        alert('카드 인증이 성공적으로 완료되었습니다.');
      } else {
        onVerificationComplete(false);
        // 부모 컴포넌트의 상태 업데이트
        onInputChange('isVerified', false);
        alert('카드 인증에 실패했습니다. 다시 시도해주세요.');
      }
    } catch (error) {
      console.error('Card verification error:', error);
      onVerificationComplete(false);
      // 부모 컴포넌트의 상태 업데이트
      onInputChange('isVerified', false);
      alert('카드 인증에 실패했습니다. 다시 시도해주세요.');
    }
  }, [paymentData, onVerificationComplete]);

  const handleInputChange = useCallback(
    e => {
      const { name, value } = e.target;
      let formattedValue = value;

      if (name === 'cardOwnerBirth') {
        formattedValue = formatBirthDate(value);
      } else if (name === 'expiryDate') {
        formattedValue = formatExpiryDate(value);
      } else if (name === 'cardNumber') {
        formattedValue = formatCardNumber(value);
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
          value={paymentData.cardNumber}
          onChange={handleInputChange}
          maxLength={19}
        />
        <Input
          label='유효기간'
          name='expiryDate'
          type='text'
          required
          placeholder='MM/YY'
          value={paymentData.expiryDate}
          onChange={handleInputChange}
          maxLength={5}
        />
        <Input
          label='명의자'
          name='cardHolder'
          type='text'
          required
          placeholder='최대 15자리'
          value={paymentData.cardHolder}
          onChange={handleInputChange}
          maxLength={15}
        />
        <Input
          label='생년월일'
          name='cardOwnerBirth'
          type='text'
          required
          placeholder='YYYY-MM-DD (예: 1990-01-01)'
          value={paymentData.cardOwnerBirth}
          onChange={handleInputChange}
          maxLength={10}
        />
      </form>
      <button
        className={`mt-4 w-full rounded-lg border py-2 text-sm font-normal transition-colors ${
          isVerified
            ? 'border-green-400 bg-green-50 text-green-400'
            : 'border-teal-400 bg-white text-teal-400 hover:bg-teal-50'
        }`}
        onClick={handleCardVerification}
        disabled={isVerified}>
        {isVerified ? '인증 완료' : '카드 인증하기'}
      </button>
    </div>
  );
};

export default React.memo(PaymentCard);
