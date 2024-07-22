import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { getContractList } from '@/apis/contract';
import { getAllProductList } from '@/apis/product';
import PagiNation from '@/components/common/PagiNation';
import InputWeb from '@/components/common/inputs/InputWeb';
import { ProductSelectField2 } from '@/components/common/selects/ProductSelectField';
import useDebounce from '@/hooks/useDebounce';
import { formatProducts } from '@/utils/formatProducts';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes, faSave, faTrash } from '@fortawesome/free-solid-svg-icons';
import BillingForm from '@/components/vendor/billing/register/BillingRegisterBillingForm';
import ContractList from '@/components/vendor/billing/register/BillingRegisterContractList';
import { createBilling } from '@/apis/billing';

const BillingRegisterPage = () => {
  const navigate = useNavigate();
  const [contractList, setContractList] = useState([]);
  const [contractListCount, setContractListCount] = useState(0);
  const [searchType, setSearchType] = useState('memberName');
  const [searchTerm, setSearchTerm] = useState('');
  const [totalPages, setTotalPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageGroup, setPageGroup] = useState(0);

  const [selectedContract, setSelectedContract] = useState(null);
  const [billingData, setBillingData] = useState({
    billingId: null,
    billingType: 'REGULAR',
    paymentDate: '',
    products: [],
  });
  const [products, setProducts] = useState([]);

  const debouncedSearchTerm = useDebounce(searchTerm, 500);

  const fetchContractList = useCallback(async (page = currentPage) => {
    try {
      const res = await getContractList({
        [searchType]: searchTerm,
        page,
        size: 10,
      });
      setContractList(res.data.content);
      setContractListCount(res.data.totalCount);
      setTotalPages(res.data.totalPage || 1);
    } catch (err) {
      console.error('Failed to fetch contract list:', err);
    }
  }, [searchType, searchTerm, currentPage]);

  const fetchAllProducts = useCallback(async () => {
    try {
      const res = await getAllProductList();
      setProducts(res.data);
    } catch (err) {
      console.error('Failed to fetch product list:', err);
    }
  }, []);

  useEffect(() => {
    fetchContractList();
    fetchAllProducts();
  }, [fetchContractList, fetchAllProducts, debouncedSearchTerm]);

  const transformContractListItem = (data) => {
    return data.map(contract => ({
      ...contract,
      contractDay: `${contract.contractDay}일`,
      contractPrice: `${contract.contractPrice.toLocaleString()}원`,
      contractProducts: formatProducts(contract.contractProducts),
    }));
  };

  const calculatePaymentDate = (contractDay) => {
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const day = parseInt(contractDay);

    let paymentDate = new Date(year, month, day);

    // 결제일은 오늘 이후 날짜여야한다.
    if (paymentDate < today) {
      paymentDate.setMonth(paymentDate.getMonth() + 1);
    }
    
    return paymentDate.toISOString().split('T')[0];
  };

  const handleSelectContract = (contract) => {
    setSelectedContract(contract);
    const paymentDate = calculatePaymentDate(contract.contractDay);
    setBillingData(prev => ({
      ...prev,
      contractId: contract.contractId,
      paymentDate: paymentDate,
      products: contract.contractProducts.map(product => ({
        productId: product.productId,
        name: product.name,
        price: product.price,
        quantity: product.quantity,
      })),
    }));
  };

  const handleBillingDataChange = (key, value) => {
    console.log(key, value);
    setBillingData(prev => ({ ...prev, [key]: value }));
  };

  const handleProductAdd = (newProduct) => {
    if (!newProduct) return;
    setBillingData(prev => ({
      ...prev,
      products: [...prev.products, { ...newProduct, quantity: 1 }],
    }));
  };

  const handleProductChange = (idx, column, to) => {
    const newBillingProducts = [...billingData.products];
    if (column === 'price') {
      newBillingProducts[idx].price = to;
    } else if (column === 'quantity') {
      newBillingProducts[idx].quantity = to;
    }

    setBillingData(prev => ({
      ...prev,
      products: newBillingProducts,
    }));
  };

  const handleProductRemove = (productId) => {
    setBillingData(prev => ({
      ...prev,
      products: prev.products.filter(p => p.productId !== productId),
    }));
  };

  const handleBillingSubmit = async () => {
    console.log('청구 데이터:', billingData);
    try {
        await createBilling(billingData);
        alert('청구 생성했습니다.');
        navigate(-1);
      } catch (err) {
        alert('청구 생성에 실패했습니다.');
        console.error('axiosBillingCreate => ', err.response.data);
      }
  };
  return (
    <div className='primary-dashboard flex flex-col h-full'>
      <div className="flex">
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
        <div className="w-px bg-gray-300"></div>

        {/* 오른쪽: 청구 생성 정보 */}
        <div className="w-3/5 p-6 overflow-auto">
          <h2 className="text-2xl font-semibold mb-4">청구 생성 정보</h2>
          {selectedContract ? (
            <BillingForm
              billingData={billingData}
              handleBillingDataChange={handleBillingDataChange}
              products={products}
              handleProductAdd={handleProductAdd}
              handleProductChange={handleProductChange}
              handleProductRemove={handleProductRemove}
            />
          ) : (
            <p>왼쪽에서 계약을 선택해주세요.</p>
          )}
        </div>
      </div>
      
      {/* 하단 버튼 영역 */}
      <div className="flex justify-end space-x-4 p-6 bg-gray-100">
        <button
          type="button"
          onClick={() => navigate(-1)}
          className="flex items-center px-4 py-2 text-negative border-negative hover:bg-negative hover:text-white rounded"
        >
          <FontAwesomeIcon icon={faTimes} className="mr-2" />
          <p>취소</p>
        </button>
        <button
          onClick={handleBillingSubmit}
          className="flex items-center px-4 py-2 text-white bg-mint hover:bg-mint_hover rounded"
        >
          <FontAwesomeIcon icon={faSave} className="mr-2" />
          <p>청구 생성</p>
        </button>
      </div>
    </div>
  );
};

export default BillingRegisterPage;