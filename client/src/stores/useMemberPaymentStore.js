import { create } from 'zustand';

export const useMemberPaymentStore = create(set => ({
  // <------ 결제 정보 - 가상계좌 ------>
  paymentTypeInfoReq_Virtual: {
    paymentType: 'VIRTUAL',
    bank: '',
    accountOwner: '',
  },

  // 데이터 하나씩 set
  setPaymentTypeInfoReq_Virtual: data =>
    set(state => ({
      paymentTypeInfoReq_Virtual: {
        ...state.paymentTypeInfoReq_Virtual,
        ...data,
      },
    })),

  // 데이터 Reset
  resetPaymentTypeInfoReq_Virtual: () =>
    set({
      paymentTypeInfoReq_Virtual: {
        paymentType: 'VIRTUAL',
        bank: '',
        accountOwner: '',
      },
    }),

  // <------ 결제 정보 - 납부자결제 ------>
  paymentTypeInfoReq_Buyer: {
    paymentType: 'BUYER',
    availableMethods: [],
  },

  // 전체 결제 수단을 설정
  setAvailableMethods: methods =>
    set(state => ({
      paymentTypeInfoReq_Buyer: {
        ...state.paymentTypeInfoReq_Buyer,
        availableMethods: methods,
      },
    })),

  // 데이터 Reset
  resetPaymentTypeInfoReq_Buyer: () =>
    set({
      paymentTypeInfoReq_Buyer: {
        paymentType: 'BUYER',
        availableMethods: [],
      },
    }),

  // <---------- 결제 정보 - 자동결제 ---------->
  paymentTypeInfoReq_Auto: {
    paymentType: 'AUTO',
    consentImgUrl: '',
    simpleConsentReqDateTime: '',
    consetImgName: '',
  },

  // 데이터 하나씩 set
  setPaymentTypeInfoReq_Auto: data =>
    set(state => ({
      paymentTypeInfoReq_Auto: {
        ...state.paymentTypeInfoReq_Auto,
        ...data,
      },
    })),

  // 데이터 Reset
  resetPaymentTypeInfoReq_Auto: () =>
    set({
      paymentTypeInfoReq_Auto: {
        paymentType: 'AUTO',
        consentImgUrl: '',
        simpleConsentReqDateTime: '',
      },
    }),

  // <---------- 결제 방식 - CMS ---------->
  paymentMethodInfoReq_Cms: {
    paymentMethod: 'CMS',
    bank: '',
    accountNumber: '',
    accountOwner: '',
    accountOwnerBirth: '',
  },

  // 데이터 하나씩 set
  setPaymentMethodInfoReq_Cms: data =>
    set(state => ({
      paymentMethodInfoReq_Cms: {
        ...state.paymentMethodInfoReq_Cms,
        ...data,
      },
    })),

  // 데이터 Reset
  resetPaymentMethodInfoReq_Cms: () =>
    set({
      paymentMethodInfoReq_Cms: {
        paymentMethod: 'CMS',
        bank: '',
        accountNumber: '',
        accountOwner: '',
        accountOwnerBirth: '',
      },
    }),

  // <---------- 결제 방식 - 카드 ---------->
  paymentMethodInfoReq_Card: {
    paymentMethod: 'CARD',
    cardNumber: '',
    cardMonth: '',
    cardYear: '',
    cardOwner: '',
    cardOwnerBirth: '',
  },

  // 데이터 하나씩 set
  setPaymentMethodInfoReq_Card: data =>
    set(state => ({
      paymentMethodInfoReq_Card: {
        ...state.paymentMethodInfoReq_Card,
        ...data,
      },
    })),

  // 데이터 Reset
  resetPaymentMethodInfoReq_Card: () =>
    set({
      paymentMethodInfoReq_Card: {
        paymentMethod: 'CARD',
        cardNumber: '',
        cardMonth: '',
        cardYear: '',
        cardOwner: '',
        cardOwnerBirth: '',
      },
    }),
}));
