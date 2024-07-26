import { useState, useEffect } from 'react';

const Timer = ({ initialTime = 1800, style = 'text-text-black font-800 text-xl' }) => {
  const [time, setTime] = useState(initialTime);

  const formatTime = seconds => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${String(minutes).padStart(2, '0')}:${String(remainingSeconds).padStart(2, '0')}`;
  };

  useEffect(() => {
    const timer = setInterval(() => {
      setTime(prevTime => {
        if (prevTime <= 0) {
          clearInterval(timer);
          return 0;
        }
        return prevTime - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  return <div className={`inline-block px-3 py-1 ${style}`}>{formatTime(time)}</div>;
};

export default Timer;
