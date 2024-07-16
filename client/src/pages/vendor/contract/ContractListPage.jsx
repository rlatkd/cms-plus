import MemberChooseModal from '@/components/vendor/modal/MemberChooseModal';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Table from '@/components/common/tables/Table.jsx';
import { getContractList } from '@/apis/contract.js';
import PagiNation from '@/components/common/PagiNation';
import SortSelect from '@/components/common/selects/SortSelect';
import MoveButton from '@/components/common/buttons/MoveButton';
import registerUser from '@/assets/registerUser.svg';
import file from '@/assets/file.svg';
import sign from '@/assets/sign.svg';

const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'memberName', label: '회원이름', width: 'w-1/12' },
  { key: 'memberPhone', label: '휴대전화', width: 'w-2/12' },
  { key: 'contractDay', label: '약정일', width: 'w-1/12' },
  { key: 'contractProducts', label: '상품', width: 'w-2/12' },
  { key: 'contractPrice', label: '계약금액', width: 'w-2/12' },
  { key: 'paymentType', label: '결제방식', width: 'w-2/12' },
  { key: 'contractEnabled', label: '계약상태', width: 'w-2/12' }, // 계약상태
];

// Type : hidden, text, num, calendar, select
const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'memberName', type: 'text', value: '', width: 'w-1/12' },
  { key: 'memberPhone', type: 'text', value: '', width: 'w-2/12' },
  {
    key: 'contractDay',
    type: 'select',
    value: '',
    width: 'w-1/12',
    options: Array.from({ length: 31 }, (_, i) => `${i + 1}일`),
  },
  { key: 'contractProducts', type: 'text', value: '', width: 'w-2/12' },
  { key: 'contractPrice', type: 'num', value: '', width: 'w-2/12' },
  {
    key: 'paymentType',
    type: 'select',
    value: '',
    width: 'w-2/12',
    options: ['자동결제', '납부자결제', '가상계좌'],
  },
  {
    key: 'contractEnabled',
    type: 'select',
    value: '',
    width: 'w-2/12',
    options: ['계약종료', '진행중', '대기중'],
  },
];

const selectOptions = [
  { label: '계약금액 많은순', orderBy: 'contractPrice', order: 'DESC' },
  { label: '계약금액 적은순', orderBy: 'contractPrice', order: 'ASC' },
];

const ContractListPage = () => {
  const [contractList, setContractList] = useState([]); // 회원 목록
  const [search, setSearch] = useState(initialSearch); // 검색 조건
  const [currentSearchParams, setCurrentSearchParams] = useState({}); // 현재 검색 조건

  const [isShowModal, setIsShowModal] = useState(false);

  const [order, setOrder] = useState(''); // 정렬 방향
  const [orderBy, setOrderBy] = useState(''); // 정렬 항목

  const [totalPages, setTotalPages] = useState(1); // 전체 페이지 수
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [pageGroup, setPageGroup] = useState(0); // 현재 페이지 그룹
  const buttonCount = 5; // 버튼 갯수

  const navigate = useNavigate();

  // 회원 목록 조회
  const axiosContractList = useCallback(
    async (searchParams = {}, page = currentPage) => {
      try {
        const res = await getContractList({
          size: 10,
          page: page,
          ...searchParams,
        });
        const transformedData = transformContractListItem(res.data.content);
        console.log(transformedData);
        setContractList(transformedData);
        setTotalPages(res.data.totalPage || 1);
      } catch (err) {
        console.error('axiosMemberList => ', err.response.data);
      }
    },
    [currentPage]
  );

  // 계약 데이터 값 정제
  const transformContractListItem = data => {
    // 데이터 변환
    return data.map(contract => {
      const { contractDay, contractPrice, contractProducts, contractEnabled } = contract;
      const firstProduct = contractProducts[0];
      const additionalProductsCount = contractProducts.length - 1;

      return {
        ...contract,
        contractDay: `${contractDay}일`,
        contractPrice: `${contractPrice}원`,
        contractProducts: `${firstProduct.name} + ${additionalProductsCount}`,
        contractEnabled: contractEnabled ? '진행중' : '계약종료',
        paymentType: contract.paymentType.title,
      };
    });
  };

  // 검색 변경 핸들러
  const handleChangeSearch = (key, value) => {
    const updatedSearch = search.map(searchItem =>
      searchItem.key === key ? { ...searchItem, value: value } : searchItem
    );

    let searchParams = { size: 10, order: order, orderBy: orderBy };
    updatedSearch.forEach(searchMember => {
      if (searchMember.value) {
        searchParams[searchMember.key] = searchMember.value;
      }
    });

    setSearch(updatedSearch);
    setCurrentSearchParams(searchParams);
  };

  // 검색 클릭 이벤트 핸들러
  const handlehClickSearch = async () => {
    axiosContractList(currentSearchParams);
    setCurrentPage(1); // 검색 후 현재 페이지 초기화
    setPageGroup(0); // 검색 후 페이지 그룹 초기화
  };

  // 회원 상세 조회 페이지 이동
  const MoveContractDetail = async contractId => {
    console.log(contractId);
    navigate(`detail/${contractId}`);
  };

  useEffect(() => {
    axiosContractList(currentPage);
  }, [currentPage]);

  return (
    <div className='primary-dashboard flex flex-col h-1500 desktop:h-full'>
      <div className='flex justify-between pt-2 pb-4 w-full'>
        <div className='flex items-center '>
          <img
            className='bg-mint h-7 w-7 p-2 rounded-md ml-1 mr-3'
            src='/src/assets/user.svg'
            alt='user'
          />
          <p className='text-text_black font-700 mr-5'>총 24건</p>
          <SortSelect
            setOrder={setOrder}
            setOrderBy={setOrderBy}
            selectOptions={selectOptions}
            currentSearchParams={currentSearchParams}
            axiosList={axiosContractList}
          />
        </div>

        <div>
          <div className='flex'>
            <MoveButton
              imgSrc={sign}
              color={'white'}
              buttonText='간편 서명 동의'
              onClick={() => setIsShowModal(true)}
            />
            <MoveButton
              imgSrc={file}
              color={'mint'}
              buttonText='신규 회원 계약 등록'
              onClick={() => navigate('/vendor/members/register')}
            />
            <MoveButton
              imgSrc={file}
              color={'mint'}
              buttonText='기존 회원 계약 등록'
              onClick={() => setIsShowModal(true)}
            />
          </div>
        </div>
      </div>

      <Table
        cols={cols}
        rows={contractList}
        search={search}
        currentPage={currentPage}
        handleChangeSearch={handleChangeSearch}
        handlehClickSearch={handlehClickSearch}
        onRowClick={item => MoveContractDetail(item.contractId)}
      />

      <PagiNation
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPages={totalPages}
        pageGroup={pageGroup}
        setPageGroup={setPageGroup}
        buttonCount={buttonCount}
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
