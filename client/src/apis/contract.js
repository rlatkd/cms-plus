import { privateAxios } from '.';

// 계약 목록 조회
export const getContractList = async (searchParams = {}) => {
  try {
    const res = await privateAxios.get('/v1/vendor/contract', {
      params: {
        ...searchParams,
      },
    });
    return res;
  } catch (err) {
    console.log('계약 목록 조회 실패 => ', err.response);
    throw err;
  }
};

// 계약 상세 조회
export const getContractDetail = async contractId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/contract/${contractId}`);
    return res;
  } catch (err) {
    console.log('계약 상세 조회 실패', err.response);
    throw err;
  }
};

// 계약 상세 조회 - 청구리스트
export const getContractDetailBillingList = async contractId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/contract/${contractId}/billing`);
    return res;
  } catch (err) {
    console.error('계약 상세 - 청구리스트 조회 실패', err.response.data);
    throw err;
  }
};

// 회원 수정 - 계약 정보
export const updateContractDetail = async (contractId, data) => {
  try {
    const res = await privateAxios.put(`/v1/vendor/contract/${contractId}`, data);
    return res;
  } catch (err) {
    console.error('계약 수정 => ', err.response.data);
    throw err;
  }
};
