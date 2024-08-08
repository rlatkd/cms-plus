import ChooseBank from '@/components/member/account/ChooseBank';
import AccountInfo from '@/components/member/account/AccountInfo';
import Loading from '@/components/common/member/Loading';
import Success from '@/components/common/member/Success';

import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';

import { useStatusStore } from '@/stores/useStatusStore';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useInvoiceStore } from '@/stores/useInvoiceStore';
import { requestAccountPayment } from '@/apis/payment';
import { useEffect, useState } from 'react';
import { validateField } from '@/utils/validators';
import { useNavigate, useParams } from 'react-router-dom';

const PaymentAccountPage = () => {
  const start = 0;
  const end = 6;
  const { status, reset, setStatus } = useStatusStore();
  const { handleClickPrevious, handleClickNext: originalHandleClickNext } = useStatusStepper(
    'account',
    start,
    end
  );
  const [isVerified, setIsVerified] = useState(false);
  const { invoiceInfo, selectedBank } = useInvoiceStore();

  const [billingId, setBillingId] = useState('');
  const [email, setEmail] = useState('');

  const { invoiceId } = useParams();
  const navigate = useNavigate();

  const validateSelectedBank = () => {
    const missingFields = [];
    if (!selectedBank) missingFields.push('은행 선택');

    return { missingFields };
  };

  const validateBankInfo = () => {
    const { accountNumber, accountOwner, accountOwnerBirth } = accountInfo;
    const missingFields = [];
    const invalidFields = [];

    if (!isVerified) missingFields.push('계좌 인증');

    if (!accountOwner) missingFields.push('예금주');
    else if (!validateField('name', accountOwner)) invalidFields.push('예금주');

    if (!accountOwnerBirth) missingFields.push('생년월일');
    else if (!validateField('birth', accountOwnerBirth)) invalidFields.push('생년월일');

    if (!accountNumber) missingFields.push('계좌번호');
    else if (!validateField('accountNumber', accountNumber)) invalidFields.push('계좌번호');

    return { missingFields, invalidFields };
  };

  const handleClickNext = async () => {
    let missingFields = [];
    let invalidFields = [];
    let selectedBankValidation;
    let bankInfoValidation;

    switch (status) {
      case 3:
        selectedBankValidation = validateSelectedBank();
        missingFields = selectedBankValidation.missingFields;
        break;

      case 4:
        bankInfoValidation = validateBankInfo();
        missingFields = bankInfoValidation.missingFields;
        invalidFields = bankInfoValidation.invalidFields;
        break;

      default:
        break;
    }

    if (missingFields.length > 0) {
      alert(`다음 필드를 입력해주세요: ${missingFields.join(', ')}`);
      return;
    }

    if (invalidFields.length > 0) {
      alert(`다음 필드의 형식이 올바르지 않습니다: ${invalidFields.join(', ')}`);
      return;
    }

    // 모든 유효성 검사를 통과한 경우에만 실행
    if (status === 4 && missingFields.length === 0 && invalidFields.length === 0) {
      try {
        await axiosAccountPayment();
        // 결제가 성공적으로 완료된 경우에만 다음 단계로 진행
        originalHandleClickNext();
      } catch (error) {
        console.error('Account payment failed:', error);
        alert('계좌 결제에 실패했습니다. 다시 시도해주세요.');
        return;
      }
    } else {
      // status가 4가 아니거나, 유효성 검사를 통과하지 못한 경우
      originalHandleClickNext();
    }
  };

  const [accountInfo, setAccountInfo] = useState({
    accountNumber: '',
    accountOwner: '',
    accountOwnerBirth: '',
  });

  const componentMap = {
    3: () => <ChooseBank billingInfo={invoiceInfo} />, //은행선택
    4: AccountInfo, // 계좌정보 입력
    5: () => <Loading content={'결제중...'} />, // 결제로딩 대충 로딩하다가 success로 가도록 해야됨. 결제결과는 문자로 날라감
    6: () => <Success content='결제가 완료되었습니다!' />, // 입금완료
  };

  const Content = componentMap[status] || (() => 'error');

  const number = accountInfo.accountNumber; //계좌번호
  const method = 'ACCOUNT';

  const paymentData = {
    billingId: billingId,
    email: email,
    method: method,
    number: number,
  };

  const axiosAccountPayment = async () => {
    try {
      const res = await requestAccountPayment(paymentData);
    } catch (err) {
      console.error('axiosAccountPayment => ', err.response);
    }
  };

  useEffect(() => {
    if (invoiceInfo) {
      setBillingId(invoiceInfo.billingId);
      setEmail(invoiceInfo.member.email);
    } else {
      reset();
      navigate(`/member/invoice/${invoiceId}`);
    }
  }, []);

  // <----- 로딩 타임아웃 설정 ----->
  useEffect(() => {
    if (status === 5) {
      const timer = setTimeout(() => {
        setStatus(6);
      }, 2500);

      return () => clearTimeout(timer);
    }
  }, [status, setStatus]);

  return (
    <>
      <Content
        accountInfo={accountInfo}
        setAccountInfo={setAccountInfo}
        isVerified={isVerified}
        setIsVerified={setIsVerified}
      />
      {status != 5 && status != 6 && (
        <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
          <PreviousButton onClick={handleClickPrevious} status={status} start={start} end={end} />
          <NextButton onClick={handleClickNext} type={'account'} status={status} end={end} />
        </div>
      )}
    </>
  );
};

export default PaymentAccountPage;
