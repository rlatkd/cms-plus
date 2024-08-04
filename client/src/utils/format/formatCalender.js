import dayjs from 'dayjs';

// 오늘 날짜 이전을 비활성화
export const disabledStartDate = current => {
  return current && current < dayjs().startOf('day');
};

// 오늘 날짜 이후를 비활성화
export const disableFutureDates = current => {
  return current && current > dayjs().startOf('day');
};

// 선택된 시작일 이후 날짜만 활성화
export const disabledEndDate = (current, startDate) => {
  return current && current <= dayjs(startDate).startOf('day');
};
