import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const useStatusStore = create(
  persist(
    set => ({
      status: 0,
      increment: () => set(state => ({ status: state.status + 1 })),
      decrement: () => set(state => ({ status: state.status - 1 })),
      reset: () => set({ status: 0 }),
    }),
    {
      name: 'status-storage', // 이름을 지정하여 localStorage에 저장
      getStorage: () => localStorage, // (선택 사항) 기본적으로 localStorage를 사용
    }
  )
);
