const Loading = () => {
  return (
    <div className='flex h-screen flex-col items-center justify-between bg-white p-3'>
      <div className='w-full text-left'>
        <h1 className='mb-4 text-2xl font-bold text-teal-400'>Hyosung CMS+</h1>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>로딩중</h3>
      </div>

      <div className='flex flex-grow items-center justify-center'>
        <div className='relative mb-40 h-48 w-52'>
          <div className='absolute inset-0 rounded-full bg-gradient-to-br from-teal-400 to-blue-200 opacity-50 blur-2xl animate-pulse' />
          <div className='absolute inset-0 flex items-center justify-center'>
            <div className='h-16 w-16 animate-spin'>
              <svg className='text-teal-500' viewBox='0 0 24 24'>
                <circle className='opacity-25' cx='12' cy='12' r='10' stroke='currentColor' strokeWidth='4' fill='none' />
                <path className='opacity-75 border-spacing-3' fill='currentColor' d='M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z' />
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Loading;