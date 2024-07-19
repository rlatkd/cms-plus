import { create } from 'zustand';

export const useMemberBasicStore = create(set => ({
  // <---------- 기본정보 ---------->
  basicInfo: {
    memberName: '',
    memberPhone: '',
    memberEnrollDate: '',
    memberHomePhone: '',
    memberEmail: '',
    memberAddress: {
      address: '',
      addressDetail: '',
      zipcode: '',
    },
    memberMemo: '',
  },

  // 객체를 한번에 Set
  setBasicInfo: Data => set({ basicInfo: Data }),

  // 데이터 하나씩 Set
  setBasicInfoItem: data =>
    set(state => ({
      basicInfo: {
        ...state.basicInfo,
        ...data,
      },
    })),

  // Address 데이터 하나씩 set
  setAddressInfoItem: data =>
    set(state => ({
      basicInfo: {
        ...state.basicInfo,
        memberAddress: {
          ...state.basicInfo.memberAddress,
          ...data,
        },
      },
    })),

  // 데이터 Reset
  resetBasicInfo: () =>
    set({
      basicInfo: {
        memberName: '',
        memberPhone: '',
        memberEnrollDate: '',
        memberHomePhone: '',
        memberEmail: '',
        memberAddress: {
          address: '',
          addressDetail: '',
          zipcode: '',
        },
        memberMemo: '',
      },
    }),
}));
