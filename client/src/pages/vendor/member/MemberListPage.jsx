import MoveButton from '@/components/common/buttons/MoveButton';
import PagiNation from '@/components/common/PagiNation';
import SortSelect from '@/components/common/selects/SortSelect';
import Table from '@/components/common/tables/Table';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import registerManyUser from '@/assets/registerManyUser.svg';
import registerUser from '@/assets/registerUser.svg';
import { getMemberDetail, getMemberList } from '@/apis/member';
import MemberExcelModal from '@/components/vendor/modal/MemberExcelModal';

const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'memberName', label: '회원이름', width: 'w-2/12' },
  { key: 'memberPhone', label: '휴대전화', width: 'w-2/12' },
  { key: 'memberEmail', label: '이메일', width: 'w-3/12' },
  { key: 'memberEnrollDate', label: '회원등록일', width: 'w-2/12' },
  { key: 'contractPrice', label: '계약금액합', width: 'w-2/12' },
  { key: 'contractCount', label: '계약건수', width: 'w-1/12' },
];

// Type : hidden, text, num, calendar, select
const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'memberName', type: 'text', value: '', width: 'w-2/12' },
  { key: 'memberPhone', type: 'text', value: '', width: 'w-2/12' },
  { key: 'memberEmail', type: 'text', value: '', width: 'w-3/12' },
  { key: 'memberEnrollDate', type: 'calendar', value: '', width: 'w-2/12' },
  { key: 'contractPrice', type: 'num', value: '', width: 'w-2/12' },
  { key: 'contractCount', type: 'num', value: '', width: 'w-1/12' },
];

const selectOptions = [
  { label: '계약금액 많은순', orderBy: 'contractPrice', order: 'DESC' },
  { label: '계약금액 적은순', orderBy: 'contractPrice', order: 'ASC' },
  { label: '계약 많은순', orderBy: 'contractCount', order: 'DESC' },
  { label: '계약 적은순', orderBy: 'contractCount', order: 'ASC' },
];

const MemberListPage = () => {
  const [memberList, setMemberList] = useState([]); // 회원 목록
  const [search, setSearch] = useState(initialSearch); // 검색 조건
  const [currentSearchParams, setCurrentSearchParams] = useState({}); // 현재 검색 조건

  const [order, setOrder] = useState(''); // 정렬 방향
  const [orderBy, setOrderBy] = useState(''); // 정렬 항목

  const [totalPages, setTotalPages] = useState(1); // 전체 페이지 수
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [pageGroup, setPageGroup] = useState(0); // 현재 페이지 그룹
  const buttonCount = 5; // 버튼 갯수

  const [isShowExcelModal, setIsShowExcelModal] = useState(false);  // 대량 회원 등록

  const navigate = useNavigate();

  // 회원 목록 조회
  const axiosMemberList = useCallback(
    async (searchParams = {}, page = currentPage) => {
      try {
        const res = await getMemberList({
          size: 10,
          page: page,
          ...searchParams,
        });
        setMemberList(res.data.content);
        setTotalPages(res.data.totalPage || 1);
      } catch (err) {
        console.error('axiosMemberList => ', err.response.data);
      }
    },
    [currentPage]
  );

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
    axiosMemberList(currentSearchParams);
    setCurrentPage(1); // 검색 후 현재 페이지 초기화
    setPageGroup(0); // 검색 후 페이지 그룹 초기화
  };

  // 회원 상세 조회 페이지 이동
  const MoveMemberDetail = async memberId => {
    console.log(memberId);
    navigate(`detail/${memberId}`);
  };

  useEffect(() => {
    axiosMemberList(currentPage);
  }, [currentPage]);

  return (
    <div className='primary-dashboard flex flex-col h-1500  desktop:h-full '>
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
            axiosList={axiosMemberList}
          />
        </div>

        <div>
          <div className='flex'>
            <MoveButton
              imgSrc={registerManyUser}
              buttonText='대량 회원 등록'
              onClick={() => setIsShowExcelModal(true)}
            />
            <MoveButton
              imgSrc={registerUser}
              buttonText='회원 등록'
              onClick={() => navigate('register')}
            />
          </div>
        </div>
      </div>
      <Table
        cols={cols}
        rows={memberList}
        search={search}
        currentPage={currentPage}
        handleChangeSearch={handleChangeSearch}
        handlehClickSearch={handlehClickSearch}
        onRowClick={item => MoveMemberDetail(item.memberId)}
      />

      <PagiNation
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPages={totalPages}
        pageGroup={pageGroup}
        setPageGroup={setPageGroup}
        buttonCount={buttonCount}
      />
      <MemberExcelModal
             isShowModal={isShowExcelModal}
             setIsShowModal={setIsShowExcelModal}
             // TODO 아이콘 변경 필요
             icon='/src/assets/user.svg'
             modalTitle={'대량 회원 등록'}
             axiosMemberList={axiosMemberList}
      />
    </div>
  );
};

export default MemberListPage;
