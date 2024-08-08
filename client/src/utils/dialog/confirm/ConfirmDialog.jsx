import { useState } from 'react';
import ConfirmContext from './ConfirmContext';
import Confirm from './Confirm';
import ConfirmWarning from './ConfirmWarning';

const ConfirmDialog = ({ children }) => {
  const [state, setState] = useState();

  const confirm = (message, type = 'default', title = '효성 CMS+') => {
    return new Promise(resolve => {
      setState({
        message: message !== undefined ? `${message}` : '',
        title: title !== undefined ? `${title}` : '',
        type: type,
        onClickOK: () => {
          setState(undefined);
          resolve(true);
        },
        onClickCancel: () => {
          setState(undefined);
          resolve(false);
        },
      });
    });
  };

  return (
    <ConfirmContext.Provider value={{ confirm }}>
      {children}
      {state && state.type === 'default' && (
        <Confirm
          title={state.title}
          message={state.message}
          onClickOK={state.onClickOK}
          onClickCancel={state.onClickCancel}
        />
      )}
      {state && state.type === 'warning' && (
        <ConfirmWarning
          title={state.title}
          message={state.message}
          onClickOK={state.onClickOK}
          onClickCancel={state.onClickCancel}
        />
      )}
    </ConfirmContext.Provider>
  );
};

export default ConfirmDialog;
