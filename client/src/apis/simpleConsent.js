import { privateAxios } from '.';
import { getProductList } from './product';

export const getSimpleConsent = async () => {
  try {
    const response = await privateAxios.get('/v1/vendor/simple-consent');
    return response.data;
  } catch (error) {
    console.error('간편서명동의 데이터 가져오기 실패:', error);
    throw error;
  }
};

export const getAllProducts = async () => {
  try {
    let allProducts = [];
    let currentPage = 1;
    let hasMore = true;

    while (hasMore) {
      const response = await getProductList({
        page: currentPage,
        size: 100,
      });

      const { content, totalPage } = response.data;
      allProducts = [...allProducts, ...content];

      if (currentPage >= totalPage) {
        hasMore = false;
      } else {
        currentPage++;
      }
    }

    return allProducts;
  } catch (error) {
    console.error('모든 상품 가져오기 실패:', error);
    throw error;
  }
};

export const updateSimpleConsent = async data => {
  try {
    const response = await privateAxios.put('/v1/vendor/simple-consent', data);
    return response.data;
  } catch (error) {
    console.error('간편서명동의 설정 업데이트 실패:', error);
    throw error;
  }
};

export const getAvailableOptions = async () => {
  try {
    const response = await privateAxios.get('/v1/vendor/simple-consent/available-options');
    return response.data;
  } catch (error) {
    console.error('간편서명동의 가능한 옵션 가져오기 실패:', error);
    throw error;
  }
};
