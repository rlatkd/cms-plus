import { privateAxios } from '.';

// 청구 생성
export const createBilling = async billingData => {
  try {
    const res = await privateAxios.post('/v1/vendor/billing', billingData);
    return res;
  } catch (err) {
    console.error('청구 생성 실패 => ', err.response.data);
    throw err;
  }
};

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
    console.error('청구 목록 조회 실패 => ', err.response);
    throw err;
  }
};

// 청구 상세 조회
export const getBillingDetail = async billingId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/${billingId}`);
    return res;
  } catch (err) {
    console.error('청구 상세 조회 실패 => ', err.response);
    throw err;
  }
};

// 청구 상세 조회
export const getBillingProducts = async billingId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/products/${billingId}`);
    return res;
  } catch (err) {
    console.error('청구상품 조회 실패 => ', err.response);
    throw err;
  }
};

// 청구 수정
export const updateBilling = async (billingId, billingReq) => {
  try {
    const res = await privateAxios.put(`/v1/vendor/billing/${billingId}`, billingReq);
    return res;
  } catch (err) {
    console.error('청구 수정 실패 => ', err.response);
    throw err;
  }
};

// 청구 삭제
export const deleteBilling = async billingId => {
  try {
    const res = await privateAxios.delete(`/v1/vendor/billing/${billingId}`);
    return res;
  } catch (err) {
    console.error('청구 삭제 실패 => ', err.response.data);
    throw err;
  }
};

// 청구서 발송
export const sendInvoice = async billingId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/invoice/${billingId}`);
    return res;
  } catch (err) {
    console.error('청구서 발송 실패 => ', err.response.data);
    throw err;
  }
};

// 청구서 발송취소
export const cancelSendInvoice = async billingId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/invoice/cancel/${billingId}`);
    return res;
  } catch (err) {
    console.error('청구서 발송 취소 실패 => ', err.response.data);
    throw err;
  }
};

// 청구 실시간 결제
export const payBilling = async billingId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/payment/${billingId}`);
    return res;
  } catch (err) {
    console.error('청구서 실시간 결제 실패 => ', err.response.data);
    throw err;
  }
};

// 청구 실시간 결제
export const cancelPayBilling = async billingId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/billing/payment/${billingId}/cancel`);
    return res;
  } catch (err) {
    console.error('청구서 실시간 결제취소 실패 => ', err.response.data);
    throw err;
  }
};

// 회원 수정 - 청구 정보
export const updateMemberBilling = async (memberId, billingData) => {
  try {
    const res = await privateAxios.put(
      `/v1/vendor/management/members/billing/${memberId}`,
      billingData
    );
    return res;
  } catch (err) {
    console.error('회원 수정 - 청구 정보 => ', err.response.data);
    throw err;
  }
};
