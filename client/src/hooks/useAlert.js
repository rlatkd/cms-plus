import { useContext } from 'react';
import AlertContext from '@/utils/dialog/alert/AlertContext';

const fieldToName = {
  billingType: '청구타입 : ',
  memberEmail: '이메일 : ',
  memberPhone: '핸드폰 번호 : ',
  memberName: '회원 이름 : ',
  memberEnrollDate: '등록일 : ',
};

// msg : 원하는 메세지
// type : 4가지(default, width, success, error), "" = defalut
// title : alert제목 , "" = 효성 CMS#
// 사용법 => const onAlert = useAlert(); 정의

const useAlert = () => {
  const { alert: alertComp } = useContext(AlertContext);

  const onAlert = async ({ msg, type = 'default', title = '효성 CMS#', err = null }) => {
    if (err != null) {
      const data = err.response.data;
      const fieldErrors = data.errors;
      msg = data.message;
      type = 'error';
      if (fieldErrors && fieldErrors.length > 0) {
        // msg = fieldErrors.map(
        //   fieldError => `${fieldToName[fieldError.field] || ''} ${fieldError.reason}`
        // );
        msg = `${fieldToName[fieldErrors[0].field] || ''} ${fieldErrors[0].reason}`;
      }
    }

    await alertComp(msg, type, title);
  };

  return onAlert;
};

export default useAlert;
