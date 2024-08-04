import { useState } from 'react';
import Input from '@/components/common/inputs/Input';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { verifyCMS } from '@/apis/validation';
import { formatBirthDate } from '@/utils/format/formatBirth';
import { validateField } from '@/utils/validators';

const AccountInfo = ({ accountInfo, setAccountInfo, isVerified, setIsVerified  }) => {
  const selectedBank = useInvoiceStore(state => state.selectedBank);

  const [isVerifying, setIsVerifying] = useState(false);
  const [verificationResult, setVerificationResult] = useState(null);

  const handleInputChange = e => {
    const { name, value } = e.target;
    let formattedValue = value;

    if (name == 'accountOwnerBirth') {
      formattedValue = formatBirthDate(value);
    }

    setAccountInfo(prevState => ({
      ...prevState,
      [name]: formattedValue,
    }));
  };

  const handleAccountVerification = async e => {
    e.preventDefault();
    if (isVerified) return;

    setIsVerifying(true);
    try {
      const accountData = {
        paymentMethod: 'CMS',
        accountNumber: accountInfo.accountNumber,
        accountOwner: accountInfo.accountOwner,
        accountOwnerBirth: accountInfo.accountOwnerBirth,
      };

      const result = await verifyCMS(accountData);
      setVerificationResult(result);

      if(result){
        setIsVerified(result);
      }else{
        setIsVerified(result);
        alert('계좌 인증에 실패했습니다.' )
      }

      console.log('Account verification result:', result);
    } catch (error) {
      console.error('Account verification failed:', error);
      setVerificationResult({ error: '계좌 인증에 실패했습니다.' });
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
        결제계좌 정보를 입력해주세요.
      </h3>

      <div className='flex flex-col bg-white p-1'>
        <form className='space-y-4' onSubmit={handleAccountVerification}>
          <Input
            label='은행'
            name='bankNumber'
            type='text'
            required
            value={selectedBank}
            disabled
            className='text-slate-500 disabled:border-slate-200 disabled:shadow-none'
          />
          <Input
            label='예금주명'
            name='accountOwner'
            type='text'
            required
            placeholder='최대 20자리'
            value={accountInfo.accountOwner}
            onChange={handleInputChange}
            maxLength={20}
            isValid={accountInfo.accountOwner === '' || validateField('name', accountInfo.accountOwner)}
            errorMsg='올바른 예금주명을 입력해주세요.'
          />
          <Input
            label='계좌번호'
            name='accountNumber'
            type='text'
            required
            placeholder='최대 14자리'
            value={accountInfo.accountNumber}
            onChange={handleInputChange}
            maxLength={14}
            isValid={accountInfo.accountNumber === '' || validateField('accountNumber', accountInfo.accountNumber)}
            errorMsg='올바른 계좌번호를 입력해주세요.'
          />
          <Input
            label='생년월일'
            name='accountOwnerBirth'
            type='text'
            required
            placeholder='YYYY-MM-DD'
            value={accountInfo.accountOwnerBirth}
            onChange={handleInputChange}
            maxLength={10}
            isValid={accountInfo.accountOwnerBirth === '' || validateField('birth', accountInfo.accountOwnerBirth)}
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
            {isVerified ? '인증 완료' : isVerifying ? '인증 중...' : '계좌 인증하기'}
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

export default AccountInfo;
