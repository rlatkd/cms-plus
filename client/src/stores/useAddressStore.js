import { create } from 'zustand';

export const useAddressStore = create((set, get) => ({
  zipcode: '',
  address: '',
  addressDetail: '',
  setZipcode: zipcode => {
    set({ zipcode });
  },
  setAddress: address => {
    set({ address });
  },
  setAddressDetail: addressDetail => {
    set({ addressDetail });
  },
  reset: () => set({ zipcode: '', address: '', addressDetail: '' }),
}));
