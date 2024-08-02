// 대시보드 로딩 스피너 컴포넌트
const LoadingSpinner = ({ size = 'md', text = '로딩 중...' }) => {
  const sizeClasses = {
    sm: 'w-8 h-8',
    md: 'w-12 h-12',
    lg: 'w-16 h-16',
    xl: 'w-20 h-20',
    xxl: 'w-28 h-28',
    xxxl: 'w-32 h-32',
  };

  return (
    <div className='flex flex-col items-center border border-red-500'>
      <div className='flex justify-center items-center'>
        <div className={`${sizeClasses[size]} relative`}>
          <div className='absolute top-0 left-0 w-full h-full border-4 border-mint opacity-30 rounded-full animate-ping' />
          <div className='absolute top-0 left-0 w-full h-full border-4 border-mint border-t-transparent rounded-full animate-spin' />
        </div>
      </div>
      <p className='mt-4 text-text_grey font-700'>{text}</p>
    </div>
  );
};

export default LoadingSpinner;
