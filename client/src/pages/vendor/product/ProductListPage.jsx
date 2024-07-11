import { getProductDetail, getProductList } from '@/apis/product';
import Table from '@/components/common/Table';
import ProductModal from '@/components/vendor/modal/ProductModal';
import { useEffect, useState } from 'react';

const cols = ['No', '상품명', '금액', '계약수', '생성시기', '비고'];

const items = [
  {
    No: '1',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '2',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
  {
    No: '3',
    상품명: '18.2.0',
    금액: '2013-05-29',
    계약수: '2013-05-29',
    생성시기: '2013-05-29',
    비고: '2013-05-29',
  },
];

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
  { key: '생성시기', type: 'text', value: '' },
  { key: '비고', type: 'number', value: '' },
];

// 생성시기 상세 조회용 더미데이터(생성시기 목록 조회에서 가져온 데이터라 가정)
const dummyData = {
  page: 1,
  offset: 10,
  totalPage: 100,
  totalCount: 1000,
  data: [
    {
      id: 1,
      vendorId: 1001,
      name: '생성시기 조회용 데이터1',
      price: 10.0,
      contractCount: 10,
      createdDateTime: '2024-01-01 11:11:11',
      updatedDateTime: 'Null',
      deletedDate: 'Null',
      memo: '비고 1',
      status: 'STATUS1',
    },
    {
      id: 2,
      vendorId: 1002,
      name: '생성시기 조회용 데이터2',
      price: 20.0,
      contractCount: 22,
      createdDateTime: '2024-01-02 11:11:11',
      updatedDateTime: '2024-01-01 11:11:11',
      deletedDate: '2024-01-01',
      memo: '비고 2',
      status: 'STATUS2',
    },
  ],
};

const ProductListPage = () => {
  const [isShowModal, setIsShowModal] = useState(false); // 모달 on,off
  const [modalTitle, setModalTitle] = useState(''); // 모달 제목 상태
  const [productId, setProductId] = useState(''); // 생성시기ID
  const [productList, setProductList] = useState([]); // 생성시기 목록
  const [productDetailData, setProductDetailData] = useState(null); // 생성시기 상세 정보
  const [pageNum, setPageNum] = useState(''); // 페이지번호
  const [size, setSize] = useState(''); // 페이지 사이즈

  // 컴포넌트 마운트시 더미데이터 세팅
  useEffect(() => {
    setProductId(dummyData.data[0].id);
  }, []);

  // useEffect(() => {
  //   const fetchProductList = async () => {
  //     try {
  //       const res = await getProductList();
  //       setProductList(res.data.data);
  //       console.log('생성시기 목록 조회 성공', res.data.data);
  //     } catch (err) {
  //       console.error('생성시기 목록 조회 실패 =>', err.response?.data || err.message);
  //     }
  //   };

  //   fetchProductList();
  // }, []);

  // 생성시기 등록 모달 열기용 이벤트핸들러
  const handleOpenRegisterModal = () => {
    setModalTitle('상품 등록');
    setIsShowModal(true);
  };

  // 생성시기 상세조회 모달 열기용 이벤트핸들러
  const handleOpenDetailModal = async () => {
    setModalTitle('상품 상세 정보');
    try {
      const res = await getProductDetail(productId);
      setProductDetailData(res.data);
      setIsShowModal(true);
    } catch (err) {
      console.error('axiosProductDetail => ', err.response.data);
    }
  };

  const [search, setSearch] = useState(initialSearch);

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

  return (
    <div className='primary-dashboard h-full w-full'>
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white mr-10'
        onClick={handleOpenRegisterModal}>
        임시 상품 등록 모달
      </button>

      {/* 이건 목록에서 생성시기 하나 클릭하면 들어가도록 */}
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white mr-10'
        onClick={handleOpenDetailModal}>
        임시 상품 상세 모달
      </button>

      <Table
        cols={cols}
        search={search}
        items={items}
        handleSearchChange={handleSearchChange}
        show={show}
      />

      <ProductModal
        isShowModal={isShowModal}
        setIsShowModal={setIsShowModal}
        modalTitle={modalTitle}
        productDetailData={productDetailData}
      />
    </div>
  );
};

export default ProductListPage;
