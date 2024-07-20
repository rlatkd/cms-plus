import Header from './Header';
import SideBar from './SideBar';

const DashBaordBaseLayout = ({ children }) => {
  return (
    <div className='flex w-full max-w-2400 max-h-1500 bg-background relative'>
      <SideBar />
      <div className='flex w-full flex-col overflow-auto'>
        <Header />
        <main className='flex h-full desktop:h-[89vh] flex-col px-8 pt-4 pb-7 desktop:pl-6  '>
          {children}
        </main>
      </div>
    </div>
  );
};

export default DashBaordBaseLayout;
