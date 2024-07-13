import { useState } from 'react';
import ConfirmContext from './ConfirmContext';
import Confirm from './Confirm';

const ConfirmDialog = ({ children }) => {
  const [state, setState] = useState();

  const confirm = message => {
    return new Promise(resolve => {
      setState({
        message: message ?? '',
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
      {state && (
        <Confirm
          message={state.message}
          onClickOK={state.onClickOK}
          onClickCancel={state.onClickCancel}
        />
      )}
    </ConfirmContext.Provider>
  );
};

export default ConfirmDialog;
