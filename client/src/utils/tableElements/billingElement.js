// <----- 청구테이블 ----->

export const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'memberName', label: '회원이름', width: 'w-2/12' },
  { key: 'memberPhone', label: '휴대전화', width: 'w-2/12' },
  { key: 'billingProducts', label: '상품', width: 'w-2/12' },
  { key: 'billingPrice', label: '청구금액', width: 'w-2/12' },
  { key: 'billingDate', label: '결제일(약정일)', width: 'w-2/12' },
  { key: 'paymentType', label: '계약방식', width: 'w-2/12' },
  { key: 'billingStatus', label: '청구상태', width: 'w-2/12' },
];

// Type : hidden, text, num, calendar, select
export const initialSearch = [
  { key: 'checkbox', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'order', type: 'hidden', value: '', width: 'w-1/12' },
  { key: 'memberName', type: 'text', value: '', width: 'w-2/12' },
  { key: 'memberPhone', type: 'text', value: '', width: 'w-2/12' },
  { key: 'productName', type: 'text', value: '', width: 'w-2/12' },
  { key: 'billingPrice', type: 'num', value: '', width: 'w-2/12' },
  { key: 'billingDate', type: 'calendar', value: '', width: 'w-2/12' },
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
    key: 'billingStatus',
    type: 'select',
    value: '',
    width: 'w-2/12',
    options: [
      { key: 'CREATED', value: '청구생성' },
      { key: 'WAITING_PAYMENT', value: '수납대기' },
      { key: 'PAID', value: '완납' },
      { key: 'NON_PAID', value: '미납' },
    ],
  },
];

export const selectOptions = [
  { label: '청구금액 많은순', orderBy: 'billingPrice', order: 'DESC' },
  { label: '청구금액 적은순', orderBy: 'billingPrice', order: 'ASC' },
];
