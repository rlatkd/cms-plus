import { updateContractDetail } from '@/apis/contract';
import ContractInfoForm from '@/components/common/memberForm/ContractInfoForm';
import { useMemberContractStore } from '@/stores/useMemberContractStore';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import { useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const ProductInfoUpdatePage = () => {
  const navigate = useNavigate();
  const { contractInfo } = useMemberContractStore();
  const { id: contractId } = useParams();

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
      console.log(res);
      await navigate(`/vendor/contracts/detail/${contractId}`);
      onAlertClick();
    } catch (err) {
      console.error('axiosUpdateContractDetail => ', err.response.data);
    }
  };

  // <------ 계약정보 수정 성공 Alert창 ------>
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async () => {
    const result = await alertComp('계약정보가 수정되었습니다!');
  };

  return (
    <>
      <div className='up-dashboard relative mb-4 w-full desktop:h-[18%]'>progressivee</div>
      <div className='primary-dashboard flex flex-col relative h-[1000px] large_desktop:h-[80%] '>
        <div className='flex items-center h-[50px] px-2 pb-[10px] '>
          <p className='text-text_black text-xl font-800'>기본정보</p>
        </div>
        <ContractInfoForm formType='UPDATE' />

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
      </div>
    </>
  );
};

export default ProductInfoUpdatePage;
