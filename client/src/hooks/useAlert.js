import { useContext } from 'react';
import AlertContext from '@/utils/dialog/alert/AlertContext';

const fieldToName = {
  billingType: '청구타입',
};

const useAlert = () => {
  const { alert: alertComp } = useContext(AlertContext);

  const onAlert = async (msg, type, title, err = null) => {
    if (err != null) {
      const data = err.response.data;
      const fieldErrors = data.errors;

      msg = data.message;
      if (fieldErrors && fieldErrors.length > 0) {
        msg = fieldErrors.map(
          fieldError => `${fieldToName[fieldError.field] || ''} ${fieldError.reason}`
        );
      }
    }

    await alertComp(msg, type, title);
  };

  return onAlert;
};

export default useAlert;
