import { useState } from 'react';
import Input from '@/components/common/inputs/Input';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { verifyCard } from '@/apis/validation';
import { validateField } from '@/utils/validators';
import { formatCardNumber, unformatCardNumber } from '@/utils/format/formatCard';
import { formatBirthDate } from '@/utils/format/formatBirth';
import { formatExpiryDate } from '@/utils/format/formatExpiryDate';

const CardInfo = ({ cardInfo, setCardInfo, isVerified, setIsVerified }) => {
  const selectedCard = useInvoiceStore(state => state.selectedCard);

  const [isVerifying, setIsVerifying] = useState(false);
  const [verificationResult, setVerificationResult] = useState(null);

  const handleInputChange = e => {
    const { name, value } = e.target;
    let formattedValue = value;
    let prevLength;

    switch (name) {
      case 'cardNumber':
        formattedValue = unformatCardNumber(value);
        break;
      case 'expiryDate':
        prevLength = cardInfo.expiryDate.length;
        formattedValue = formatExpiryDate(value);
        if (formattedValue.length < prevLength && formattedValue.endsWith('/')) {
          formattedValue = formattedValue.slice(0, -1);
        }
        break;
      case 'cardOwnerBirth':
        formattedValue = formatBirthDate(value);
        break;
      case 'cardOwner':
        formattedValue = value;
        break;
      default:
        break;
    }

    setCardInfo(prevState => ({
      ...prevState,
      [name]: formattedValue,
    }));
  };

  const handleCardVerification = async e => {
    e.preventDefault();
    if (isVerified) return;

    setIsVerifying(true);
    try {
      const cardData = {
        paymentMethod: 'CARD',
        cardNumber: unformatCardNumber(cardInfo.cardNumber),
        cardOwner: cardInfo.cardOwner,
        cardOwnerBirth: cardInfo.cardOwnerBirth,
      };

      const result = await verifyCard(cardData);
      setVerificationResult(result);
      if (result) {
        setIsVerified(result);
      } else {
        setIsVerified(result);
        alert('카드 인증에 실패했습니다.');
      }
    } catch (error) {
      console.error('Card verification failed:', error);
      setVerificationResult({ error: '카드 인증에 실패했습니다.' });
      setIsVerified(false);
    } finally {
      setIsVerifying(false);
    }
  };

  return (
    <div>
      <h3 className='mb-8 text-base font-semibold text-gray-700'>
        회원님의
        <br />
        결제카드 정보를 입력해주세요.
      </h3>

      <div className='flex flex-col bg-white p-1'>
        <form className='space-y-4' onSubmit={handleCardVerification}>
          <Input
            label='카드사'
            name='cardCompany'
            type='text'
            required
            value={selectedCard}
            disabled
            className='text-slate-500 disabled:border-slate-200 disabled:shadow-none'
          />
          <Input
            label='카드번호'
            name='cardNumber'
            type='text'
            required
            placeholder='숫자만 입력해주세요'
            value={formatCardNumber(cardInfo.cardNumber)}
            onChange={handleInputChange}
            maxLength={19}
            isValid={
              cardInfo.cardNumber === '' ||
              validateField('cardNumber', unformatCardNumber(cardInfo.cardNumber))
            }
            errorMsg='올바른 카드번호를 입력해주세요.'
          />
          <Input
            label='유효기간'
            name='expiryDate'
            type='text'
            required
            placeholder='MM/YY'
            value={cardInfo.expiryDate}
            onChange={handleInputChange}
            maxLength={5}
            isValid={cardInfo.expiryDate === '' || validateField('expiryDate', cardInfo.expiryDate)}
            errorMsg='올바른 유효기간을 입력해주세요.'
          />
          <Input
            label='명의자'
            name='cardOwner'
            type='text'
            required
            placeholder='최대 15자리'
            value={cardInfo.cardOwner}
            onChange={handleInputChange}
            isValid={cardInfo.cardOwner === '' || validateField('name', cardInfo.cardOwner)}
            errorMsg='올바른 명의자를 입력해주세요.'
          />
          <Input
            label='생년월일'
            name='cardOwnerBirth'
            type='text'
            required
            placeholder='YYYY-MM-DD'
            value={cardInfo.cardOwnerBirth}
            onChange={handleInputChange}
            maxLength={10}
            isValid={
              cardInfo.cardOwnerBirth === '' || validateField('birth', cardInfo.cardOwnerBirth)
            }
            errorMsg='올바른 생년월일을 입력해주세요.'
          />
          <button
            type='submit'
            className={`mt-4 w-full rounded-lg border py-2 text-sm font-normal transition-colors ${
              isVerified
                ? 'border-green-400 bg-green-50 text-green-400'
                : 'border-teal-400 bg-white text-teal-400 hover:bg-teal-50'
            }`}
            disabled={isVerifying || isVerified}>
            {isVerified ? '인증 완료' : isVerifying ? '인증 중...' : '카드 인증하기'}
          </button>
        </form>
        {verificationResult && !isVerified && (
          <div className='mt-4 p-2 text-sm rounded bg-red-100 text-red-700'>
            {verificationResult.error}
          </div>
        )}
      </div>
    </div>
  );
};

export default CardInfo;
