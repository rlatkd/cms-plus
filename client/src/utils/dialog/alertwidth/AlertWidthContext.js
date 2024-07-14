import { createContext } from 'react';

const AlertWdithContext = createContext({
  alertWidth: () => new Promise((_, reject) => reject()),
});

export default AlertWdithContext;
