// <----- 회원테이블 ----->

export const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'memberName', label: '회원이름', width: 'w-2/12' },
  { key: 'memberPhone', label: '휴대전화', width: 'w-2/12' },
  { key: 'contractPrice', label: '계약금액합', width: 'w-2/12' },
  { key: 'contractCount', label: '계약건수', width: 'w-1/12' },
  { key: 'memberEnrollDate', label: '회원등록일', width: 'w-2/12' },
  { key: 'memberEmail', label: '이메일', width: 'w-3/12' },
];

// Type : hidden, text, num, calendar, select
export const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'memberName', type: 'text', value: '', width: 'w-2/12' },
  { key: 'memberPhone', type: 'text', value: '', width: 'w-2/12' },
  { key: 'contractPrice', type: 'num', value: '', width: 'w-2/12' },
  { key: 'contractCount', type: 'num', value: '', width: 'w-1/12' },
  { key: 'memberEnrollDate', type: 'calendar', value: '', width: 'w-2/12' },
  { key: 'memberEmail', type: 'text', value: '', width: 'w-3/12' },
];

export const selectOptions = [
  { label: '계약금액 많은순', orderBy: 'contractPrice', order: 'DESC' },
  { label: '계약금액 적은순', orderBy: 'contractPrice', order: 'ASC' },
  { label: '계약 많은순', orderBy: 'contractCount', order: 'DESC' },
  { label: '계약 적은순', orderBy: 'contractCount', order: 'ASC' },
];
