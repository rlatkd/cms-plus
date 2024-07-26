// <----- 상품테이블 ----->

export const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'productName', label: '상품명', width: 'w-3/12' },
  { key: 'productPrice', label: '금액', width: 'w-3/12' },
  { key: 'contractNumber', label: '계약수', width: 'w-3/12' },
  { key: 'productCreatedDate', label: '생성일', width: 'w-3/12' },
  { key: 'productMemo', label: '비고', width: 'w-3/12' },
];

// Type : hidden, text, num, calendar, select
export const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'productName', type: 'text', value: '', width: 'w-3/12' },
  { key: 'productPrice', type: 'num', value: '', width: 'w-3/12' },
  { key: 'contractNumber', type: 'num', value: '', width: 'w-3/12' },
  { key: 'productCreatedDate', type: 'calendar', value: '', width: 'w-3/12' },
  { key: 'productMemo', type: 'text', value: '', width: 'w-3/12' },
];

export const selectOptions = [
  { label: '높은 가격순', orderBy: 'productPrice', order: 'DESC' },
  { label: '낮은 가격순', orderBy: 'productPrice', order: 'ASC' },
  { label: '계약 많은순', orderBy: 'contractCount', order: 'DESC' },
  { label: '계약 적은순', orderBy: 'contractCount', order: 'ASC' },
];
