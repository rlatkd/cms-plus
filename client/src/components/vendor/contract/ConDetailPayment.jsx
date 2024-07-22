import { useNavigate, useParams } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import ConDetailPaymentType from './ConDetailPaymentType';
import ConDetailPaymentMethod from './ConDetailPaymentMethod';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import bankCode from '@/utils/bank/bankCode';
import { formatCardYearForDisplay } from '@/utils/format/formatCardDate';

const ConDetailPayment = ({ contractData }) => {
  const { setContractInfoItem, setContractProducts } = useMemberContractStore(); // 계약정보 - 수정목적
  const {
    setPaymentType,
    setPaymentTypeInfoReq_Virtual,
    setAvailableMethods,
    setPaymentTypeInfoReq_Auto,
    setPaymentMethod,
    setPaymentMethodInfoReq_Cms,
    setPaymentMethodInfoReq_Card,
  } = useMemberPaymentStore(); // 결제정보 - 수정목적

  const navigate = useNavigate();
  const { id: contractId } = useParams();

  const handleButtonClick = () => {
    navigate(`/vendor/contracts/payment/update/${contractId}`);
  };

  // <------ 회원 계약 정보 zustand에 입력 ------>
  const updateContractInfo = data => {
    setContractInfoItem({
      contractName: data.contractName,
      contractDay: data.contractDay,
      contractStartDate: data.contractStartDate,
      contractEndDate: data.contractEndDate,
    });
    setContractProducts(data.contractProducts);
  };

  // <------ 회원 결제 정보(결제방식) zustand에 입력 ------>
  const updatePaymentTypeInfo = data => {
    setPaymentType(data.paymentType.code);

    if (data.paymentType.code === 'VIRTUAL') {
      setPaymentTypeInfoReq_Virtual({
        bank: bankCode[data.bank.code],
        accountOwner: data.accountOwner,
      });
    }

    if (data.paymentType.code === 'BUYER') {
      setAvailableMethods(
        data.availableMethods.map(method => {
          return method.code;
        })
      );
    }

    if (data.paymentType.code === 'AUTO') {
      setPaymentTypeInfoReq_Auto({
        consentImgUrl: data.consentImgUrl,
        simpleConsentReqDateTime: data.simpleConsentReqDateTime,
      });
    }
  };

  // <------ 회원 결제 정보(결제수단) zustand에 입력 ------>
  const updatePaymentMethodInfo = data => {
    if (data) {
      setPaymentMethod(data.paymentMethod.code);

      if (data.paymentMethod.code === 'CMS') {
        setPaymentMethodInfoReq_Cms({
          bank: bankCode[data.bank.code],
          accountNumber: data.accountNumber,
          accountOwner: data.accountOwner,
          accountOwnerBirth: data.accountOwnerBirth,
        });
      }

      if (data.paymentMethod.code === 'CARD') {
        setPaymentMethodInfoReq_Card({
          cardNumber: data.cardNumber,
          cardMonth: data.cardMonth,
          cardYear: formatCardYearForDisplay(data.cardYear),
          cardOwner: data.cardOwner,
          cardOwnerBirth: data.cardOwnerBirth,
        });
      }
    }
  };

  return (
    <div className='bg-white p-4 rounded-lg shadow w-1/2'>
      <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
        <p className='text-text_black text-xl font-800'>결제정보</p>
        <button
          className='flex justify-between items-center px-4 py-2 ml-4 text-mint
            font-700 rounded-md border border-mint cursor-pointer'
          onClick={() => {
            updateContractInfo(contractData);
            updatePaymentTypeInfo(contractData.paymentTypeInfo);
            updatePaymentMethodInfo(contractData.paymentMethodInfo);
            handleButtonClick();
          }}>
          <img src={edit} alt='edit' className='mr-2 ' />
          <p>결제수정</p>
        </button>
      </div>
      <ConDetailPaymentType contractData={contractData} />
      {contractData.paymentTypeInfo.paymentType.code !== 'BUYER' && (
        <>
          <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
            <p className='text-text_black text-xl font-800'>현재 결제수단</p>
          </div>
          <ConDetailPaymentMethod contractData={contractData} />
        </>
      )}
    </div>
  );
};

export default ConDetailPayment;
