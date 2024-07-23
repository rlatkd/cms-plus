import { create } from 'zustand';

export const useUserDataStore = create((set, get) => ({
  userData: {
    memberDTO: {
      name: '',
      phone: '',
      homePhone: '',
      email: '',
      zipcode: '',
      address: '',
      addressDetail: '',
    },
    paymentDTO: {
      paymentMethod: '',
      cardNumber: '',
      expiryDate: '',
      cardHolder: '',
      cardOwnerBirth: '',
      bank: '',
      accountHolder: '',
      accountOwnerBirth: '',
      accountNumber: '',
    },
    contractDTO: {
      selectedProduct: '',
      items: [],
      contractName: '',
      startDate: '',
      endDate: '',
      contractDay: 1,
      totalPrice: 0,
      signatureUrl: '',
    },
  },

  setUserData: data =>
    set(state => {
      const newUserData = {
        memberDTO: { ...state.userData.memberDTO, ...data.memberDTO },
        paymentDTO: { ...state.userData.paymentDTO, ...data.paymentDTO },
        contractDTO: { ...state.userData.contractDTO, ...data.contractDTO },
      };

      if (data.contractDTO && data.contractDTO.items) {
        newUserData.contractDTO.totalPrice = newUserData.contractDTO.items.reduce(
          (sum, item) => sum + item.quantity * item.price,
          0
        );
      }

      return { userData: newUserData };
    }),

  resetUserData: () =>
    set(() => ({
      userData: {
        memberDTO: {
          name: '',
          phone: '',
          homePhone: '',
          email: '',
          zipcode: '',
          address: '',
          addressDetail: '',
        },
        paymentDTO: {
          paymentMethod: '',
          cardNumber: '',
          expiryDate: '',
          cardHolder: '',
          cardOwnerBirth: '',
          bank: '',
          accountHolder: '',
          accountOwnerBirth: '',
          accountNumber: '',
        },
        contractDTO: {
          selectedProduct: '',
          items: [],
          contractName: '',
          startDate: '',
          endDate: '',
          contractDay: 1,
          totalPrice: 0,
          signatureUrl: '',
        },
      },
    })),

  clearPaymentInfo: () =>
    set(state => ({
      userData: {
        ...state.userData,
        paymentDTO: {
          ...state.userData.paymentDTO,
          cardNumber: '',
          expiryDate: '',
          cardHolder: '',
          cardOwnerBirth: '',
          bank: '',
          accountHolder: '',
          accountOwnerBirth: '',
          accountNumber: '',
        },
      },
    })),
}));
