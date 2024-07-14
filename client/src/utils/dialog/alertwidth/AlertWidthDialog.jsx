import { useState } from 'react';
import AlertWidthContext from './AlertWidthContext';
import AlertWidth from './AlertWidth';

const AlertWidthDialog = ({ children }) => {
  const [state, setState] = useState();

  const alertWidth = message => {
    return new Promise(resolve => {
      setState({
        message: message !== undefined ? `${message}` : '',
        onClose: () => {
          setState(undefined);
          resolve(undefined);
        },
      });
    });
  };

  return (
    <AlertWidthContext.Provider value={{ alertWidth }}>
      {children}
      {state && <AlertWidth message={state.message} onClose={state.onClose} />}
    </AlertWidthContext.Provider>
  );
};

export default AlertWidthDialog;
