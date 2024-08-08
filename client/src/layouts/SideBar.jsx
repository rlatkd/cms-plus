import { NavLink, useNavigate } from 'react-router-dom';
import vendorRoute from '@/routes/vendorRoute';
import React from 'react';
import { useSideBarActiveStore } from '@/stores/useSideBarActiveStore';
import cmslogo from '@/assets/cmslogo.svg';

const SideBar = () => {
  const navigate = useNavigate();

  const { toggle } = useSideBarActiveStore();

  return (
    <div className='hidden h-full w-72  py-6 pl-4 mr-4 sticky desktop:block desktop:animate-slideIn '>
      <div
        className='mb-7 ml-3 mt-3 flex items-center cursor-pointer'
        onClick={() => navigate('/vendor/dashboard')}>
        <img src={cmslogo} alt='logo' className='mx-2 h-8 w-8' />
        <h1 className='font-GeekbleMalang text-xl text-text_black mb-1'>CMS.Plus</h1>
      </div>
      <div className='border-gradient mb-5 border-b-2' />
      {vendorRoute().map((route, idx) => {
        return (
          route.path && (
            <div key={idx} onClick={() => toggle(route.path)}>
              <NavLink
                to={route.path}
                className={({ isActive }) =>
                  ` ${isActive && ' bg-white shadow-sidebars'} flex rounded-xl cursor-pointer mb-2 px-3 py-2`
                }>
                {({ isActive }) => (
                  <div className='flex h-full w-full items-center justify-between'>
                    <div className='flex items-center'>
                      <div
                        className={`mr-4 flex h-9 w-9 items-center justify-center rounded-xl ${isActive ? 'bg-mint' : 'bg-white shadow-sidebars'}`}>
                        {React.cloneElement(route.icon, {
                          className: 'h-6 w-4',
                          fill:
                            route.path === 'products'
                              ? isActive
                                ? '#4FD1C5'
                                : '#ffffff'
                              : isActive
                                ? '#ffffff'
                                : '#4FD1C5',

                          stroke:
                            route.path === 'products' ? (isActive ? '#ffffff' : '#4FD1C5') : ' ',
                        })}
                      </div>
                      <p
                        className={`text-15 font-800 ${isActive ? ' text-text_black' : ' text-text_grey'}`}>
                        {route.name}
                      </p>
                    </div>
                  </div>
                )}
              </NavLink>
            </div>
          )
        );
      })}
    </div>
  );
};

export default SideBar;
