import { getBillingList } from '@/apis/billing';
import MoveButton from '@/components/common/buttons/MoveButton';
import PagiNation from '@/components/common/PagiNation';
import SortSelect from '@/components/common/selects/SortSelect';
import Table from '@/components/common/tables/Table';
import BillingRegisterModal from '@/components/vendor/modal/BillingRegisterModal';
import { useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import addItem from '@/assets/addItem.svg';
import card from '@/assets/card.svg';
import send from '@/assets/send.svg';
import Card from '@/assets/Card';

const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'memberName', label: '회원이름', width: 'w-2/12' },
  { key: 'memberPhone', label: '휴대전화', width: 'w-2/12' },
  { key: 'billingProducts', label: '상품', width: 'w-2/12' },
  { key: 'billingPrice', label: '청구금액', width: 'w-2/12' },
  { key: 'billingDate', label: '결제일(약정일)', width: 'w-2/12' },
  { key: 'paymentType', label: '계약방식', width: 'w-2/12' },
  { key: 'billingStatus', label: '청구상태', width: 'w-2/12' },
];

// Type : hidden, text, num, calendar, select
const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'memberName', type: 'text', value: '', width: 'w-2/12' },
  { key: 'memberPhone', type: 'text', value: '', width: 'w-2/12' },
  { key: 'billingProducts', type: 'text', value: '', width: 'w-2/12' },
  { key: 'billingPrice', type: 'num', value: '', width: 'w-2/12' },
  { key: 'billingDate', type: 'calendar', value: '', width: 'w-2/12' },
  {
    key: 'paymentType',
    type: 'select',
    value: '',
    width: 'w-2/12',
    options: ['자동결제', '납부자결제', '가상계좌'],
  },
  {
    key: 'billingStatus',
    type: 'select',
    value: '',
    width: 'w-2/12',
    options: ['청구생성', '수납대기', '완납', '미납'],
  },
];

const selectOptions = [
  { label: '계약금액 많은순', orderBy: 'contractPrice', order: 'DESC' },
  { label: '계약금액 적은순', orderBy: 'contractPrice', order: 'ASC' },
];

const BillingListPage = () => {
  const [billingList, setBillingList] = useState([]); // 청구 목록
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

  // 청구 목록 조회
  const axiosBillingList = useCallback(
    async (searchParams = {}, page = currentPage) => {
      try {
        const res = await getBillingList({
          size: 10,
          page: page,
          ...searchParams,
        });
        const transformedData = transformBillingListItem(res.data.content);
        setBillingList(transformedData);
        setTotalPages(res.data.totalPage || 1);
      } catch (err) {
        console.error('axiosMemberList => ', err.response.data);
      }
    },
    [currentPage]
  );

  // 청구 데이터 값 정제
  const transformBillingListItem = data => {
    // 데이터 변환
    return data.map(billing => {
      const { billingPrice, billingProducts, paymentType, billingStatus } = billing;
      const firstProduct = billingProducts[0];
      const additionalProductsCount = billingProducts.length - 1;

      return {
        ...billing,
        billingPrice: `${billingPrice}원`,
        billingProducts: `${firstProduct.name} + ${additionalProductsCount}`,
        paymentType: paymentType.title,
        billingStatus: billingStatus.title,
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
    axiosBillingList(currentSearchParams);
    setCurrentPage(1); // 검색 후 현재 페이지 초기화
    setPageGroup(0); // 검색 후 페이지 그룹 초기화
  };

  // 청구 상세 조회 페이지 이동
  const MoveBillingDetail = async billingId => {
    console.log(billingId);
    navigate(`detail/${billingId}`);
  };

  useEffect(() => {
    axiosBillingList(currentPage);
  }, [currentPage]);

  return (
    <div className='primary-dashboard flex flex-col h-1500 desktop:h-full'>
      <div className='flex justify-between pt-2 pb-4 w-full'>
        <div className='flex items-center'>
          <div className='bg-mint h-7 w-7 rounded-md ml-1 mr-3 flex items-center justify-center'>
            <Card fill='#ffffff' />
          </div>
          <p className='text-text_black font-700 mr-5'>총 24건</p>
          <SortSelect
            setOrder={setOrder}
            setOrderBy={setOrderBy}
            selectOptions={selectOptions}
            currentSearchParams={currentSearchParams}
            axiosList={axiosBillingList}
          />
        </div>

        <div>
          <div className='flex'>
            <MoveButton imgSrc={card} color='white' buttonText='실시간 결제' />
            <MoveButton imgSrc={send} color='mint' buttonText='청구서 발송' />
            <MoveButton
              imgSrc={addItem}
              color='mint'
              buttonText='청구생성'
              onClick={() => setIsShowModal(true)}
            />
          </div>
        </div>
      </div>

      <Table
        cols={cols}
        rows={billingList}
        search={search}
        currentPage={currentPage}
        handleChangeSearch={handleChangeSearch}
        handlehClickSearch={handlehClickSearch}
        onRowClick={item => MoveBillingDetail(item.contractId)}
      />

      <PagiNation
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPages={totalPages}
        pageGroup={pageGroup}
        setPageGroup={setPageGroup}
        buttonCount={buttonCount}
      />

      <BillingRegisterModal
        isShowModal={isShowModal}
        icon={card}
        setIsShowModal={setIsShowModal}
        modalTitle={'청구생성'}
      />
    </div>
  );
};

export default BillingListPage;
