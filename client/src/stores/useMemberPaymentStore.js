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

  // <---------- 결제 정보 - 자동결제 ---------->
  paymentTypeInfoReq_Auto: {
    paymentType: 'AUTO',
    consentImgUrl: '',
    simpleConsentReqDateTime: '',
  },

  // <---------- 결제 방식 - 카드 ---------->
  paymentMethodInfoReq_Card: {
    paymentMethod: 'CARD',
    cardNumber: '',
    cardMonth: 0,
    cardYear: 0,
    cardOwner: '',
    cardOwnerBirth: '',
  },

  // <---------- 결제 방식 - CMS ---------->
  paymentMethodInfoReq_Cms: {
    paymentMethod: 'CMS',
    bank: '',
    accountNumber: '',
    accountOwner: '',
    accountOwnerBirth: '',
  },
}));
