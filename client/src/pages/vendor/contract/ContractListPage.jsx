import MemberChooseModal from '@/components/vendor/modal/MemberChooseModal';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Table from '@/components/common/tables/Table.jsx';
import { getContractList } from '@/apis/contract.js';

// 계약 목록 컬럼
const cols = [
  'No',
  '회원이름',
  '휴대전화',
  '약정일',
  '상품',
  '계약금액',
  '계약상태',
  '결제방식',
];

const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '' },
  { key: 'No', type: 'hidden', value: '' },
  { key: '회원이름', type: 'text', value: '' },
  { key: '휴대전화', type: 'text', value: '' },
  {
    key: '약정일',
    type: 'select',
    value: '',
    options: ['1일', '7일', '12일', '24일'],
  },
  { key: '상품', type: 'text', value: '' },
  { key: '계약금액', type: 'number', value: '' },
  {
    key: '계약상태',
    type: 'select',
    value: '',
    options: ['계약종료', '진행중', '대기중'],
  },
  {
    key: '결제방식',
    type: 'select',
    value: '',
    options: ['자동결제', '납부자결제', '가상계좌'],
  },
];

const ContractListPage = () => {
  const [isShowModal, setIsShowModal] = useState(false);
  const [search, setSearch] = useState(initialSearch);
  const [items, setItems] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() =>  {
    const fetchContractList = async (searchParams = {}) => {
      const res = await getContractList(searchParams, currentPage);
      setItems(res.data.content);
    }

    fetchContractList().catch((err) => console.log(err));
  }, [currentPage]);

  // 결과값 테이블 매핑
  const mapContentToCols = (content) => {
    const mapContractProductsToCol = (products) => {
      return `${products[0].name}${(products.length === 1) ? '' : ` +${products.length - 1}`}`;
    };

    return content.map((item, index) => ({
        No: index + 1,
        회원이름: item.memberName,
        휴대전화: item.memberPhone,
        약정일: item.contractDay,
        상품: mapContractProductsToCol(item.contractProducts),
        계약금액: item.contractPrice,
        계약상태: item.contractStatus,
        결제방식: item.paymentType.title,
        contractId: item.contractId
    }));
  };

  const handleSearchChange = (key, value) => {
    setSearch(prev =>
      prev.map(searchItem =>
        searchItem.key === key ? { ...searchItem, value: value } : searchItem
      )
    );
  };

  // 검색 API 들어갈 함수
  const show = () => {
    console.log(search);
  };

  const navigate = useNavigate();

  const handleRowClick = (contractId) => {
    navigate(`detail/${contractId}`);
  };

  return (
    <div className='primary-dashboard h-full w-full'>
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white'
        onClick={() => setIsShowModal(true)}>
        임시 기존 회원 계약 등록
      </button>
      <Table
        cols={cols}
        search={search}
        items={mapContentToCols(items)}
        handleSearchChange={handleSearchChange}
        onRowClick={item => handleRowClick(item.contractId)}
        show={show}
      />
      <MemberChooseModal
        isShowModal={isShowModal}
        setIsShowModal={setIsShowModal}
        modalTitle={'회원선택'}
      />
    </div>
  );
};

export default ContractListPage;
