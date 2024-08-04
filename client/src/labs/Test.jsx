import LoadingSpinner from '@/components/common/loadings/LoadingSpinner';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';
import Statistic from '@/components/common/statistics/Statistics';
import { useContext, useEffect, useState } from 'react';

const LoginPage = () => {
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async () => {
    setIsLoading(true);
    // 여기에 실제 로그인 로직을 구현합니다.
    await new Promise(resolve => setTimeout(resolve, 2000)); // 로그인 시뮬레이션
    setIsLoading(false);
  };

  return (
    <div className='min-h-screen bg-gray-100 flex flex-col justify-center py-12 sm:px-6 lg:px-8'>
      <div className='sm:mx-auto sm:w-full sm:max-w-md'>
        <h2 className='mt-6 text-center text-3xl font-extrabold text-gray-900'>Hyosung CMS#</h2>
        <p className='mt-2 text-center text-sm text-gray-600'>
          수납 및 자금 관리 통합 비즈니스 솔루션
        </p>
      </div>

      <div className='mt-8 sm:mx-auto sm:w-full sm:max-w-md'>
        <div className='bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10'>
          <form className='space-y-6'>
            <div>
              <label htmlFor='email' className='block text-sm font-medium text-gray-700'>
                아이디
              </label>
              <div className='mt-1'>
                <input
                  id='email'
                  name='email'
                  type='email'
                  required
                  className='appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-[#4FD1C5] focus:border-[#4FD1C5] sm:text-sm'
                />
              </div>
            </div>

            <div>
              <label htmlFor='password' className='block text-sm font-medium text-gray-700'>
                비밀번호
              </label>
              <div className='mt-1'>
                <input
                  id='password'
                  name='password'
                  type='password'
                  required
                  className='appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-[#4FD1C5] focus:border-[#4FD1C5] sm:text-sm'
                />
              </div>
            </div>

            <div>
              <LoginButton isLoading={isLoading} onClick={handleLogin} />
            </div>
          </form>

          <div className='mt-6'>
            <div className='relative'>
              <div className='absolute inset-0 flex items-center'>
                <div className='w-full border-t border-gray-300' />
              </div>
              <div className='relative flex justify-center text-sm'>
                <span className='px-2 bg-white text-gray-500'>
                  아직 회원이 아니신가요?{' '}
                  <a href='#' className='font-medium text-[#4FD1C5] hover:text-[#45b7ac]'>
                    회원가입
                  </a>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* {isLoading && <OverlaySpinner />} */}
    </div>
  );
};

const OverlaySpinner = () => {
  return (
    <div className='fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full flex items-center justify-center z-50'>
      <div className='flex flex-col items-center  relative p-8 bg-white rounded-lg shadow-xl'>
        <svg
          className='animate-spin h-12 w-12  text-[#4FD1C5]'
          xmlns='http://www.w3.org/2000/svg'
          fill='none'
          viewBox='0 0 24 24'>
          <circle
            className='opacity-25'
            cx='12'
            cy='12'
            r='10'
            stroke='currentColor'
            strokeWidth='4'
          />
          <path
            className='opacity-75 '
            fill='currentColor'
            d='M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z'
          />{' '}
        </svg>
        <p className='mt-4 text-[#4FD1C5] font-semibold'>로그인 중입니다...</p>
      </div>
    </div>
  );
};

const LoginButton = ({ isLoading, onClick }) => {
  return (
    <button
      onClick={onClick}
      disabled={isLoading}
      className={`w-full py-3 rounded-md transition duration-300 ${
        isLoading
          ? 'bg-[#4FD1C5] text-white cursor-not-allowed'
          : 'bg-[#4FD1C5] text-white hover:bg-[#45b7ac]'
      }`}>
      {isLoading ? (
        <div className='flex items-center justify-center'>
          <svg
            className='animate-spin -ml-1 mr-3 h-5 w-5 text-white'
            xmlns='http://www.w3.org/2000/svg'
            fill='none'
            viewBox='0 0 24 24'>
            <circle
              className='opacity-25'
              cx='12'
              cy='12'
              r='10'
              stroke='currentColor'
              strokeWidth='4'
            />
            <path
              className='opacity-75'
              fill='currentColor'
              d='M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z'
            />
          </svg>
          로그인 중...
        </div>
      ) : (
        '로그인'
      )}
    </button>
  );
};

