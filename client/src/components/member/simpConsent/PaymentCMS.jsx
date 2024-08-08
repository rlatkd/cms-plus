import React, { useCallback } from 'react';
import Input from '@/components/common/inputs/Input';
import SelectField from '@/components/common/selects/SelectField';
import { verifyCMS } from '@/apis/validation';
import { validateField } from '@/utils/validators';
import { formatBirthDate } from '@/utils/format/formatBirth';
import { bankOptions } from '@/utils/bank/bank';

const PaymentCMS = ({
  paymentData,
  onInputChange,
  isVerified,
  setIsVerified,
  contractId,
  contract,
}) => {
  const cmsData = {
    paymentMethod: 'CMS',
    accountNumber: paymentData.accountNumber,
    accountOwner: paymentData.accountHolder,
    accountOwnerBirth: paymentData.accountOwnerBirth,
  };

  const handleCMSVerification = async () => {
    try {
      const result = await verifyCMS(cmsData);
      if (result === true) {
        setIsVerified(true);
        alert('계좌 인증이 성공적으로 완료되었습니다.');
      } else {
        setIsVerified(false);
        alert('계좌 인증에 실패했습니다. 다시 시도해주세요.');
      }
    } catch (error) {
      console.error('CMS verification error:', error);
      setIsVerified(false);
      alert('계좌 인증에 실패했습니다. 다시 시도해주세요.');
    }
  };

  const handleInputChange = e => {
    const { name, value } = e.target;
    let formattedValue = value;

    if (name === 'accountOwnerBirth') {
      formattedValue = formatBirthDate(value);
    }
    onInputChange(name, formattedValue);
  };

  const isDisabled =
    !paymentData.bank ||
    !paymentData.accountHolder ||
    !paymentData.accountOwnerBirth ||
    !paymentData.accountNumber;

  return (
    <div className='flex flex-col bg-white p-1'>
      <form className='space-y-4'>
        <SelectField
          label='은행'
          name='bank'
          required
          options={bankOptions}
          value={paymentData.bank}
          onChange={handleInputChange}
          disabled={!!contract.paymentMethodInfo}
        />
        <Input
          label='예금주명'
          name='accountHolder'
          type='text'
          required
          placeholder='최대 20자리'
          value={paymentData.accountHolder}
          onChange={handleInputChange}
          maxLength={20}
          isValid={
            paymentData.accountHolder === '' || validateField('name', paymentData.accountHolder)
          }
          errorMsg='올바른 예금주명을 입력해주세요.'
          disabled={!!contract.paymentMethodInfo}
        />
        <Input
          label='생년월일'
          name='accountOwnerBirth'
          type='text'
          required
          placeholder='YYYY-MM-DD (예: 1990-01-01)'
          value={paymentData.accountOwnerBirth}
          onChange={handleInputChange}
          maxLength={10}
          isValid={
            paymentData.accountOwnerBirth === '' ||
            validateField('birth', paymentData.accountOwnerBirth)
          }
          errorMsg='올바른 생년월일을 입력해주세요.'
          disabled={!!contract.paymentMethodInfo}
        />
        <Input
          label='계좌번호'
          name='accountNumber'
          type='text'
          required
          placeholder='최대 14자리'
          value={paymentData.accountNumber}
          onChange={handleInputChange}
          maxLength={14}
          isValid={
            paymentData.accountNumber === '' ||
            !!contractId ||
            validateField('accountNumber', paymentData.accountNumber)
          }
          errorMsg='올바른 계좌번호를 입력해주세요.'
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
        onClick={handleCMSVerification}
        disabled={isVerified || isDisabled}>
        {isVerified ? '인증 완료' : '계좌 인증하기'}
      </button>
    </div>
  );
};

export default React.memo(PaymentCMS);
