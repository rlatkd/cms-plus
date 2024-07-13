import { createContext } from 'react';

const ConfirmContext = createContext({
  confirm: () => new Promise((_, reject) => reject()),
});

export default ConfirmContext;
