import BreadCrumb from '@/components/common/BreadCrumb';
import Timer from '@/components/common/Timer';
import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import profile from '@/assets/profile.svg';
import clock from '@/assets/clock.svg';
import Reset from '@/assets/Reset';
import { useVendorInfoStore } from '@/stores/useVendorInfoStore';
import { postRefreshToken } from '@/apis/auth';
import useConfirm from '@/hooks/useConfirm';

const Header = () => {
  const { pathname } = useLocation();
  const { vendorInfo } = useVendorInfoStore();
  const onConfirm = useConfirm();
  const navigate = useNavigate();
  const mainRef = useRef(null);
  const [time, setTime] = useState(() => JSON.parse(localStorage.getItem('time')) || 3600);

  // <----- 시간 로컬 스토리지에 저장 ----->
  useEffect(() => {
    console.log('시작??');
    localStorage.setItem('time', JSON.stringify(time));
  }, [time]);

  // <----- 로그인 연장(refresh 토큰 재발급) API ----->
  const axiosExtendLogin = async () => {
    try {
      const isExtendLogin = await onConfirm({
        msg: `로그인 연장 시 로그인 시간이 60분으로 늘어납니다!`,
        type: 'warning',
        title: '로그인을 연장 하시겠습니까?',
      });
      if (!isExtendLogin) {
        if (time === 0) {
          localStorage.clear(); // 로컬 스토리지 비우기
          navigate('/');
        }
        return;
      }

      const res = await postRefreshToken();
      localStorage.setItem('access_token', res.data.accessToken);
      console.log('!----로그인 연장----!'); // 삭제예정
      setTime(3600);
      console.log('time : ', time);
    } catch (err) {
      console.error('axiosExtendLogin => ', err.response);
    }
  };

  // <----- 라우팅 시 페이지 상단고정 ----->
  useEffect(() => {
    if (mainRef.current) {
      mainRef.current.scrollIntoView();
    }
  }, [pathname]);

  return (
    <div
      ref={mainRef}
      className='flex justify-between pt-9 px-12 pb-3 h-[20%] desktop:h-[11vh] extra_desktop:h-[10vh] '>
      <BreadCrumb />
      <div className='flex items-center py-4'>
        <div className='flex items-center '>
          <img src={clock} alt='clock' className='w-4 h-4' />
          <Timer
            time={time}
            setTime={setTime}
            timeOut={axiosExtendLogin}
            style='text-text-black mx-2 font-700'
          />
          <div onClick={axiosExtendLogin}>
            <Reset width='15px' height='15px' fill='#344767' />
          </div>
        </div>
        <div className='border border-text_grey h-full mx-5' />
        <img src={profile} alt='profile' className='w-9 h-9 cursor-pointer ' />
        <p className='text-text_black text-xl font-800 ml-2'>{vendorInfo.name}님</p>
      </div>
    </div>
  );
};

export default Header;
