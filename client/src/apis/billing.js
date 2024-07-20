import { privateAxios } from '.';

// 청구 목록 조회
export const getBillingList = async (searchParams = {}) => {
  try {
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
export const getBillingDetail = async billingId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/${billingId}`);
    return res;
  } catch (err) {
    console.log('청구 상세 조회 실패 => ', err.response);
    throw err;
  }
};

// 회원 수정 - 청구 정보
export const updateMemberBilling = async (contractId, billingData) => {
  try {
    const res = await privateAxios.put(
      `/v1/vendor/management/members/billing/${contractId}`,
      billingData
    );
    return res;
  } catch (err) {
    console.error('회원 수정 - 청구 정보 => ', err.response.data);
    throw err;
  }
};
