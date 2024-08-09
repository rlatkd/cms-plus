import React, { useCallback, useState } from 'react';
import Input from '@/components/common/inputs/Input';
import { verifyCard } from '@/apis/validation';
import { validateField } from '@/utils/validators';
import { formatCardNumber, unformatCardNumber } from '@/utils/format/formatCard';
import { formatBirthDate } from '@/utils/format/formatBirth';
import { formatExpiryDate } from '@/utils/format/formatExpiryDate';

const PaymentCard = ({ paymentData, onInputChange, isVerified, setIsVerified, contract }) => {
  // const [formattedCardNumber, setFormattedCardNumber] = useState('');

  const handleCardVerification = async () => {
    try {
      const cardData = {
        paymentMethod: 'CARD',
        cardNumber: unformatCardNumber(paymentData.cardNumber),
        cardOwner: paymentData.cardHolder,
        cardOwnerBirth: paymentData.cardOwnerBirth,
      };

      const result = await verifyCard(cardData);

      if (result === true) {
        setIsVerified(true);
        alert('카드 인증이 성공적으로 완료되었습니다.');
      } else {
        setIsVerified(false);
        alert('카드 인증에 실패했습니다. 다시 시도해주세요.');
      }
    } catch (error) {
      console.error('Card verification error:', error);
      setIsVerified(false);
      alert('카드 인증에 실패했습니다. 다시 시도해주세요.');
    }
  };

  const handleInputChange = e => {
    const { name, value } = e.target;
    let formattedValue = value;
    let storedValue = value;
    setIsVerified(false);

    if (name === 'cardOwnerBirth') {
      formattedValue = formatBirthDate(value);
    } else if (name === 'expiryDate') {
      // 이전 값과 새로운 값의 길이를 비교
      const prevLength = paymentData.expiryDate.length;
      formattedValue = formatExpiryDate(value);
      // 만약 길이가 줄어들었고, 마지막 문자가 '/'이면 '/'도 제거
      if (formattedValue.length < prevLength && formattedValue.endsWith('/')) {
        formattedValue = formattedValue.slice(0, -1);
      }
    } else if (name === 'cardNumber') {
      storedValue = unformatCardNumber(value);
      formattedValue = unformatCardNumber(value);
      // setFormattedCardNumber(formatCardNumber(value));
    }

    onInputChange(name, storedValue);
    onInputChange(name, formattedValue);
  };

  const isDisabled =
    !paymentData.cardNumber ||
    !paymentData.expiryDate ||
    !paymentData.cardHolder ||
    !paymentData.cardOwnerBirth;

  return (
    <div className='flex flex-col bg-white p-1'>
      <form className='space-y-4'>
        <Input
          label='카드번호'
          name='cardNumber'
          type='text'
          required
          placeholder='카드번호 16자리'
          value={formatCardNumber(paymentData.cardNumber)}
          onChange={handleInputChange}
          maxLength={19}
          isValid={
            !!contract ||
            paymentData.cardNumber === '' ||
            validateField('cardNumber', unformatCardNumber(paymentData.cardNumber))
          }
          errorMsg='올바른 카드번호를 입력해주세요.'
          disabled={!!contract.paymentMethodInfo}
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
          isValid={
            paymentData.expiryDate === '' || validateField('expiryDate', paymentData.expiryDate)
          }
          errorMsg='올바른 유효기간을 입력해주세요.'
          disabled={!!contract.paymentMethodInfo}
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
          isValid={paymentData.cardHolder === '' || validateField('name', paymentData.cardHolder)}
          errorMsg='올바른 명의자를 입력해주세요.'
          disabled={!!contract.paymentMethodInfo}
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
          isValid={
            paymentData.cardOwnerBirth === '' || validateField('birth', paymentData.cardOwnerBirth)
          }
          errorMsg='올바른 생년월일을 입력해주세요.'
          disabled={!!contract.paymentMethodInfo}
        />
      </form>
      <button
        className={`mt-4 w-full rounded-lg border py-2 text-sm font-normal transition-colors ${
          isDisabled
            ? 'bg-ipt_disa'
            : isVerified
              ? 'border-green-400 bg-green-50 text-green-400'
              : 'border-teal-400 bg-white text-teal-400 hover:bg-teal-50'
        }`}
        onClick={handleCardVerification}
        disabled={isVerified || isDisabled}>
        {isVerified ? '인증 완료' : '카드 인증하기'}
      </button>
    </div>
  );
};

export default React.memo(PaymentCard);
