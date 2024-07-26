import { Navigate, createBrowserRouter } from 'react-router-dom';
import vendorRoute from './vendorRoute';
import memberRoute from './memberRoute';
import Login from '@/pages/LoginPage'; // 로그인
import Signup from '@/pages/SignupPage'; // 회원가입
import VendorIndex from '@/pages/vendor/VenIndexPage'; // 고객 중첩 라우팅
import MemberIndex from '@/pages/member/MemIndexPage'; // 회원 중첩 라우팅
import Test from '@/labs/Test';
import NotFoundPage from '@/pages/utils/error/NotFoundPage';
import InternalServerErrorPage from '@/pages/utils/error/InternalServerErrorPage';
import ForbiddenErrorPage from '@/pages/utils/error/ForbiddenErrorPage';

const root = createBrowserRouter([
  {
    path: '/',
    element: <Navigate replace to='login' />,
  },
  {
    path: 'login',
    element: <Login />,
  },
  {
    path: 'signup',
    element: <Signup />,
  },
  {
    path: 'vendor',
    element: <VendorIndex />,
    children: vendorRoute(),
  },
  {
    path: 'member',
    element: <MemberIndex />,
    children: memberRoute(),
  },
  {
    path: 'test',
    element: <Test />,
  },
  {
    path: 'error/notfound',
    element: <NotFoundPage />,
  },
  {
    path: 'error/interal',
    element: <InternalServerErrorPage />,
  },
  {
    path: 'error/forbidden',
    element: <ForbiddenErrorPage />,
  },
]);

export default root;
