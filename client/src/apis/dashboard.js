import { privateAxios } from '.';

// 현황 조회
export const getStatInfo = async (year, month) => {
  const res = await privateAxios.get(`/stat/info?year=${year}&month=${month}`);
  return res;
};

// 청구 목록 조회
export const getMonthBillingInfo = async (year, month) => {
  const res = await privateAxios.get(`/stat/billings?year=${year}&month=${month}`);
  return res;
};

// 탑 5 목록 조회
export const getTopInfo = async () => {
  const res = await privateAxios.get(`/stat/top`);
  return res;
};
