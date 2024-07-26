import { useState } from 'react';
import Input from '@/components/common/inputs/Input';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { verifyCard } from '@/apis/validation';
import { formatBirthDate } from '@/utils/format/formatBirth';

const CardInfo = ({ cardInfo, setCardInfo }) => {
  const selectedCard = useInvoiceStore(state => state.selectedCard);

  const [isVerifying, setIsVerifying] = useState(false);
  const [isVerified, setIsVerified] = useState(false);
  const [verificationResult, setVerificationResult] = useState(null);

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

    console.log(chunks.join('-').slice(0, 19));
    return chunks.join('-').slice(0, 19);
  };

  const handleInputChange = e => {
    const { name, value } = e.target;
    let formattedValue = value;

    switch (name) {
      case 'cardNumber':
        formattedValue = formatCardNumber(value);
        break;
      case 'expiryDate':
        formattedValue = formatExpiryDate(value);
        break;
      case 'cardOwnerBirth':
        formattedValue = formatBirthDate(value);
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
        cardNumber: cardInfo.cardNumber.replace(/-/g, ''),
        cardOwner: cardInfo.cardOwner,
        cardOwnerBirth: cardInfo.cardOwnerBirth,
      };

      const result = await verifyCard(cardData);
      setVerificationResult(result);
      setIsVerified(true);
      console.log('Card verification result:', result);
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
            placeholder={selectedCard}
            disabled
            className='disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none'
          />
          <Input
            label='카드번호'
            name='cardNumber'
            type='text'
            required
            placeholder="'-' 없이 숫자만 입력해주세요"
            value={cardInfo.cardNumber}
            onChange={handleInputChange}
            maxLength={19}
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
          />
          <Input
            label='명의자'
            name='cardOwner'
            type='text'
            required
            placeholder='최대 15자리'
            value={cardInfo.cardOwner}
            onChange={handleInputChange}
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
