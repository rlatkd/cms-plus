import { getProductDetail, getProductList } from '@/apis/product';
import ProductModal from '@/components/vendor/modal/ProductModal';
import { useEffect, useState } from 'react';

const ProductListPage = () => {
  const [isShowModal, setIsShowModal] = useState(false);
  const [modalTitle, setModalTitle] = useState(''); //모달 제목 상태
  const [productId, setProductId] = useState('');
  const [productList, setProductList] = useState([]); // 상품 목록
  const [productDetailData, setProductDetailData] = useState(null); // 상품 상세 정보

  const dummyData = {
    page: 1,
    offset: 10,
    totalPage: 100,
    totalCount: 1000,
    data: [
      {
        id: 1,
        vendorId: 1001,
        name: '상품 조회용 데이터1',
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
        name: '상품 조회용 데이터2',
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

  useEffect(() => {
    setProductId(dummyData.data[0].id);
  }, []);

  // useEffect(() => {
  //   const fetchProductList = async () => {
  //     try {
  //       const res = await getProductList();
  //       setProductList(res.data.data);
  //       console.log('상품 목록 조회 성공', res.data.data);
  //     } catch (err) {
  //       console.error('상품 목록 조회 실패 =>', err.response?.data || err.message);
  //     }
  //   };

  //   fetchProductList();
  // }, []);

  // 상품 등록 모달 열기용 이벤트핸들러
  const openRegisterModalHandle = () => {
    console.log('클릭');
    setModalTitle('상품 등록');
    setIsShowModal(true);
  };

  console.log(isShowModal);

  // 상품 상세조회 모달 열기용 이벤트핸들러
  const openDetailModalHandle = async () => {
    setModalTitle('상품 상세 정보');
    // console.log('[1] ', productId);
    try {
      const res = await getProductDetail(productId);
      setProductDetailData(res.data);
      // console.log('상품 상세 조회 성공', res.data);
      setIsShowModal(true);
    } catch (err) {
      console.error('axiosProductDetail => ', err.response.data);
    }
  };

  return (
    <div className='primary-dashboard h-full w-full'>
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white mr-10'
        onClick={openRegisterModalHandle}>
        임시 상품 등록 모달
      </button>

      {/* 이건 목록에서 상품 하나 클릭하면 들어가도록 */}
      <button
        className='rounded-lg bg-mint p-3 font-bold text-white'
        onClick={openDetailModalHandle}>
        임시 상품 상세 모달
      </button>
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
