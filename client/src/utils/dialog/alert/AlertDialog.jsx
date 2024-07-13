import { useState } from 'react';
import AlertContext from './AlertContext';
import Alert from './Alert';

const AlertDialog = ({ children }) => {
  const [state, setState] = useState();

  const alert = message => {
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
    <AlertContext.Provider value={{ alert }}>
      {children}
      {state && <Alert message={state.message} onClose={state.onClose} />}
    </AlertContext.Provider>
  );
};

export default AlertDialog;
