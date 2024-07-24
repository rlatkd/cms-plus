import BreadCrumb from '@/components/common/BreadCrumb';
import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';

const Header = () => {
  const { pathname } = useLocation();
  const mainRef = useRef(null);

  // <--------라우팅 시 페이지 상단고정-------->
  useEffect(() => {
    if (mainRef.current) {
      mainRef.current.scrollIntoView();
    }
  }, [pathname]);

  return (
    <div
      ref={mainRef}
      className='flex pt-9 pl-9 pb-3 h-[20%] desktop:h-[11vh] extra_desktop:h-[10vh] '>
      <BreadCrumb />
    </div>
  );
};

export default Header;
