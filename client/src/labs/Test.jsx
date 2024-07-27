import AlertContext from '@/utils/dialog/alert/AlertContext';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';
import { useContext } from 'react';

const Test = () => {
  // msg : 원하는 메세지
  // type : 4가지(default, width, success, error), "" = defalut
  // title : alert제목 , "" = 효성 CMS#
  const { alert: alertComp } = useContext(AlertContext);
  const onAlert = async (msg, type, title) => {
    const result = await alertComp(msg, type, title);
  };

  // type:
  // msg : 원하는 메세지
  // type : 2가지(default, warning), "" = defalut
  // title : alert제목 , "" = 효성 CMS#
  const { confirm: confrimComp } = useContext(ConfirmContext);
  const onConfirm = async (msg, type, title) => {
    const result = await confrimComp(msg, type, title);
    console.log(result);
  };


  console.log('test v1');


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
          onClick={() => onAlert('회원정보가 수정되었습니다!', 'default')}>
          alert
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => onAlert('회원정보가 수정되었습니다!', 'width')}>
          alertwide
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => onAlert('회원정보가 수정되었습니다!', 'success', '회원정보 수정 성공')}>
          alertSuccess
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => onAlert('회원정보가 수정되었습니다!', 'error', '회원정보 수정 실패')}>
          alertError
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => onConfirm('회원정보가 수정되었습니다!', 'default')}>
          confirm
        </button>
        <button
          style={{ border: '1px solid black', height: 60, padding: '10px', marginRight: '5px' }}
          onClick={() => onConfirm('회원정보가 수정하시겠습니까?', 'warning', '회원정보 수정')}>
          confirmWarning
        </button>
      </div>
    </div>
  );
};

export default Test;
