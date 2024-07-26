import { useStatusStore } from '@/stores/useStatusStore';
import React, { useState, useEffect } from 'react';

const ProgressBar = ({ steps }) => {
  const { status, setStatus } = useStatusStore();
  const [animatedStatus, setAnimatedStatus] = useState(status);

  useEffect(() => {
    setAnimatedStatus(status);
  }, [status]);

  return (
    <div className='flex flex-col justify-center items-center'>
      <div className='flex justify-center items-center w-full py-3 px-6 '>
        {steps.map((step, index) => (
          <React.Fragment key={index}>
            <div className='flex items-center '>
              <div
                className={`w-[13px] h-[13px] rounded-full transition-all duration-300 ease-in-out cursor-pointer ${
                  animatedStatus >= index ? 'bg-text_black scale-110' : 'bg-ipt_border'
                }`}
                onClick={() => setStatus(index)}
              />
            </div>
            {index < steps.length - 1 && (
              <div className='w-60 h-0.5 relative'>
                <div className='absolute top-0 left-0 h-full bg-gray-300 w-full' />
                <div
                  className='absolute top-0 left-0 h-full bg-text_black transition-all duration-300 ease-in-out'
                  style={{ width: `${animatedStatus > index ? 100 : 0}%` }}
                />
              </div>
            )}
          </React.Fragment>
        ))}
      </div>

      <div className='flex justify-between items-center w-full '>
        {steps.map((step, index) => (
          <span
            key={index}
            className={`text-15 font-700 transition-colors duration-300 ease-in-out cursor-pointer ${
              animatedStatus >= index ? 'text-text_black' : 'text-ipt_border'
            }`}
            onClick={() => setStatus(index)}>
            {step}
          </span>
        ))}
      </div>
    </div>
  );
};

export default ProgressBar;
