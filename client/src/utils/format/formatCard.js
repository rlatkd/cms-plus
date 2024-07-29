// <----- 카드 정보 관련 포멧팅 함수 ----->

// 카드 년도 포맷팅 함수 (저장용)
export const formatCardYearForStorage = year => {
  year = String(year);
  if (!year || year.length !== 2) return '';
  return `20${year}`;
};

// 카드 년도 포맷팅 함수 (표시용)
export const formatCardYearForDisplay = year => {
  year = String(year);
  if (!year || year.length !== 4) return year;
  return String(year).substring(2);
};

// 카드 월 포맷팅 함수 (표시용)
export const formatCardMonthForDisplay = month => {
  month = String(month);
  if (!month || month.length !== 3) return month;
  return String(month).substring(2);
};
