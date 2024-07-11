import { publicAxios } from '.';

// 상품 리스트 조회
export const getProductList = async () => {
  try {
    return await publicAxios.get('/v1/vendor/product', {
      params: {
        // 테스트 더미 쿼리파라미터
        page: 1,
        size: 1,
      },
    });
  } catch (err) {
    console.error('상품 목록 조회 실패 =>', err.response?.data || err.message);
    throw err;
  }
};

// 상품 상세 조회
export const getProductDetail = async productId => {
  try {
    // console.log('[2] ', productId);
    return await publicAxios.get(`/v1/vendor/product/${productId}`);
  } catch (err) {
    console.error('상품 상세 조회 실패 =>', err.response.data);
    throw err;
  }
};
