import MoveButton from '@/components/common/buttons/MoveButton';
import PagiNation from '@/components/common/PagiNation';
import SortSelect from '@/components/common/selects/SortSelect';
import Table from '@/components/common/tables/Table';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
const cols = [
  'No',
  '회원이름',
  '휴대전화',
  '약정일',
  '상품',
  '계약금액',
  '계약상태',
  '간편동의여부',
];

const items = [
  {
    No: '1',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '2',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
  {
    No: '3',
    회원이름: '18.2.0',
    휴대전화: '2013-05-29',
    약정일: '2013-05-29',
    상품: '2013-05-29',
    계약금액: '2013-05-29',
    계약상태: '2013-05-29',
    간편동의여부: '2013-05-29',
  },
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
    key: '간편동의여부',
    type: 'select',
    value: '',
    options: ['완료', '대기중'],
  },
];

const MemberListPage = () => {
  const [search, setSearch] = useState(initialSearch);
  const [totalPages, setTotalPages] = useState(1); // 전체 페이지 수
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [pageGroup, setPageGroup] = useState(0); // 현재 페이지 그룹
  const [page, setPage] = useState(1); // 현재 표시할 페이지 번호 - 페이지 라우팅되는 변수와 페이징을 통해 api parameter에 영향을 주는 변수 분리
  const navigate = useNavigate();

  // SortSelect를 위한 값들
  const [selectedOption, setSelectedOption] = useState('');
  const Options = [
    { value: 'option1', label: 'Option 1' },
    { value: 'option2', label: 'Option 2' },
    { value: 'option3', label: 'Option 3' },
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
              imgSrc='/src/assets/registerManyUser.svg'
              buttonText='대량 회원 등록'
              onClick={handleGoRegister}
            />
            <MoveButton
              imgSrc='/src/assets/registerUser.svg'
              buttonText='회원 등록'
              onClick={handleGoRegister}
            />
          </div>
        </div>
      </div>
      <Table cols={cols} search={search} items={items} handleSearchChange={handleSearchChange} />

      {/* 페이지네이션*/}
      <div className='flex justify-center h-full items-end'>
        <PagiNation
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalPages={totalPages}
          pageGroup={pageGroup}
          setPageGroup={setPageGroup}
          page={page}
          setPage={setPage}
        />
      </div>
    </div>
  );
};

export default MemberListPage;
