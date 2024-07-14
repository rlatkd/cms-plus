import root from '@/routes/root';
import { RouterProvider } from 'react-router-dom';
import ConfirmDialog from '@/utils/dialog/confirm/ConfirmDialog';
import AlertDialog from './utils/dialog/alert/AlertDialog';
import AlertWidthDialog from './utils/dialog/alertwidth/AlertWidthDialog';

const App = () => {
  return (
    <div className='flex h-full w-full justify-center'>
      <AlertDialog>
        <AlertWidthDialog>
          <ConfirmDialog>
            <RouterProvider router={root} />
          </ConfirmDialog>
        </AlertWidthDialog>
      </AlertDialog>
    </div>
  );
};

export default App;
