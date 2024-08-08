import { useState } from 'react';
import AlertContext from './AlertContext';
import Alert from './Alert';
import AlertWidth from './AlertWidth';
import AlertSuccess from './AlertSuccess';
import AlertError from './AlertError';

const AlertDialog = ({ children }) => {
  const [state, setState] = useState();

  const alert = (message, type = 'default', title = '효성 CMS+') => {
    return new Promise(resolve => {
      setState({
        message: message !== undefined ? `${message}` : '',
        title: title !== undefined ? `${title}` : '',
        type: type,
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
      {state && state.type == 'default' && (
        <Alert title={state.title} message={state.message} onClose={state.onClose} />
      )}
      {state && state.type == 'width' && (
        <AlertWidth title={state.title} message={state.message} onClose={state.onClose} />
      )}
      {state && state.type == 'success' && (
        <AlertSuccess title={state.title} message={state.message} onClose={state.onClose} />
      )}
      {state && state.type == 'error' && (
        <AlertError title={state.title} message={state.message} onClose={state.onClose} />
      )}
    </AlertContext.Provider>
  );
};

export default AlertDialog;
