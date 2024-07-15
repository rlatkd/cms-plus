import { getProductDetail, getProductList } from '@/apis/product';
import PagiNation from '@/components/common/PagiNation';
import Table from '@/components/common/tables/Table';
import ProductModal from '@/components/vendor/modal/ProductModal';
import { validateField } from '@/utils/validators';
import { useCallback, useEffect, useState } from 'react';

// 상품 목록 조회 컬럼
const cols = ['No', '상품명', '금액', '계약수', '생성일', '비고'];

// 상품 조건 설정
const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '' },
  { key: 'No', type: 'hidden', value: '' },
  { key: '상품명', type: 'text', value: '' },
  { key: '금액', type: 'text', value: '' },
  {
    key: '계약수',
    type: 'text',
    value: '',
  },
  { key: '생성일', type: 'text', value: '' },
  { key: '비고', type: 'text', value: '' },
];

const ProductListPage = () => {
  const [isShowModal, setIsShowModal] = useState(false); // 모달 on,off
  const [modalTitle, setModalTitle] = useState(''); // 모달 제목
  const [productList, setProductList] = useState([]); // 상품 목록
  const [productDetailData, setProductDetailData] = useState(null); // 상품 상세 정보
  const [search, setSearch] = useState(initialSearch); // 상품 조건
  const [currentSearchParams, setCurrentSearchParams] = useState({}); // 현재 검색 조건
  const [isValid, setIsValid] = useState(true); // 유효성 flag

  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
  const [pageGroup, setPageGroup] = useState(0); // 현재 페이지 그룹
  const [page, setPage] = useState(1); // 현재 표시할 페이지 번호 - 페이지 라우팅되는 변수와 페이징을 통해 api parameter에 영향을 주는 변수 분리
  const [totalPages, setTotalPages] = useState(1); // 전체 페이지 수

  // 상품 목록 조회 함수
  // axiosProductLists는 useEffect 훅 내부에서 사용하고 있기 때문에
  // 종속성 배열에 포함시키고 useCallback으로 함수 재생성 방지
  // 의도하지 않은 렌더링 에러 방지
  const axiosProductList = useCallback(
    async (searchParams = {}, page = currentPage) => {
      try {
        const res = await getProductList({ size: 9, page: page, ...searchParams });
        const formattedData = res.data.content.map((data, index) => ({
          No: (page - 1) * 9 + index + 1,
          상품명: data.productName,
          금액: data.productPrice,
          계약수: data.contractNumber,
          생성일: data.productCreatedDate,
          비고: data.productMemo || '',
          productId: data.productId,
        }));
        setProductList(formattedData);
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
  const handleSearchChange = (key, value) => {
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
        const keyMapping = {
          상품명: 'productName',
          금액: 'productPrice',
          계약수: 'contractNumber',
          생성일: 'productCreatedDate',
          비고: 'productMemo',
        };
        const paramKey = keyMapping[searchProduct.key];
        if (paramKey) {
          if (!validateField(paramKey, searchProduct.value)) {
            alert(`[${searchProduct.key}]은(는) 숫자만 입력할 수 있습니다.`);
            setIsValid(false);
            return false;
          }
        }
      }
    }
    setIsValid(true);
    return true;
  };

  // 검색 클릭 이벤트 핸들러
  const handleSearchClick = async () => {
    if (!validateSearchParams()) return;
    const searchParams = { size: 9 };
    search.forEach(searchProduct => {
      if (searchProduct.value) {
        const keyMapping = {
          상품명: 'productName',
          금액: 'productPrice',
          계약수: 'contractNumber',
          생성일: 'productCreatedDate',
          비고: 'productMemo',
        };
        const paramKey = keyMapping[searchProduct.key];
        if (paramKey) {
          searchParams[paramKey] = searchProduct.value;
        }
      }
    });
    setCurrentSearchParams(searchParams);
    setCurrentPage(1); // 검색 후 현재 페이지 초기화
    setPage(1); // 검색 후 표시할 페이지 초기화
    setPageGroup(0); // 검색 후 페이지 그룹 초기화
    setIsValid(true); // 검색 후 유효성 flag 초기화
  };

  return (
    <div className='primary-dashboard h-full w-full flex flex-col overflow-hidden'>
      <div className='flex justify-end'>
        <button
          className='rounded-lg bg-mint hover:bg-mint_hover p-3 font-bold text-white w-24'
          onClick={handleCreateModalOpen}>
          상품 등록
        </button>
      </div>

      <div className='flex-grow  mt-5'>
        <Table
          cols={cols}
          search={search}
          items={productList}
          handleSearchChange={handleSearchChange}
          onRowClick={item => handleDetailModalOpen(item.productId)}
          onSearchClick={handleSearchClick}
        />
      </div>

      {/* 페이지네이션*/}
      <div className='flex justify-center mt-5'>
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

      <ProductModal
        isShowModal={isShowModal}
        setIsShowModal={setIsShowModal}
        modalTitle={modalTitle}
        productDetailData={productDetailData}
        refreshProductList={() => axiosProductList(currentSearchParams, currentPage)} // 모달에서 상품 등록 완료되면 추가된거 포함해서 상품 목록 다시 렌더링
      />
    </div>
  );
};

export default ProductListPage;
