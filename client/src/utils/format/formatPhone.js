export const formatPhone = phone => {
  if (!phone) return '';

  if (phone.startsWith('011') || phone.startsWith('010')) {
    // 011 또는 010으로 시작하는 휴대전화 번호 포맷팅
    if (phone.length === 10) {
      return phone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
    } else if (phone.length === 11) {
      return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    }
  } else if (phone.startsWith('02')) {
    // 02로 시작하는 전화번호 포맷팅
    if (phone.length === 9) {
      return phone.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
    } else if (phone.length === 10) {
      return phone.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
    }
  } else if (/^0[1-9][0-9]/.test(phone)) {
    // 그 외 지역번호로 시작하는 전화번호 포맷팅
    if (phone.length === 10) {
      return phone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
    } else if (phone.length === 11) {
      return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    }
  }
  return phone;
};

export const removeDashes = phone => {
  if (!phone) return '';
  // 전화번호에서 모든 대시를 제거하고 숫자만 이어붙임
  return phone.replace(/-/g, '');
};
