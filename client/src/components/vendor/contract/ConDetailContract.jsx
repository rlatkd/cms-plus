import { useNavigate, useParams } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import { useMemberContractStore } from '@/stores/useMemberContractStore';

const productForm = (product, idx) => {
  return (
    <div
      key={idx}
      className='flex justify-between border border-ipt_border px-5 py-5 m-3 rounded-lg'>
      <p>{product.name}</p>
      <p>{product.price.toLocaleString()}원</p>
      <p>{product.quantity}개</p>
    </div>
  );
};

const ConDetailContract = ({ contractData }) => {
  const { setContractInfoItem, setContractProducts } = useMemberContractStore(); // 계약정보 - 수정목적
  const navigate = useNavigate();

  const { id: contractId } = useParams();

  const handleButtonClick = () => {
    navigate(`/vendor/contracts/product/update/${contractId}`);
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

  return (
    <div className='sub-dashboard w-1/2 h-full'>
      <div className='flex justify-between items-center border-b border-ipt_border px-2 pb-3 pt-1 h-1/12'>
        <p className='text-text_black text-xl font-800'>계약정보</p>
        <button
          className='flex justify-between items-center px-4 py-2 ml-4 text-mint
            font-700 rounded-md border border-mint cursor-pointer'
          onClick={() => {
            updateContractInfo(contractData);
            handleButtonClick();
          }}>
          <img src={edit} alt='edit' className='mr-2 ' />
          <p>계약수정</p>
        </button>
      </div>
      <div className='my-8 flex flex-col h-5/6'>
        <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3 mx-5'>
          <p className='text-text_black text-lg font-400'>
            상품수 : {contractData.contractProducts.length}개
          </p>
          <p className='text-text_black text-lg font-400'>
            총금액 : {contractData.contractPrice.toLocaleString()}원
          </p>
        </div>
        <div className='mt-5 flex-1 overflow-y-auto scrollbar-custom'>
          {contractData.contractProducts.map((product, idx) => productForm(product, idx))}
        </div>
      </div>
    </div>
  );
};

export default ConDetailContract;
