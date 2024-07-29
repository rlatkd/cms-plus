// <----- 생년월일 포맷팅 함수 ----- >

export const formatBirth = birth => {
  if (!birth) return '';

  // 생년월일이 8자리일 때 YYYY-MM-DD 포맷팅
  if (birth.length === 8) {
    return birth.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
  }

  return birth;
};

// <------ 생년월일에서 대시 제거 함수 ------>
export const removeDashesFromBirth = birth => {
  if (!birth) return '';
  // 생년월일에서 모든 대시를 제거하고 숫자만 이어붙임
  return birth.replace(/-/g, '');
};

// <------ 생년월일 포맷팅 함수 ------>
export const formatBirthDate = value => {
  const cleaned = value.replace(/\D/g, '');
  let formatted = cleaned;

  if (cleaned.length > 4) {
    formatted = `${cleaned.slice(0, 4)}-${cleaned.slice(4)}`;
  }
  if (cleaned.length > 6) {
    formatted = `${formatted.slice(0, 7)}-${formatted.slice(7)}`;
  }

  return formatted.slice(0, 10);
};
