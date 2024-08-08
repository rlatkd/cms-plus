import root from '@/routes/root';
import { RouterProvider } from 'react-router-dom';
import ConfirmDialog from '@/utils/dialog/confirm/ConfirmDialog';
import AlertDialog from './utils/dialog/alert/AlertDialog';

const App = () => {
  
  return (
    <div className='flex h-dvh w-vw justify-center'>
      <AlertDialog>
        <ConfirmDialog>
          <RouterProvider router={root} />
        </ConfirmDialog>
      </AlertDialog>
    </div>
  );
};

export default App;
