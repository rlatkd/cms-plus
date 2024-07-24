import { privateAxios } from '.';

// 현황 조회
export const getStatInfo = async (year, month) => {
  try {
    return await privateAxios.get(`/stat/info?year=${year}&month=${month}`);
  } catch (err) {
    console.error('대시보드 현황 조회', err.response);
    throw err;
  }
};

// 청구 목록 조회
export const getMonthBillingInfo = async (year, month) => {
  try {
    return await privateAxios.get(`/stat/billings?year=${year}&month=${month}`);
  } catch (err) {
    console.error('대시보드 청구 목록 조회', err.response);
    throw err;
  }
};

// 탑 5 목록 조회
export const getTopInfo = async () => {
  try {
    return await privateAxios.get(`/stat/top`);
  } catch (err) {
    console.error('대시보드 탑 5 목록 조회', err.response);
    throw err;
  }
};
