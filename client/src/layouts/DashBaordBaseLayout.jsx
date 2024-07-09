import Header from './Header';
import SideBar from './SideBar';

const DashBaordBaseLayout = ({ children }) => {
  return (
    <div className='flex h-full w-full max-w-2400 bg-background'>
      <SideBar />
      <div className='flex h-full w-full flex-col'>
        <Header />
        <main className='flex h-full flex-col overflow-auto p-8 px-10'>{children}</main>
      </div>
    </div>
  );
};

export default DashBaordBaseLayout;
