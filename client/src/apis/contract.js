// TODO private으로 변경
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
