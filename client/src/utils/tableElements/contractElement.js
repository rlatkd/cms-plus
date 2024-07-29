// <----- 계약테이블 ----->

export const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'memberName', label: '회원이름', width: 'w-1/12' },
  { key: 'memberPhone', label: '휴대전화', width: 'w-2/12' },
  { key: 'contractDay', label: '약정일', width: 'w-1/12' },
  { key: 'contractProducts', label: '상품', width: 'w-2/12' },
  { key: 'contractPrice', label: '계약금액', width: 'w-2/12' },
  { key: 'paymentType', label: '결제방식', width: 'w-2/12' },
  { key: 'contractStatus', label: '계약상태', width: 'w-2/12' }, // 계약상태
];

// Type : hidden, text, num, calendar, select
export const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'memberName', type: 'text', value: '', width: 'w-1/12' },
  { key: 'memberPhone', type: 'text', value: '', width: 'w-2/12' },
  {
    key: 'contractDay',
    type: 'select',
    value: '',
    width: 'w-1/12',
    options: Array.from({ length: 31 }, (_, i) => ({ key: i + 1, value: `${i + 1}일` })),
  },
  { key: 'productName', type: 'text', value: '', width: 'w-2/12' },
  { key: 'contractPrice', type: 'num', value: '', width: 'w-2/12' },
  {
    key: 'paymentType',
    type: 'select',
    value: '',
    width: 'w-2/12',
    options: [
      { key: 'AUTO', value: '자동결제' },
      { key: 'BUYER', value: '납부자결제' },
      { key: 'VIRTUAL', value: '가상계좌' },
    ],
  },
  {
    key: 'contractStatus',
    type: 'select',
    value: '',
    width: 'w-2/12',
    options: [
      { key: 'DISABLED', value: '계약종료' },
      { key: 'ENABLED', value: '진행중' },
    ],
  },
];

export const selectOptions = [
  { label: '계약금액 많은순', orderBy: 'contractPrice', order: 'DESC' },
  { label: '계약금액 적은순', orderBy: 'contractPrice', order: 'ASC' },
  { label: '약정일 빠른순', orderBy: 'contractDay', order: 'ASC' },
  { label: '약정일 느린순', orderBy: 'contractDay', order: 'DESC' },
];
