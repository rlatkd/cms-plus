import { updateContractDetail } from '@/apis/contract';
import ContractInfoForm from '@/components/common/memberForm/ContractInfoForm';
import useAlert from '@/hooks/useAlert';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const UpdateContractInfo = ({ formType }) => {
  const { contractInfo } = useMemberContractStore();
  const { contractId, memberId } = useParams();
  const navigate = useNavigate();
  const onAlert = useAlert();

  // <------ 계약 정보 수정 요청Data 형태변환 ------>
  const transformContractInfo = contractInfo => ({
    contractName: contractInfo.contractName,
    contractProducts: contractInfo.contractProducts.map(product => ({
      productId: product.productId,
      price: product.price,
      quantity: product.quantity,
    })),
  });

  // <------ 계약 정보 수정 API ------>
  const axiosUpdateContractDetail = async () => {
    try {
      const trasformData = transformContractInfo(contractInfo);
      const res = await updateContractDetail(contractId, trasformData);
      console.log('!----계약 정보 수정 성공----!');
      await navigate(`/vendor/contracts/detail/${contractId}`);
      onAlert({ msg: '계약정보가 수정되었습니다!', type: 'success' });
    } catch (err) {
      console.error('axiosUpdateContractDetail => ', err);
      onAlert({
        msg: '계약 수정에 실패하였습니다.',
        type: 'error',
        title: '계약 수정 실패',
        err: err,
      });
      navigate(`/vendor/contracts/detail/${contractId}`);
    }
  };

  return (
    <>
      <ContractInfoForm formType={formType} />
      <div className='absolute bottom-0 left-0 flex h-[65px] w-full justify-end px-7 pb-5 font-800 text-lg '>
        <button
          className=' px-10 py-2 border border-mint rounded-lg text-mint'
          onClick={() => navigate(-1)}>
          취소
        </button>
        <button
          className=' px-10 py-2 bg-mint rounded-lg text-white transition-all duration-200 hover:bg-mint_hover ml-3'
          onClick={axiosUpdateContractDetail}>
          저장
        </button>
      </div>
    </>
  );
};

export default UpdateContractInfo;
