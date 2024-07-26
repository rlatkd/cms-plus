import dayjs from 'dayjs';

// <------ 시분초가 포함된 날짜 포맷팅 ------>
const formatDateTime = dateTime => {
  return dayjs(dateTime).format('YYYY-MM-DD HH:mm:ss');
};

export default formatDateTime;
