import { useContext } from 'react';
import AlertContext from '@/utils/dialog/alert/AlertContext';

const fieldToName = {
  billingType: '청구타입',
};

// msg : 원하는 메세지
// type : 4가지(default, width, success, error), "" = defalut
// title : alert제목 , "" = 효성 CMS#
// 사용법 => const onAlert = useAlert(); 정의

const useAlert = () => {
  const { alert: alertComp } = useContext(AlertContext);

  const onAlert = async ({ msg, type = 'default', title = '효성 CMS#', err = null }) => {
    console.log('??', err);

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
