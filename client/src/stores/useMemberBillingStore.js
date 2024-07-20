import { create } from 'zustand';

export const useMemberBillingStore = create(set => ({
  // <------ 청구정보 ------>
  billingInfo: {
    invoiceSendMethod: 'SMS',
    autoInvoiceSend: true,
    autoBilling: true,
  },

  // <------ 데이터 하나씩 Set ------>
  setBillingInfoItem: data =>
    set(state => ({
      billingInfo: {
        ...state.billingInfo,
        ...data,
      },
    })),

  // <------ 데이터 Reset ------>
  resetBillingInfo: () =>
    set({
      billingInfo: {
        invoiceSendMethod: 'SMS',
        autoInvoiceSend: true,
        autoBilling: true,
      },
    }),
}));
