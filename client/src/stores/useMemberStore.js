import { create } from 'zustand';

export const useMemberStore = create(set => ({
  // <---------- 청구정보 ---------->
  billingInfo: {
    invoiceSendMethod: '',
    autoInvoiceSend: false,
    autoBilling: false,
  },

  setBillingInfo: Data => set({ billingInfo: Data }),

  // <---------- 계약정보 ---------->
  contractCreateReq: {
    contractName: '',
    contractStartDate: '',
    contractEndDate: '',
    contractDay: '',
    contractProducts: [],
  },

  // <---------- 결제 정보 - 가상계좌 ---------->
  paymentTypeInfoReq_Virtual: {
    paymentType: 'VIRTUAL',
    bank: '',
    accountOwner: '',
  },

  // <---------- 결제 정보 - 납부자결제 ---------->
  paymentTypeInfoReq_Buyer: {
    paymentType: 'BUYER',
    availableMethods: [],
  },

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
