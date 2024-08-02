import { Link, useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft, faHome, faSearch } from '@fortawesome/free-solid-svg-icons';

const NotFoundPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const containsMember = location.pathname.includes('/member/');

  return (
    <div className='min-h-screen flex flex-col justify-center items-center px-4'>
      <div className='max-w-md w-full rounded-lg overflow-hidden'>
        <div className='p-8'>
          <div className='text-center'>
            <h1 className='text-9xl font-bold text-mint mb-4'>404</h1>
            <p className='text-2xl font-semibold text-gray-700 mb-4'>페이지를 찾을 수 없습니다</p>
            <p className='text-gray-500 mb-8'>
              죄송합니다. 요청하신 페이지를 찾을 수 없습니다. URL을 확인하시거나 아래 버튼을
              사용해주세요.
            </p>
          </div>
          <div className='flex justify-center space-x-4'>
            {!containsMember && (
              <Link
                to='/'
                className='flex items-center justify-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-mint hover:bg-mint_hover focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-mint'>
                <FontAwesomeIcon icon={faHome} className='mr-2' />
                홈으로 가기
              </Link>
            )}
            <button
              onClick={() => navigate(-2)}
              className='flex items-center justify-center px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-mint'>
              <FontAwesomeIcon icon={faArrowLeft} className='mr-2' />
              뒤로가기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default NotFoundPage;
