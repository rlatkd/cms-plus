import { getContractDetail } from '@/apis/contract';
import ConDetailBilling from '@/components/vendor/contract/ConDetailBilling';
import ConDetailBillingList from '@/components/vendor/contract/ConDetailBillingList';
import ConDetailContract from '@/components/vendor/contract/ConDetailContract';
import ConDetailHeader from '@/components/vendor/contract/ConDetailHeader';
import ConDetailPayment from '@/components/vendor/contract/ConDetailPayment';
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
      title: '',
    },
    paymentMethodInfo: null,
    totalBillingCount: 0,
    totalBillingPrice: 0,
  });

  const [isLoading, setLoading] = useState(false);
  const { id: contractId } = useParams();

  // <------ 계약 상세 조회 ------>
  const axiosContractDetail = async () => {
    try {
      setLoading(true);
      const res = await getContractDetail(contractId);
      console.log('!----계약 상세 조회 성공----!');
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
    <div>
      <ConDetailHeader contractData={contractData} />
      <div className='flex gap-6 mb-6 h-730'>
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
