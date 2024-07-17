import { create } from 'zustand';

export const useUserDataStore = create((set, get) => ({
  userData: {
    // 기본 정보
    name: '',
    phone: '',
    homePhone: '',
    email: '',
    zipcode: '',
    address: '',
    addressDetail: '',
    // 계약 정보
    selectedProduct: '',
    items: [],
    contractName: '',
    startDate: '',
    endDate: '',
    contractDay: 1,
    totalPrice: 0,

    // 결제 정보
    paymentMethod: 'CARD',
    cardNumber: '',
    expiryDate: '',
    cardHolder: '',
    cardOwnerBirth: '',
    bank: '',
    accountHolder: '',
    accountOwnerBirth: '',
    accountNumber: '',

    // 서명 정보 추가
    signatureUrl: '',
  },
  setUserData: data =>
    set(state => {
      const newUserData = { ...state.userData, ...data };
      if (data.items) {
        newUserData.totalPrice = newUserData.items.reduce(
          (sum, item) => sum + item.quantity * item.price,
          0
        );
      }
      return { userData: newUserData };
    }),
  resetUserData: () =>
    set(state => ({
      userData: {
        ...state.userData,
        name: '',
        phone: '',
        homePhone: '',
        email: '',
        zipcode: '',
        address: '',
        addressDetail: '',
        selectedProduct: '',
        items: [],
        contractName: '',
        startDate: '',
        endDate: '',
        contractDay: 1,
        totalPrice: 0,
        paymentMethod: 'CARD',
        cardNumber: '',
        expiryDate: '',
        cardHolder: '',
        cardOwnerBirth: '',
        bank: '',
        accountHolder: '',
        accountOwnerBirth: '',
        accountNumber: '',
        signatureUrl: '',
      },
    })),
  clearPaymentInfo: () =>
    set(state => ({
      userData: {
        ...state.userData,
        cardNumber: '',
        expiryDate: '',
        cardHolder: '',
        cardOwnerBirth: '',
        bank: '',
        accountHolder: '',
        accountOwnerBirth: '',
        accountNumber: '',
      },
    })),
}));
