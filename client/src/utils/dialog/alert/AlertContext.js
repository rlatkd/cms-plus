import { createContext } from 'react';

const AlertContext = createContext({
  alert: () => new Promise((_, reject) => reject()),
});

export default AlertContext;
