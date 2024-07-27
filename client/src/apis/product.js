import { privateAxios, testAxios } from '.';

// 상품 등록
export const createProduct = async productData => {
  const res = await privateAxios.post('/v1/vendor/product', productData);
  return res;
};

// 상품 목록 조회
export const getProductList = async (searchParams = {}) => {
  const res = await privateAxios.get('/v1/vendor/product', {
    params: {
      ...searchParams,
    },
  });
  return res;
};

// 전체 상품 목록 조회 (조건 없이, 이름, 가격, id만)
export const getAllProductList = async () => {
  const res = await privateAxios.get('/v1/vendor/product/all/no-cond');
  return res;
};

// 상품 상세 조회
export const getProductDetail = async productId => {
  const res = await privateAxios.get(`/v1/vendor/product/${productId}`);
  return res;
};

// 상품 수정
export const updateProduct = async (productId, productData) => {
  const res = await privateAxios.put(`/v1/vendor/product/${productId}`, productData);
  return res;
};

// 상품 삭제
export const deleteProduct = async productId => {
  const res = await privateAxios.delete(`/v1/vendor/product/${productId}`);
  return res;
};

// 상품 목록 조회 임시
export const getProductListTmp = async () => {
  try {
    const res = await privateAxios.get('/v1/vendor/product/tmp');
    return res;
  } catch (err) {
    console.error(' 상품 목록 조회 임시 => ', err.response.data);
  }
};

export const test = async testData => {
  try {
    const res = await testAxios.post('v1/kafkatest/payment', testData, { validateStatus: false });
    if (res.status !== 202) {
      console.error('Unexpected response status:', res.status);
    }
  } catch (err) {
    console.error('테스트 실패 => ', err.response.data);
    throw err;
  }
};
