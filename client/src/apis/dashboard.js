import { publicAxios } from '.';

// 현황 조회
export const getStatInfo = async (year, month) => {
  try {
    return await publicAxios.get(`/stat/info?year=${year}&month=${month}`);
  } catch (err) {
    console.log('대시보드 현황 조회', err.response);
    throw err;
  }
};

export const getMonthBillingInfo = async (year, month) => {
  try {
    return await publicAxios.get(`/stat/billings?year=${year}&month=${month}`);
  } catch (err) {
    console.log('대시보드 청구 목록 조회', err.response);
    throw err;
  }
};

export const getTopInfo = async () => {
  try {
    return await publicAxios.get(`/stat/top`);
  } catch (err) {
    console.log('대시보드 탑 5 목록 조회', err.response);
    throw err;
  }
};
