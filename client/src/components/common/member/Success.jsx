import successImage from '@/assets/success.svg';
import { useState, useEffect } from 'react';

const Success = ({ userData, content, name: propName }) => {
  const [name, setName] = useState('회원');

  useEffect(() => {
    if (userData && userData.memberDTO && userData.memberDTO.name) {
      setName(userData.memberDTO.name);
    }
  }, [userData]);


  return (
    <div className='flex h-screen flex-col items-center justify-between bg-white p-3'>
      <div className='w-full text-left'>
        <h1 className='mb-4 text-2xl font-bold text-teal-400'>Hyosung CMS+</h1>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          {name}님의
          <br />
          {content}
        </h3>
      </div>

      <div className='flex flex-grow items-center justify-center'>
        <div className='relative mb-40 h-48 w-52'>
          <div className='absolute inset-0 rounded-full bg-gradient-to-br from-teal-400 to-blue-200 opacity-50 blur-2xl' />
          <img
            src={successImage}
            alt='Card'
            className='absolute inset-0 z-10 h-full w-full object-contain'
          />
        </div>
      </div>
    </div>
  );
};

export default Success;
