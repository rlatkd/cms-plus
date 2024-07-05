import { useNavigate } from 'react-router-dom';

const MemberListPage = () => {
  const navigate = useNavigate();

  // id값은 추후 변경
  const handleGoDetail = () => {
    navigate('detail/1');
  };

  const handleGoRegister = () => {
    navigate('register');
  };
  return (
    <div className='h-full w-full rounded-xl p-6 shadow-dash-board'>
      MemberListPage
      <br />
      <button className='rounded-lg bg-mint p-3 font-bold text-white' onClick={handleGoDetail}>
        임시 회원 상세 이동
      </button>
      <button className='rounded-lg bg-mint p-3 font-bold text-white' onClick={handleGoRegister}>
        임시 회원 등록
      </button>
    </div>
  );
};

export default MemberListPage;
