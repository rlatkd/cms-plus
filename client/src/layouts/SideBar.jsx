import { NavLink } from 'react-router-dom';
import Card from '@/assets/Card';
import File from '@/assets/File';
import Home from '@/assets/Home';
import Item from '@/assets/Item';
import Setting from '@/assets/Setting';
import TmpLogo from '@/assets/TmpLogo';
import UpArrow from '@/assets/UpArrow';
import User from '@/assets/User';

const SideBar = () => {
  return (
    <div className='hidden h-full w-96 animate-slideOut p-0 desktop:block desktop:animate-slideIn desktop:p-6'>
      <div className='mb-6 ml-3 mt-3 flex items-center'>
        <TmpLogo className='mr-2 h-7 w-7' />
        <h1 className='text-xl font-bold text-text_black'>HYOSUNG CMS#</h1>
      </div>
      <div className='border-gradient mb-6 border-b-2' />

      <NavLink
        to='dashboard'
        className={({ isActive }) =>
          `mb-1 flex cursor-pointer ${isActive ? 'rounded-xl bg-white shadow-sidebars' : 'bg-transparent'} p-2`
        }>
        {({ isActive }) => (
          <div className='flex h-full w-full items-center justify-between'>
            <div className='flex items-center'>
              <div
                className={`mr-3 flex h-10 w-10 items-center justify-center rounded-xl ${isActive ? 'bg-mint' : 'bg-white shadow-sidebars'}`}>
                <Home className='h-5 w-5' fill={isActive ? 'white' : '#4FD1C5'} />
              </div>
              <p
                className={`text-17 ${isActive ? 'font-bold text-text_black' : 'font-bold text-text_grey'}`}>
                대시보드
              </p>
            </div>
          </div>
        )}
      </NavLink>

      <NavLink
        to='members'
        className={({ isActive }) =>
          `mb-1 flex cursor-pointer ${isActive ? 'rounded-xl bg-white shadow-sidebars' : 'bg-transparent'} p-2`
        }>
        {({ isActive }) => (
          <div className='flex h-full w-full items-center justify-between'>
            <div className='flex items-center'>
              <div
                className={`mr-3 flex h-10 w-10 items-center justify-center rounded-xl ${isActive ? 'bg-mint' : 'bg-white shadow-sidebars'}`}>
                <User className='h-5 w-5' fill={isActive ? 'white' : '#4FD1C5'} />
              </div>
              <p
                className={`text-17 ${isActive ? 'font-bold text-text_black' : 'font-bold text-text_grey'}`}>
                회원
              </p>
            </div>
            <UpArrow className='mr-3 h-4 w-4' rotation={isActive ? 0 : 180} />
          </div>
        )}
      </NavLink>
      <div className='my-3 flex items-center'>
        <div className='mx-6 h-2 w-2 rounded-full bg-mint' />
        <p className='text-17 text-text_grey'>회원목록</p>
      </div>

      <NavLink
        to='contracts'
        className={({ isActive }) =>
          `mb-1 flex cursor-pointer ${isActive ? 'rounded-xl bg-white shadow-sidebars' : 'bg-transparent'} p-2`
        }>
        {({ isActive }) => (
          <div className='flex h-full w-full items-center justify-between'>
            <div className='flex items-center'>
              <div
                className={`mr-3 flex h-10 w-10 items-center justify-center rounded-xl ${isActive ? 'bg-mint' : 'bg-white shadow-sidebars'}`}>
                <File className='h-5 w-5' fill={isActive ? 'white' : '#4FD1C5'} />
              </div>
              <p
                className={`text-17 ${isActive ? 'font-bold text-text_black' : 'font-bold text-text_grey'}`}>
                계약
              </p>
            </div>
            <UpArrow className='mr-3 h-4 w-4' rotation={isActive ? 0 : 180} />
          </div>
        )}
      </NavLink>

      <NavLink
        to='billings'
        className={({ isActive }) =>
          `mb-1 flex cursor-pointer ${isActive ? 'rounded-xl bg-white shadow-sidebars' : 'bg-transparent'} p-2`
        }>
        {({ isActive }) => (
          <div className='flex h-full w-full items-center justify-between'>
            <div className='flex items-center'>
              <div
                className={`mr-3 flex h-10 w-10 items-center justify-center rounded-xl ${isActive ? 'bg-mint' : 'bg-white shadow-sidebars'}`}>
                <Card className='h-5 w-5' fill={isActive ? 'white' : '#4FD1C5'} />
              </div>
              <p
                className={`text-17 ${isActive ? 'font-bold text-text_black' : 'font-bold text-text_grey'}`}>
                청구
              </p>
            </div>
            <UpArrow className='mr-3 h-4 w-4' rotation={isActive ? 0 : 180} />
          </div>
        )}
      </NavLink>

      <NavLink
        to='products'
        className={({ isActive }) =>
          `mb-1 flex cursor-pointer ${isActive ? 'rounded-xl bg-white shadow-sidebars' : 'bg-transparent'} p-2`
        }>
        {({ isActive }) => (
          <div className='flex h-full w-full items-center justify-between'>
            <div className='flex items-center'>
              <div
                className={`mr-3 flex h-10 w-10 items-center justify-center rounded-xl ${isActive ? 'bg-mint' : 'bg-white shadow-sidebars'}`}>
                <Item
                  className='h-5 w-5'
                  stroke={isActive ? '#ffffff' : '#4FD1C5'}
                  fill={isActive ? '#4FD1C5' : '#ffffff'}
                />
              </div>
              <p
                className={`text-17 ${isActive ? 'font-bold text-text_black' : 'font-bold text-text_grey'}`}>
                상품
              </p>
            </div>
            <UpArrow className='mr-3 h-4 w-4' rotation={isActive ? 0 : 180} />
          </div>
        )}
      </NavLink>

      <NavLink
        to='setting/simpconsent'
        className={({ isActive }) =>
          `mb-1 flex cursor-pointer ${isActive ? 'rounded-xl bg-white shadow-sidebars' : 'bg-transparent'} p-2`
        }>
        {({ isActive }) => (
          <div className='flex h-full w-full items-center justify-between'>
            <div className='flex items-center'>
              <div
                className={`mr-3 flex h-10 w-10 items-center justify-center rounded-xl ${isActive ? 'bg-mint' : 'bg-white shadow-sidebars'}`}>
                <Setting className='h-4 w-4' fill={isActive ? 'white' : '#4FD1C5'} />
              </div>
              <p
                className={`text-17 ${isActive ? 'font-bold text-text_black' : 'font-bold text-text_grey'}`}>
                설정
              </p>
            </div>
            <UpArrow className='mr-3 h-4 w-4' rotation={isActive ? 0 : 180} />
          </div>
        )}
      </NavLink>
    </div>
  );
};

export default SideBar;
