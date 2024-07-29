import { useEffect } from 'react';

const Timer = ({ time, setTime, timeOut, style }) => {
  const formatTime = seconds => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${String(minutes).padStart(2, '0')}:${String(remainingSeconds).padStart(2, '0')}`;
  };

  useEffect(() => {
    const timer = setInterval(() => {
      setTime(prevTime => {
        console.log('??');
        if (prevTime <= 0) {
          clearInterval(timer);
          if (timeOut) {
            timeOut(); // 시간이 0일 때 timeOut 함수 호출
          }
          return 0;
        }
        return prevTime - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  return <div className={`${style}`}>{formatTime(time)}</div>;
};

export default Timer;
