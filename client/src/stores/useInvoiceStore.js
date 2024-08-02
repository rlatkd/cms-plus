import { create } from 'zustand';

export const useInvoiceStore = create(set => ({
  invoiceInfo: '',
  selectedCard: '',
  selectedBank: '',

  setInvoiceInfo: billingInfo => set({ invoiceInfo: billingInfo }),
  setSelectedCard: card => set({ selectedCard: card }),
  setSelectedBank: bank => set({ selectedBank: bank }),
}));
