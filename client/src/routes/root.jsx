import { Navigate, createBrowserRouter } from 'react-router-dom';
import vendorRoute from '@/routes/vendorRoute';
import memberRoute from '@/routes/memberRoute';
import Login from '@/pages/LoginPage'; // 로그인
import Signup from '@/pages/SignupPage'; // 회원가입
import Vendor from '@/pages/vendor/VenIndex'; // 고객 중첩 라우팅
import Member from '@/pages/member/MemIndex'; // 회원 중첩 라우팅
import Test from '@/labs/Test';
import NotFoundPage from '@/pages/utils/error/NotFoundPage';
import InternalServerErrorPage from '@/pages/utils/error/InternalServerErrorPage';
import ForbiddenErrorPage from '@/pages/utils/error/ForbiddenErrorPage';
import ChartTest from '@/labs/ChartTest';

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
    element: <Vendor />,
    children: vendorRoute(),
  },
  {
    path: 'member',
    element: <Member />,
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
  {
    path: 'chart',
    element: <ChartTest />,
  },
]);

export default root;
