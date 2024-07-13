import { getMemberList } from '@/apis/member';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';
import { useContext } from 'react';

const DashBoardPage = () => {
  // 회원 목록 조회
  const axiosMemberList = async () => {
    try {
      const res = await getMemberList();
      console.log('!----회원 목록 조회 성공----!'); // 삭제예정
      console.log(res.data);
    } catch (err) {
      console.error('axiosMemberList => ', err.response.data);
    }
  };

  const { alert: alertComp } = useContext(AlertContext);

  const onAlertClick = async () => {
    const result = await alertComp('hello world');
  };

  const { confirm: confirmComp } = useContext(ConfirmContext);

  const onConfirmClick = async () => {
    const result = await confirmComp('are you sure?');
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
        <p>DashBoard</p>
        <button onClick={() => axiosMemberList()}>임시버튼</button>
        <br />
        <button onClick={() => onAlertClick()}>alert (component)</button>
        <br />
        <button onClick={() => onConfirmClick()}>confirm (component)</button>
      </div>
    </>
  );
};

export default DashBoardPage;
