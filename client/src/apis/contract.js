// TODO private으로 변경
import { publicAxios } from '.';

// 계약 목록 조회
export const getContractList = async (searchParams = {}, page, size= 8) => {
  try {
    return await publicAxios.get('/v1/vendor/contract', {
      params: {
        ...searchParams,
        page: page,
        size: size,
      },
    });
  } catch (err) {
    console.log('계약 목록 조회', err.response);
    throw err;
  }
};

// 계약 상세 조회
export const getContractDetail = async (contractId) => {
  try {
    return await publicAxios.get(`/v1/vendor/contract/${contractId}`);
  } catch (err) {
    console.log('계약 목록 조회', err.response);
    throw err;
  }
};
