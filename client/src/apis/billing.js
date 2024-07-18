import { privateAxios } from '.';

// 청구 목록 조회
export const getBillingList = async (searchParams = {}) => {
  try {
    console.log(searchParams);
    const res = await privateAxios.get('/v1/vendor/billing', {
      params: {
        ...searchParams,
      },
    });
    return res;
  } catch (err) {
    console.log('청구 목록 조회 실패 => ', err.response);
    throw err;
  }
};

// 청구 상세 조회
export const getBillingDetail = async (billingId) => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/${billingId}`);
    return res;
  } catch (err) {
    console.log('청구 상세 조회 실패 => ', err.response);
    throw err;
  }
};