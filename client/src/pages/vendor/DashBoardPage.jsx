import { useContext } from 'react';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import AlertWdithContext from '@/utils/dialog/alertwidth/AlertWidthContext';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';

const DashBoardPage = () => {
  const { alert: alertComp } = useContext(AlertContext);

  const onAlertClick = async () => {
    const result = await alertComp('인증번호가 전송되었습니다!');
    console.log('onAlertClick : ', result);
  };

  const { alertWidth: alertCompWidth } = useContext(AlertWdithContext);

  const onAlertWidthClick = async () => {
    const result = await alertCompWidth('hello world Width');
    console.log('onAlertWidthClick : ', result);
  };

  const { confirm: confirmComp } = useContext(ConfirmContext);

  const onConfirmClick = async () => {
    const result = await confirmComp('상품을 삭제 하시겠습니까?');
    console.log('onConfirmClick : ', result);
  };

  return (
    <>
      <div className='mb-10 flex justify-between'>
        <div className='primary-dashboard h-36 w-1/5'>
          <p>DashBoard</p>
        </div>
        <div className='primary-dashboard h-36 w-1/5'>
          <p>DashBoard</p>
        </div>
        <div className='primary-dashboard h-36 w-1/5'>
          <p>DashBoard</p>
        </div>{' '}
        <div className='primary-dashboard h-36 w-1/5'>
          <p>DashBoard</p>
        </div>
      </div>
      <div className='primary-dashboard flex-1'>
        <p className='mb-2'>DashBoard</p>
        <div className='flex'>
          <button
            className='border-2 border-mint p-3 mr-2 rounded-md'
            onClick={() => onAlertClick()}>
            alert
          </button>
          <br />
          <button
            className='border-2 border-mint p-3 mr-2 rounded-md'
            onClick={() => onAlertWidthClick()}>
            alertwidth
          </button>
          <br />
          <button
            className='border-2 border-mint p-3 mr-2 rounded-md'
            onClick={() => onConfirmClick()}>
            confirm
          </button>
        </div>
      </div>
    </>
  );
};

export default DashBoardPage;
