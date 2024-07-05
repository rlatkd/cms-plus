import { Outlet } from 'react-router-dom';
import BaseLayout from '@/layouts/MobileBaseLayout';

const indexPage = () => {
  return (
    <BaseLayout>
      <Outlet />
    </BaseLayout>
  );
};

export default indexPage;
