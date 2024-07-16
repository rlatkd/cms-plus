import { getProductDetail, getProductList } from '@/apis/product';
import MoveButton from '@/components/common/buttons/MoveButton';
import PagiNation from '@/components/common/PagiNation';
import SortSelect from '@/components/common/selects/SortSelect';
import Table from '@/components/common/tables/Table';
import ProductModal from '@/components/vendor/modal/ProductModal';
import { validateField } from '@/utils/validators';
import { useCallback, useEffect, useState } from 'react';

const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'productName', label: '상품명', width: 'w-3/12' },
  { key: 'productPrice', label: '금액', width: 'w-3/12' },
  { key: 'contractNumber', label: '계약수', width: 'w-3/12' },
  { key: 'productCreatedDate', label: '생성일', width: 'w-3/12' },
  { key: 'productMemo', label: '비고', width: 'w-3/12' },
];

// Type : hidden, text, num, calendar, select
const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'productName', type: 'text', value: '', width: 'w-3/12' },
  { key: 'productPrice', type: 'num', value: '', width: 'w-3/12' },
  { key: 'contractNumber', type: 'num', value: '', width: 'w-3/12' },
  { key: 'productCreatedDate', type: 'calendar', value: '', width: 'w-3/12' },
  { key: 'productMemo', type: 'text', value: '', width: 'w-3/12' },
];

// // SortSelect를 위한 값들
// const [selectedOption, setSelectedOption] = useState('');
// const Options = [
//   { value: 'option1', label: 'Option 1' },
//   { value: 'option2', label: 'Option 2' },
//   { value: 'option3', label: 'Option 3' },
// ];

const ProductListPage = () => {
  const [isShowModal, setIsShowModal] = useState(false); // 모달 on,off
  const [modalTitle, setModalTitle] = useState(''); // 모달 제목
  const [productList, setProductList] = useState([]); // 상품 목록
  const [productDetailData, setProductDetailData] = useState(null); // 상품 상세 정보
  const [search, setSearch] = useState(initialSearch); // 상품 조건
  const [currentSearchParams, setCurrentSearchParams] = useState({}); // 현재 검색 조건
  const [isValid, setIsValid] = useState(true); // 유효성 flag

  const [totalPages, setTotalPages] = useState(1); // 전체 페이지 수
  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [pageGroup, setPageGroup] = useState(0); // 현재 페이지 그룹
  const buttonCount = 5;

  // 상품 목록 조회 함수
  // axiosProductLists는 useEffect 훅 내부에서 사용하고 있기 때문에
  // 종속성 배열에 포함시키고 useCallback으로 함수 재생성 방지
  // 의도하지 않은 렌더링 에러 방지
  const axiosProductList = useCallback(
    async (searchParams = {}, page = currentPage) => {
      try {
        const res = await getProductList({ size: 10, page: page, ...searchParams });
        setProductList(res.data.content);
        setTotalPages(res.data.totalPage || 1);
      } catch (err) {
        console.error('axiosProductList => ', err.response.data);
      }
    },
    [currentPage]
  );

  // 페이지 진입 시 상품 목록 조회
  useEffect(() => {
    axiosProductList(currentSearchParams, currentPage);
  }, [currentPage, currentSearchParams, axiosProductList]);

  // 상품 등록 모달 열기용 이벤트핸들러
  const handleCreateModalOpen = () => {
    setModalTitle('상품 등록');
    setIsShowModal(true);
  };

  // 상품 상세조회 모달 열기용 이벤트핸들러
  const handleDetailModalOpen = async productId => {
    setModalTitle('상품 상세 정보');
    try {
      const res = await getProductDetail(productId);
      setProductDetailData(res.data);
      setIsShowModal(true);
    } catch (err) {
      console.error('axiosProductDetail => ', err.response.data);
    }
  };

  // 조건 변경 핸들러
  const handleChangeSearch = (key, value) => {
    console.log(key, value);
    setSearch(prev =>
      prev.map(searchProduct =>
        searchProduct.key === key ? { ...searchProduct, value: value } : searchProduct
      )
    );
  };

  // 검색 안에 입력값 유효성 검사
  const validateSearchParams = () => {
    for (const searchProduct of search) {
      if (searchProduct.value) {
        if (!validateField(searchProduct.key, searchProduct.value)) {
          alert(`[${searchProduct.key}]은(는) 숫자만 입력할 수 있습니다.`);
          setIsValid(false);
          return false;
        }
      }
    }
    setIsValid(true);
    return true;
  };

  // 검색 클릭 이벤트 핸들러
  const handlehClickSearch = async () => {
    if (!validateSearchParams()) return;
    const searchParams = { size: 10 };
    search.forEach(searchProduct => {
      if (searchProduct.value) {
        searchParams[searchProduct.key] = searchProduct.value;
      }
    });

    setCurrentSearchParams(searchParams);
    setCurrentPage(1); // 검색 후 현재 페이지 초기화
    setPageGroup(0); // 검색 후 페이지 그룹 초기화
    setIsValid(true); // 검색 후 유효성 flag 초기화
  };

  return (
    <div className='primary-dashboard flex flex-col h-1500 desktop:h-full '>
      <div className='flex justify-between pt-2 pb-4 w-full'>
        <div className='flex items-center '>
          <img
            className='bg-mint h-7 w-7 p-1 rounded-md ml-1 mr-3'
            src='/src/assets/item.svg'
            alt='user'
          />
          <p className='text-text_black font-700 mr-5'>총 24건</p>
          {/* <SortSelect
            selectedOption={selectedOption}
            setSelectedOption={setSelectedOption}
            options={Options}
          /> */}
        </div>

        <div>
          <div className='flex'>
            <MoveButton
              imgSrc='/src/assets/addItem.svg'
              buttonText='상품 등록'
              onClick={handleCreateModalOpen}
            />
          </div>
        </div>
      </div>

      <Table
        cols={cols}
        search={search}
        rows={productList}
        currentPage={currentPage}
        handleChangeSearch={handleChangeSearch}
        onRowClick={item => handleDetailModalOpen(item.productId)}
        handlehClickSearch={handlehClickSearch}
      />

      {/* 페이지네이션*/}
      <PagiNation
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPages={totalPages}
        pageGroup={pageGroup}
        setPageGroup={setPageGroup}
        buttonCount={buttonCount}
      />

      <ProductModal
        isShowModal={isShowModal}
        setIsShowModal={setIsShowModal}
        modalTitle={modalTitle}
        icon='/src/assets/item.svg'
        productDetailData={productDetailData}
        refreshProductList={() => axiosProductList(currentSearchParams, currentPage)} // 모달에서 상품 등록 완료되면 추가된거 포함해서 상품 목록 다시 렌더링
      />
    </div>
  );
};

export default ProductListPage;
