import { create } from 'zustand';

export const useMemberContractStore = create(set => ({
  // <---------- 계약정보 ---------->
  contractInfo: {
    contractName: '',
    contractStartDate: '',
    contractEndDate: '',
    contractDay: '',
    contractProducts: [],
  },

  // 객체를 한번에 Set
  setContractInfo: Data => set({ contractInfo: Data }),

  // 데이터 하나씩 Set
  setContractInfoItem: data =>
    set(state => ({
      contractInfo: {
        ...state.contractInfo,
        ...data,
      },
    })),

  // 전체 contractProducts를 설정
  setContractProducts: products =>
    set(state => ({
      contractInfo: {
        ...state.contractInfo,
        contractProducts: products,
      },
    })),

  // 데이터 Reset
  resetContractInfo: () =>
    set({
      contractInfo: {
        contractName: '',
        contractStartDate: '',
        contractEndDate: '',
        contractDay: '',
        contractProducts: [],
      },
    }),
}));
