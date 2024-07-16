import MoveButton from '@/components/common/buttons/MoveButton';
import PagiNation from '@/components/common/PagiNation';
import SortSelect from '@/components/common/selects/SortSelect';
import Table from '@/components/common/tables/Table';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import registerManyUser from '@/assets/registerManyUser.svg';
import registerUser from '@/assets/registerUser.svg';

const rows = [
  {
    memberName: '박준형',
    memberPhone: '01012345678',
    memberEmail: 'member1@example.com',
    memberEnrollDate: '2020-01-01',
    contractPrice: 10000,
    contractCount: 1,
  },
  {
    memberName: '박민석',
    memberPhone: '01099999999',
    memberEmail: 'microsoft123@gmail.com',
    memberEnrollDate: '2013-05-29',
    contractPrice: 900000,
    contractCount: 14,
  },
  // 다른 항목들 추가
];

const cols = [
  { key: 'order', label: 'No.' },
  { key: 'memberName', label: '회원이름' },
  { key: 'memberPhone', label: '휴대전화' },
  { key: 'memberEmail', label: '이메일' },
  { key: 'memberEnrollDate', label: '회원등록일' },
  { key: 'contractPrice', label: '계약금액합' },
  { key: 'contractCount', label: '계약건수' },
];

// Type : hidden, text, num, calendar, select
const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '' },
  { key: 'order', type: 'hidden', value: '' },
  { key: 'memberName', type: 'text', value: '' },
  { key: 'memberPhone', type: 'text', value: '' },
  { key: 'memberEmail', type: 'text', value: '' },
  { key: 'memberEnrollDate', type: 'calendar', value: '' },
  { key: 'contractPrice', type: 'num', value: '' },
  { key: 'contractCount', type: 'num', value: '' },
];

const MemberListPage = () => {
  const [search, setSearch] = useState(initialSearch);

  const [totalPages, setTotalPages] = useState(1); // 전체 페이지 수
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [pageGroup, setPageGroup] = useState(0); // 현재 페이지 그룹
  const buttonCount = 5; // 버튼 갯수

  const navigate = useNavigate();

  // SortSelect를 위한 값들
  const [selectedOption, setSelectedOption] = useState('');
  const Options = [
    { value: '계약금액 많은순', label: '계약금액 많은순' },
    { value: '계약금액 적은순', label: '계약금액 적은순' },
    { value: '계약 많은순', label: '계약 많은순' },
    { value: '계약 적은순', label: '계약 적은순' },
  ];

  const handleSearchChange = (key, value) => {
    setSearch(prev =>
      prev.map(searchItem =>
        searchItem.key === key ? { ...searchItem, value: value } : searchItem
      )
    );
  };

  const handleGoRegister = () => {
    navigate('register');
  };

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
            selectedOption={selectedOption}
            setSelectedOption={setSelectedOption}
            options={Options}
          />
        </div>

        <div>
          <div className='flex'>
            <MoveButton
              imgSrc={registerManyUser}
              buttonText='대량 회원 등록'
              onClick={handleGoRegister}
            />
            <MoveButton imgSrc={registerUser} buttonText='회원 등록' onClick={handleGoRegister} />
          </div>
        </div>
      </div>
      <Table
        cols={cols}
        rows={rows}
        search={search}
        currentPage={currentPage}
        handleSearchChange={handleSearchChange}
      />

      <PagiNation
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPages={totalPages}
        pageGroup={pageGroup}
        setPageGroup={setPageGroup}
        buttonCount={buttonCount}
      />
    </div>
  );
};

export default MemberListPage;
