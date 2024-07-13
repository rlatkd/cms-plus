import { getMemberList } from '@/apis/member';

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
      </div>
    </>
  );
};

export default DashBoardPage;
