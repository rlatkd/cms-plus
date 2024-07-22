import { getContractDetail } from '@/apis/contract';
import ConDetailBilling from '@/components/vendor/contract/ConDetailBilling';
import ConDetailBillingList from '@/components/vendor/contract/ConDetailBillingList';
import ConDetailContract from '@/components/vendor/contract/ConDetailContract';
import ConDetailHeader from '@/components/vendor/contract/ConDetailHeader';
import ConDetailPayment from '@/components/vendor/contract/ConDetailPayment';
import { useMemberBillingStore } from '@/stores/useMemberBillingStore';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router';

const ContractDetailPage = () => {
  const [contractData, setContractData] = useState({
    contractId: 1,
    contractName: '',
    createdDateTime: '',
    modifiedDateTime: '',
    contractProducts: [],
    contractPrice: 0,
    contractStartDate: '',
    contractEndDate: '',
    paymentTypeInfo: {
      paymentType: {},
    },
    invoiceSendMethod: {
      title: ''
    },
    paymentMethodInfo: null,
    totalBillingCount: 0,
    totalBillingPrice: 0,
  });
  const { setContractInfoItem, setContractProducts } = useMemberContractStore(); // 계약정보 - 수정목적
  const { setBillingInfoItem } = useMemberBillingStore(); // 청구정보 - 수정목적
  const {
    setPaymentType,
    setPaymentTypeInfoReq_Virtual,
    setAvailableMethods,
    setPaymentTypeInfoReq_Auto,
    setPaymentMethod,
    setPaymentMethodInfoReq_Cms,
    setPaymentMethodInfoReq_Card,
  } = useMemberPaymentStore(); // 결제정보 - 수정목적

  const [isLoading, setLoading] = useState(false);

  const { id: contractId } = useParams();

  // <------ 회원 계약 정보 zustand에 입력 ------>
  const updateContractInfo = data => {
    setContractInfoItem({ contractName: data.contractName });
    setContractProducts(data.contractProducts);
  };

  // <------ 회원 청구 정보 zustand에 입력 ------>
  const updateBillingInfo = data => {
    setBillingInfoItem({
      invoiceSendMethod: data.invoiceSendMethod,
      autoInvoiceSend: data.autoInvoiceSend,
      autoBilling: data.autoBilling,
    });
  };

  // <------ 회원 결제 정보(결제방식) zustand에 입력 ------>
  const updatePaymentTypeInfo = data => {
    setPaymentType(data.paymentTypeInfo.paymentType.code);

    if (data.paymentTypeInfo.paymentType.code === 'VIRTUAL') {
      setPaymentTypeInfoReq_Virtual({
        bank: data.paymentTypeInfo.bank.title,
        accountOwner: data.paymentTypeInfo.accountOwner,
      });
    }

    if (data.paymentTypeInfo.paymentType.code === 'BUYER') {
      setAvailableMethods(data.paymentTypeInfo.availableMethods);
    }

    if (data.paymentTypeInfo.paymentType.code === 'AUTO') {
      setPaymentTypeInfoReq_Auto({
        consentImgUrl: data.paymentTypeInfo.consentImgUrl,
        simpleConsentReqDateTime: data.paymentTypeInfo.simpleConsentReqDateTime,
      });
    }
  };

  // <------ 회원 결제 정보(결제수단) zustand에 입력 ------>
  const updatePaymentMethodInfo = data => {
    if (data.paymentMethodInfo) {
      setPaymentMethod(data.paymentMethodInfo.paymentMethod.code);

      if (data.paymentMethodInfo.paymentMethod.code === 'CMS') {
        setPaymentMethodInfoReq_Cms({
          bank: data.paymentMethodInfo.bank.title,
          accountNumber: data.paymentMethodInfo.accountNumber,
          accountOwner: data.paymentMethodInfo.accountOwner,
          accountOwnerBirth: data.paymentMethodInfo.accountOwnerBirth,
        });
      }

      if (data.paymentMethodInfo.paymentMethod.code === 'CARD') {
        setPaymentMethodInfoReq_Card({
          cardNumber: data.paymentMethodInfo.cardNumber,
          cardMonth: data.paymentMethodInfo.cardMonth,
          cardYear: data.paymentMethodInfo.cardYear,
          cardOwner: data.paymentMethodInfo.cardOwner,
          cardOwnerBirth: data.paymentMethodInfo.cardOwnerBirth,
        });
      }
    }
  };

  // <------ 계약 상세 조회 ------>
  const axiosContractDetail = async () => {
    try {
      setLoading(true);
      const res = await getContractDetail(contractId);
      console.log('!----계약 상세 조회 성공----!');
      updateContractInfo(res.data);
      updatePaymentTypeInfo(res.data);
      updatePaymentMethodInfo(res.data);
      updateBillingInfo(res.data);
      setContractData(res.data);
      setLoading(false);
      console.log(res.data);
    } catch (err) {
      console.log('effect');
      console.error('axiosContrctDetail => ', err.response.data);
    }
  };

  useEffect(() => {
    axiosContractDetail();
  }, []);

  return (
    <div className='p-6'>
      <ConDetailHeader contractData={contractData} />
      <div className='flex gap-6 mb-6'>
        <ConDetailContract contractData={contractData} />
        <ConDetailPayment contractData={contractData} />
      </div>
      <ConDetailBilling contractData={contractData}>
        <ConDetailBillingList />
      </ConDetailBilling>
    </div>
  );
};

export default ContractDetailPage;
