import { privateAxios } from '.';

// 상품 등록
export const createProduct = async productData => {
  try {
    const res = await privateAxios.post('/v1/vendor/product', productData);
    return res;
  } catch (err) {
    console.error('상품 등록 실패 => ', err.response.data);
    throw err;
  }
};

// 상품 목록 조회
export const getProductList = async (searchParams = {}) => {
  try {
    const res = await privateAxios.get('/v1/vendor/product', {
      params: {
        ...searchParams,
      },
    });
    return res;
  } catch (err) {
    console.error('상품 목록 조회 실패 => ', err.response.data);
    throw err;
  }
};

// 상품 상세 조회
export const getProductDetail = async productId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/product/${productId}`);
    return res;
  } catch (err) {
    console.error('상품 상세 조회 실패 => ', err.response.data);
    throw err;
  }
};

// 상품 수정
export const updateProduct = async (productId, productData) => {
  try {
    const res = await privateAxios.put(`/v1/vendor/product/${productId}`, productData);
    return res;
  } catch (err) {
    console.error('상품 수정 실패 => ', err.response.data);
    throw err;
  }
};

// 상품 삭제
export const deleteProduct = async productId => {
  try {
    const res = await privateAxios.delete(`/v1/vendor/product/${productId}`);
    return res;
  } catch (err) {
    console.error('상품 삭제 실패 => ', err.response.data);
    throw err;
  }
};
