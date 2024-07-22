import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const useStatusStore = create(
  persist(
    set => ({
      status: 0,
      increment: () => set(state => ({ status: state.status + 1 })),
      decrement: () => set(state => ({ status: state.status - 1 })),
      reset: () => set({ status: 0 }),
      setStatus: newStatus => set({ status: newStatus }),
    }),
    {
      name: 'status-storage',
      getStorage: () => localStorage,
    }
  )
);
