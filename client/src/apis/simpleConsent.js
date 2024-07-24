import { privateAxios } from '.';
import { getProductList } from './product';

export const getSimpleConsent = async () => {
  try {
    const res = await privateAxios.get('/v1/vendor/simple-consent');
    return res.data;
  } catch (err) {
    console.error('간편서명동의 데이터 가져오기 실패:', err);
    throw err;
  }
};

export const getAllProducts = async () => {
  try {
    let allProducts = [];
    let currentPage = 1;
    let hasMore = true;

    while (hasMore) {
      const res = await getProductList({
        page: currentPage,
        size: 100,
      });

      const { content, totalPage } = res.data;
      allProducts = [...allProducts, ...content];

      if (currentPage >= totalPage) {
        hasMore = false;
      } else {
        currentPage++;
      }
    }

    return allProducts;
  } catch (err) {
    console.err('모든 상품 가져오기 실패:', err);
    throw err;
  }
};

export const updateSimpleConsent = async data => {
  try {
    const res = await privateAxios.put('/v1/vendor/simple-consent', data);
    return res.data;
  } catch (err) {
    console.error('간편서명동의 설정 업데이트 실패:', err);
    throw err;
  }
};

export const getAvailableOptions = async () => {
  try {
    const res = await privateAxios.get('/v1/vendor/simple-consent/available-options');
    return res.data;
  } catch (err) {
    console.error('간편서명동의 가능한 옵션 가져오기 실패:', err);
    throw err;
  }
};
