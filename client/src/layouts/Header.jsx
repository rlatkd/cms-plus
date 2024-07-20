import BreadCrumb from '@/components/common/BreadCrumb';
import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';

const Header = () => {
  const { pathname } = useLocation();
  const mainRef = useRef(null);

  useEffect(() => {
    if (mainRef.current) {
      mainRef.current.scrollIntoView();
    }
  }, [pathname]);
  return (
    <div ref={mainRef} className='flex h-[11vh] pt-9 pl-9 pb-3'>
      <BreadCrumb />
    </div>
  );
};

export default Header;
