import MemberChooseModal from '@/components/vendor/modal/MemberChooseModal';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Table from '@/components/common/tables/Table.jsx';
import { getContractList } from '@/apis/contract.js';
import PagiNation from '@/components/common/PagiNation';
import SortSelect from '@/components/common/selects/SortSelect';
import MoveButton from '@/components/common/buttons/MoveButton';
import file from '@/assets/file.svg';
import sign from '@/assets/sign.svg';
import user from '@/assets/user.svg';
import File from '@/assets/File';
import { formatPhone } from '@/utils/format/formatPhone';
import useDebounce from '@/hooks/useDebounce';
import { cols, initialSearch, selectOptions } from '@/utils/tableElements/contractElement';
import { formatProducts } from '@/utils/format/formatProducts';

const ContractListPage = () => {
  const [contractList, setContractList] = useState([]); // 계약 목록
  const [contractListCount, setContractListCount] = useState(); // 계약 목록 전체 수
  const [search, setSearch] = useState(initialSearch); // 검색 조건
  const [currentSearchParams, setCurrentSearchParams] = useState({}); // 현재 검색 조건

  const [currentorder, setCurrentOrder] = useState(''); // 정렬 방향
  const [currentorderBy, setCurrentOrderBy] = useState(''); // 정렬 항목

  const [totalPages, setTotalPages] = useState(1); // 전체 페이지 수
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [pageGroup, setPageGroup] = useState(0); // 현재 페이지 그룹
  const buttonCount = 5; // 버튼 갯수

  const [isShowModal, setIsShowModal] = useState(false);

  const navigate = useNavigate();

  // <--------계약 목록 조회-------->
  const axiosContractList = useCallback(
    async (
      searchParams = {},
      order = currentorder,
      orderBy = currentorderBy,
      page = currentPage
    ) => {
      try {
        const res = await getContractList({
          ...searchParams,
          order: order,
          orderBy: orderBy,
          page: page,
          size: 10,
        });
        const transformdData = transformContractListItem(res.data.content);
        setContractList(transformdData);
        setContractListCount(res.data.totalCount);
        setTotalPages(res.data.totalPage || 1);
      } catch (err) {
        console.error('axiosMemberList => ', err.response.data);
      }
    },
    [currentPage]
  );

  // <--------데이터 변환-------->
  const transformContractListItem = data => {
    return data.map(contract => {
      const { contractDay, contractPrice, contractProducts, contractStatus, memberPhone } =
        contract;

      return {
        ...contract,
        contractDay: `${contractDay}일`,
        contractPrice: `${contractPrice.toLocaleString()}원`,
        contractProducts: formatProducts(contractProducts),
        contractStatus: contractStatus.title,
        paymentType: contract.paymentType.title,
        memberPhone: formatPhone(memberPhone),
      };
    });
  };

  // <--------검색 변경 핸들러-------->
  const handleChangeSearch = (key, value) => {
    const updatedSearch = search.map(searchItem =>
      searchItem.key === key ? { ...searchItem, value: value } : searchItem
    );

    let searchParams = {};
    updatedSearch.forEach(searchMember => {
      if (searchMember.value) {
        searchParams[searchMember.key] = searchMember.value;
      }
    });

    setSearch(updatedSearch);
    setCurrentSearchParams(searchParams);
  };

  // <--------검색 클릭 이벤트 핸들러-------->
  const handleClickSearch = async () => {
    axiosContractList(debouncedSearchParams);
    setCurrentPage(1); // 검색 후 현재 페이지 초기화
    setPageGroup(0); // 검색 후 페이지 그룹 초기화
  };

  // <--------계약 상세 조회 페이지 이동-------->
  const MoveContractDetail = async contractId => {
    navigate(`detail/${contractId}`);
  };

  // <--------디바운스 커스텀훅-------->
  const debouncedSearchParams = useDebounce(currentSearchParams, 500);

  useEffect(() => {
    handleClickSearch();
  }, [debouncedSearchParams]);

  useEffect(() => {
    axiosContractList(currentSearchParams, currentorder, currentorderBy, currentPage);
  }, [currentPage]);

  return (
    <div className='table-dashboard flex flex-col h-1500 extra_desktop:h-full '>
      <div className='flex justify-between pt-2 pb-4 w-full '>
        <div className='flex items-center '>
          <div className='bg-mint h-7 w-7 rounded-md ml-1 mr-3 flex items-center justify-center'>
            <File fill='#ffffff' />
          </div>
          <p className='text-text_black font-700 mr-5'>총 {contractListCount}건</p>
          <SortSelect
            setCurrentOrder={setCurrentOrder}
            setCurrentOrderBy={setCurrentOrderBy}
            selectOptions={selectOptions}
            currentSearchParams={currentSearchParams}
            axiosList={axiosContractList}
          />
        </div>

        <div>
          <div className='flex'>
            <MoveButton imgSrc={sign} color='white' buttonText='간편 서명 동의' />
            <MoveButton
              imgSrc={file}
              color='mint'
              buttonText='신규 회원 계약 등록'
              onClick={() => navigate('/vendor/members/register')}
            />
            <MoveButton
              imgSrc={file}
              color='mint'
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
        handleClickSearch={handleClickSearch}
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
        icon={user}
        setIsShowModal={setIsShowModal}
        modalTitle={'회원선택'}
      />
    </div>
  );
};

export default ContractListPage;
