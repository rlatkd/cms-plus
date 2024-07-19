import { create } from 'zustand';

export const useInvoiceStore = create((set) => ({
  invoiceInfo: null,
  selectedCard: '',

  setInvoiceInfo: (billingInfo) =>  set({ invoiceInfo: billingInfo}),
  setSelectedCard: (card) => set({ selectedCard: card })
}));
