import { create } from 'zustand';

export const useMemberDetailStore = create(set => ({
  memberData: {
    billingCount: '',
    contractCount: '',
    createdDateTime: '',
    memberAddress: {
      address: '',
      addressDetail: '',
      zipcode: '',
    },
    memberEmail: '',
    memberEnrollDate: '',
    memberHomePhone: '',
    memberId: '',
    memberMemo: '',
    memberName: '',
    memberPhone: '',
    modifiedDateTime: '',
    totalBillingPrice: '',
  },
  setMemberInfo: Data => set({ memberData: Data }),
}));
