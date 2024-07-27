import BreadCrumb from '@/components/common/BreadCrumb';
import Timer from '@/components/common/Timer';
import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';

const Header = () => {
  const { pathname } = useLocation();
  const mainRef = useRef(null);

  // <----- 라우팅 시 페이지 상단고정 ----->
  useEffect(() => {
    if (mainRef.current) {
      mainRef.current.scrollIntoView();
    }
  }, [pathname]);

  return (
    <div
      ref={mainRef}
      className='flex justify-between pt-9 px-9 pb-3 h-[20%] desktop:h-[11vh] extra_desktop:h-[10vh] border border-red-400'>
      <BreadCrumb />
      <Timer />
    </div>
  );
};

export default Header;
