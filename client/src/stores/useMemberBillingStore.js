import { create } from 'zustand';

export const useMemberStore = create(set => ({
  // <---------- 청구정보 ---------->
  billingInfo: {
    invoiceSendMethod: '',
    autoInvoiceSend: false,
    autoBilling: false,
  },
}));
