import { publicAxios } from '.';

// 상품 등록
export const createProduct = async productData => {
  try {
    return await publicAxios.post('/v1/vendor/product', productData);
  } catch (err) {
    console.error('상품 등록 실패 => ', err.response.data);
    throw err;
  }
};

// 상품 목록 조회
export const getProductList = async (searchParams = {}) => {
  try {
    return await publicAxios.get('/v1/vendor/product', {
      params: {
        ...searchParams,
      },
    });
  } catch (err) {
    console.error('상품 목록 조회 실패 => ', err.response.data);
    throw err;
  }
};

// 상품 상세 조회
export const getProductDetail = async productId => {
  try {
    return await publicAxios.get(`/v1/vendor/product/${productId}`);
  } catch (err) {
    console.error('상품 상세 조회 실패 => ', err.response.data);
    throw err;
  }
};