const Test = () => {
  // msg : 원하는 메세지
  // type : 4가지(default, width, success, error), "" = defalut
  // title : alert제목 , "" = 효성 CMS#
  // const { alert: alertComp } = useContext(AlertContext);
  // const onAlert = async (msg, type, title, err = null) => {
  //   if (err != null) {
  //     const data = err.response.data;
  //     const fieldErrors = data.errors;

  //     msg = data.messeage;
  //     if (fieldErrors && fieldErrors.length > 0) {
  //       msg = fieldErrors.join('\n');
  //     }
  //   }

  //   const result = await alertComp(msg, type, title);
  // };

  console.log('infra test v0803-2');

  // type:
  // msg : 원하는 메세지
  // type : 2가지(default, warning), "" = defalut
  // title : alert제목 , "" = 효성 CMS#
  // const { confirm: confrimComp } = useContext(ConfirmContext);
  // const onConfirm = async (msg, type, title) => {
  //   const result = await confrimComp(msg, type, title);
  //   console.log(result);
  // };

  // const [isChecked, setIsChecked] = useState(false);

  // const handleCheckboxChange = () => {
  //   console.log('???', isChecked);
  //   setIsChecked(!isChecked);
  // };

  return (
    <div className='w-full'>
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
      {/* <div style={{ display: 'flex' }}>
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
      </div> */}

      <div className='flex flex-col items-center justify-center p-12 bg-white rounded-lg'>
        <div className='mb-8'>
          <svg className='w-24 h-24 text-teal-400' fill='currentColor' viewBox='0 0 24 24'>
            <path d='M19 5v14H5V5h14m0-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z' />
          </svg>
        </div>
        <h2 className='text-2xl font-semibold text-gray-800 mb-2'>할 일 목록이 비어있습니다</h2>
        <p className='text-gray-600 text-center mb-8'>
          새로운 할 일을 추가하여 생산적인 하루를 시작하세요.
        </p>
        <button className='px-6 py-3 bg-teal-400 text-white rounded-md hover:bg-teal-500 transition duration-300 focus:outline-none focus:ring-2 focus:ring-teal-500 focus:ring-opacity-50'>
          할 일 추가하기
        </button>
      </div>

      {/* <div className='flex items-center justify-center h-14 bg-gray-100'>
        <div className='flex items-center'>
          <input
            id='custom-checkbox'
            type='checkbox'
            className='hidden peer'
            checked={isChecked}
            onChange={handleCheckboxChange}
          />
          <label htmlFor='custom-checkbox' className='flex items-center cursor-pointer'>
            <span
              className={`w-5 h-5 flex items-center justify-center border border-gray-300 rounded-md bg-white ${isChecked ? 'bg-mint border-mint' : ''}`}>
              {isChecked && (
                <svg
                  className='w-3 h-3 text-white'
                  viewBox='0 0 24 24'
                  fill='none'
                  stroke='currentColor'>
                  <path
                    d='M5 13l4 4L19 7'
                    strokeWidth='2'
                    strokeLinecap='round'
                    strokeLinejoin='round'
                  />
                </svg>
              )}
            </span>
            <span className='ml-2 text-gray-700'>Custom Checkbox</span>
          </label>
        </div>
      </div> */}

      <label className='flex cursor-pointer gap-2'>
        <svg
          xmlns='http://www.w3.org/2000/svg'
          width='20'
          height='20'
          viewBox='0 0 24 24'
          fill='none'
          stroke='currentColor'
          strokeWidth='2'
          strokeLinecap='round'
          strokeLinejoin='round'>
          <circle cx='12' cy='12' r='5' />
          <path d='M12 1v2M12 21v2M4.2 4.2l1.4 1.4M18.4 18.4l1.4 1.4M1 12h2M21 12h2M4.2 19.8l1.4-1.4M18.4 5.6l1.4-1.4' />
        </svg>
        <input type='checkbox' value='synthwave' className='toggle theme-controller' />
        <svg
          xmlns='http://www.w3.org/2000/svg'
          width='20'
          height='20'
          viewBox='0 0 24 24'
          fill='none'
          stroke='currentColor'
          strokeWidth='2'
          strokeLinecap='round'
          strokeLinejoin='round'>
          <path d='M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z' />
        </svg>
        <Statistic />
      </label>
      <LoginPage />
      <DashboardChart />
      <LoadingSpinner size='lg' text='잠시만 기다려주세요...' />
      <LoadingSpinner size='xl' text='잠시만 기다려주세요...' />
      <LoadingSpinner size='xxl' text='잠시만 기다려주세요...' />
      <LoadingSpinner size='xxxl' text='잠시만 기다려주세요...' />
    </div>
  );
};

export default Test;

const DashboardLoadingSpinner = ({ size = 'md' }) => {
  const sizeClasses = {
    sm: 'w-8 h-8',
    md: 'w-12 h-12',
    lg: 'w-16 h-16',
  };

  return (
    <div className='flex justify-center items-center'>
      <div className={`${sizeClasses[size]} relative`}>
        <div className='absolute top-0 left-0 w-full h-full border-4 border-mint opacity-30 rounded-full animate-ping' />
        <div className='absolute top-0 left-0 w-full h-full border-4 border-mint border-t-transparent rounded-full animate-spin' />
      </div>
    </div>
  );
};

const DashboardState = ({ isLoading, isEmpty, emptyMessage = '데이터가 없습니다.', icon }) => {
  if (isLoading) {
    return (
      <div className='flex flex-col items-center justify-center h-64 bg-white rounded-xl shadow-dash-board p-6'>
        <DashboardLoadingSpinner size='lg' />
        <p className='mt-4 text-text_grey font-700'>로딩 중...</p>
      </div>
    );
  }

  if (isEmpty) {
    return (
      <div className='flex flex-col items-center justify-center h-64 bg-white rounded-xl shadow-dash-board p-6'>
        {icon ? (
          icon
        ) : (
          <svg
            className='w-16 h-16 text-mint'
            fill='none'
            stroke='currentColor'
            viewBox='0 0 24 24'
            xmlns='http://www.w3.org/2000/svg'>
            <path
              strokeLinecap='round'
              strokeLinejoin='round'
              strokeWidth='2'
              d='M9 17v-2m3 2v-4m3 4v-6m2 10H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z'
            />
          </svg>
        )}
        <p className='mt-4 text-text_grey font-700'>{emptyMessage}</p>
      </div>
    );
  }

  return null;
};

const DashboardChart = () => {
  const [data, setData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // 데이터를 가져오는 로직
    // setData(fetchedData);
    // setIsLoading(false);
  }, []);

  if (isLoading || data.length === 0) {
    return (
      <div className='dashboard-card'>
        <h2 className='text-xl font-700 text-text_black mb-4'>차트 제목</h2>
        <DashboardState
          isLoading={isLoading}
          isEmpty={!isLoading && data.length === 0}
          emptyMessage='표시할 데이터가 없습니다.'
        />
      </div>
    );
  }

  return (
    <div className='dashboard-card'>
      <h2 className='text-xl font-700 text-text_black mb-4'>차트 제목</h2>
      {/* 실제 차트 렌더링 */}
    </div>
  );
};
