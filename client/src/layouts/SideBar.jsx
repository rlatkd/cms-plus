import { useNavigate } from 'react-router-dom';

const SideBar = () => {
  const navigate = useNavigate();

  const handleNavigation = path => {
    navigate(path);
  };

  return (
    <div
      className='hidden h-full w-96 animate-slideOut p-0 desktop:block desktop:animate-slideIn desktop:p-6'
      style={{ borderRight: '1px solid blue' }}>
      <h1 className='text-xl font-bold text-text_black'>HYOSUNG CMS#</h1>
      <div className='border-gradient mb-8 border-b-2' />
      <div
        className='mb-3 flex cursor-pointer flex-col rounded-lg p-3 shadow-dash-board'
        onClick={() => handleNavigation('dashboard')}>
        대시보드
      </div>
      <div
        className='mb-3 flex cursor-pointer flex-col rounded-lg p-3 shadow-dash-board'
        onClick={() => handleNavigation('members')}>
        회원
      </div>
      <div
        className='mb-3 flex cursor-pointer flex-col rounded-lg p-3 shadow-dash-board'
        onClick={() => handleNavigation('contracts')}>
        계약
      </div>
      <div
        className='mb-3 flex cursor-pointer flex-col rounded-lg p-3 shadow-dash-board'
        onClick={() => handleNavigation('billings')}>
        청구
      </div>
      <div
        className='mb-3 flex cursor-pointer flex-col rounded-lg p-3 shadow-dash-board'
        onClick={() => handleNavigation('products')}>
        상품
      </div>
      <div
        className='mb-3 flex cursor-pointer flex-col rounded-lg p-3 shadow-dash-board'
        onClick={() => handleNavigation('setting/simpconsent')}>
        설정
      </div>
    </div>
  );
};

export default SideBar;
