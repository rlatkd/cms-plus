import { Outlet } from 'react-router-dom';
import BaseLayout from '@/layouts/MobileBaseLayout';

const MemIndex = () => {
  return (
    <BaseLayout>
      <Outlet />
    </BaseLayout>
  );
};

export default MemIndex;
