import AlertContext from '@/utils/dialog/alert/AlertContext';
import AlertWdithContext from '@/utils/dialog/alertwidth/AlertWidthContext';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';
import { useContext } from 'react';

const Test = () => {
  // 결제
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async message => {
    const result = await alertComp(message);
  };

  const { alertWidth: alertWidthComp } = useContext(AlertWdithContext);
  const onAlertWidthClick = async message => {
    const result = await alertWidthComp(message);
  };

  const { confirm: confrimComp } = useContext(ConfirmContext);
  const confirmClick = async message => {
    const result = await confrimComp(message);
  };

  console.log('testv0');

  return (
    <div>
      <div style={{ display: 'flex', marginBottom: '20px' }}>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}>
          카드결제버튼
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}>
          계좌결제버튼
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}>
          가상계좌결제버튼
        </button>
      </div>
      <div style={{ display: 'flex' }}>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => onAlertClick('회원정보가 수정되었습니다!')}>
          alert
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => onAlertWidthClick('회원정보가 수정되었습니다!')}>
          alertwide
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => confirmClick('회원정보가 수정되었습니다!')}>
          confirm
        </button>
      </div>
    </div>
  );
};

export default Test;
