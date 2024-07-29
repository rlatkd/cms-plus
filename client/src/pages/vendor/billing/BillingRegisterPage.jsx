import { useState, useEffect, useCallback, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { getContractList, getContractProducts } from '@/apis/contract';
import { getAllProductList } from '@/apis/product';
import useDebounce from '@/hooks/useDebounce';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes, faSave } from '@fortawesome/free-solid-svg-icons';
import BillingForm from '@/components/vendor/billing/register/BillingRegisterBillingForm';
import ContractList from '@/components/vendor/billing/register/BillingRegisterContractList';
import { createBilling } from '@/apis/billing';
import useAlert from '@/hooks/useAlert';

const BillingRegisterPage = () => {
  /**
   * 계약 목록 페이징
   */
  const [contractList, setContractList] = useState([]);
  const [searchType, setSearchType] = useState('memberName');
  const [searchTerm, setSearchTerm] = useState('');
  const [totalPages, setTotalPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageGroup, setPageGroup] = useState(0);
  const [selectedContract, setSelectedContract] = useState(null);
  const debouncedSearchTerm = useDebounce(searchTerm, 500);

  const [products, setProducts] = useState([]); // 전체 상품 목록

  /**
   * 청구 생성 정보
   */
  const [billingData, setBillingData] = useState({
    billingType: '',
    billingDate: '',
    products: [],
  });

  const onAlert = useAlert();

  const fetchContractList = useCallback(
    async (page = currentPage) => {
      try {
        const res = await getContractList({
          [searchType]: searchTerm,
          page,
          size: 10,
        });
        setContractList(res.data.content);
        setTotalPages(res.data.totalPage || 1);
      } catch (err) {
        console.error('청구 생성 - 계약 목록 조회 실패', err);
      }
    },
    [searchType, searchTerm, currentPage]
  );

  const fetchContractProducts = useCallback(async contractId => {
    try {
      const res = await getContractProducts(contractId);
      return res.data;
    } catch (err) {
      console.error('청구 생성 - 계약상품 목록 조회 실패', err);
      return [];
    }
  }, []);

  const fetchAllProducts = useCallback(async () => {
    try {
      const res = await getAllProductList();
      setProducts(res.data);
    } catch (err) {
      console.error('청구 생성 - 상품 목록 조회 실패', err);
    }
  }, []);

  useEffect(() => {
    fetchAllProducts();
  }, [fetchAllProducts]);

  useEffect(() => {
    fetchContractList();
    setCurrentPage(1);
    setPageGroup(0);
  }, [debouncedSearchTerm]);

  useEffect(() => {
    fetchContractList(currentPage);
  }, [currentPage, fetchContractList]);

  const navigate = useNavigate();

  /**
   * 선택한 계약의 약정일을 기반으로 청구의 결제일을 계산한다.
   */
  const calculateBillingDate = contractDay => {
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const day = parseInt(contractDay);

    let billingDate = new Date(year, month, day);

    // 결제일은 오늘 이후 날짜여야한다.
    if (billingDate < today) {
      billingDate.setMonth(billingDate.getMonth() + 1);
    }

    return billingDate.toISOString().split('T')[0];
  };

  const handleSelectContract = async contract => {
    const contractProducts = await fetchContractProducts(contract.contractId);
    console.log('contractProducts', contractProducts);
    setBillingData(prev => ({
      ...prev,
      contractId: contract.contractId,
      billingDate: calculateBillingDate(contract.contractDay),
      products: contractProducts,
    }));
    setSelectedContract(contract);
  };

  const handleBillingDataChange = (key, value) => {
    setBillingData(prev => ({ ...prev, [key]: value }));
  };

  const handleProductAdd = newProduct => {
    if (!newProduct) return;
    if (billingData.products.length === 10) {
      alert('청구는 최대 10 개의 상품을 지녀야합니다.');
    }
    setBillingData(prev => ({
      ...prev,
      products: [...prev.products, { ...newProduct, quantity: 1 }],
    }));
  };

  const handleProductChange = (idx, column, to) => {
    const newBillingProducts = [...billingData.products];
    newBillingProducts[idx] = { ...newBillingProducts[idx], [column]: to };
    setBillingData(prev => ({
      ...prev,
      products: newBillingProducts,
    }));
  };

  const handleProductRemove = productId => {
    if (billingData.products.length < 2) {
      alert('청구는 최소 한 개의 상품을 지녀야합니다.');
      return;
    }
    setBillingData(prev => ({
      ...prev,
      products: prev.products.filter(p => p.productId !== productId),
    }));
  };

  const handleBillingSubmit = async () => {
    try {
      await createBilling(billingData);
      onAlert('청구가 생성되었습니다.', 'success', '청구 생성 완료');
      navigate(-1);
    } catch (err) {
      console.log(err);
      onAlert('', 'error', '청구 생성 실패', err);
      console.error('axiosBillingCreate => ', err.response);
    }
  };

  return (
    <div className='primary-dashboard flex flex-col h-full'>
      <div className='flex flex-1 overflow-hidden'>
        {/* 왼쪽: 계약 목록 */}
        <ContractList
          searchType={searchType}
          setSearchType={setSearchType}
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
          contractList={contractList}
          handleSelectContract={handleSelectContract}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalPages={totalPages}
          pageGroup={pageGroup}
          setPageGroup={setPageGroup}
        />

        {/* 중앙 구분선 */}
        <div className='w-px bg-ipt_border' />

        {/* 오른쪽: 청구 생성 정보 */}
        <div className='w-3/5 p-6 flex flex-col h-full overflow-hidden'>
          <h2 className='text-2xl font-semibold mb-4 text-text_black'>청구 생성 정보</h2>
          {selectedContract ? (
            <div className='flex-1 overflow-hidden flex flex-col'>
              <BillingForm
                billingData={billingData}
                handleBillingDataChange={handleBillingDataChange}
                products={products}
                handleProductAdd={handleProductAdd}
                handleProductChange={handleProductChange}
                handleProductRemove={handleProductRemove}
              />
            </div>
          ) : (
            <div className='flex-1 flex items-center justify-center'>
              <p className='text-center text-xl text-gray-500'>계약을 선택해주세요.</p>
            </div>
          )}
        </div>
      </div>

      {/* 하단 버튼 영역 */}
      <div className='flex justify-end space-x-4 p-6 bg-white'>
        <button
          type='button'
          onClick={() => navigate(-1)}
          className='flex justify-between items-center px-4 py-2 ml-2 
    font-700 rounded-md border cursor-pointer transition-all duration-200 ease-in-out text-red-500 border-red-500 hover:bg-red-50'>
          <FontAwesomeIcon icon={faTimes} className='mr-2' />
          <p>취소</p>
        </button>
        <button
          onClick={handleBillingSubmit}
          className='flex items-center px-4 py-2 text-white border border-mint bg-mint hover:bg-mint_hover rounded'>
          <FontAwesomeIcon icon={faSave} className='mr-2' />
          <p>청구 생성</p>
        </button>
      </div>
    </div>
  );
};

export default BillingRegisterPage;
