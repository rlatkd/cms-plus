import { privateAxios } from '.';

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
  const res = await privateAxios.get('/v1/vendor/product/all');
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
