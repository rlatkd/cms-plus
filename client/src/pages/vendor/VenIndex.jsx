import { Outlet } from 'react-router-dom';
import BaseLayout from '@/layouts/DashBoardBaseLayout';

const VenIndex = () => {
  return (
    <BaseLayout>
      <Outlet />
    </BaseLayout>
  );
};

export default VenIndex;
