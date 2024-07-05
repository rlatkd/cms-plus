import { Navigate } from 'react-router-dom';
import { lazy } from 'react';
import LazyComponentWrapper from '@/pages/utils/LazyComponentWrapper';

const DashBoard = lazy(() => import('@/pages/vendor/DashBoardPage')); // 대시보드
const MemberList = lazy(() => import('@/pages/vendor/Member/MemberListPage')); // 회원목록
const MemberDetail = lazy(() => import('@/pages/vendor/Member/MemberDetailPage')); // 회원상세
const MemberRegister = lazy(() => import('@/pages/vendor/Member/MemberRegisterPage')); // 회원등록
const MemberUpdate = lazy(() => import('@/pages/vendor/Member/MemberUpdatePage')); // 회원수정
const ContractList = lazy(() => import('@/pages/vendor/Contract/ContractListPage')); // 계약목록
const ContractDetail = lazy(() => import('@/pages/vendor/Contract/ContractDetailPage')); // 계약상세
const ContractUpdate = lazy(() => import('@/pages/vendor/Contract/ContractUpdatePage')); // 계약수정
const BillingList = lazy(() => import('@/pages/vendor/Billing/BillingListPage')); // 청구목록
const BillingDetail = lazy(() => import('@/pages/vendor/Billing/BillingDetailPage')); // 청구상세
const ProductList = lazy(() => import('@/pages/vendor/Product/ProductListPage')); // 상품목록
const SettingSimpConsent = lazy(() => import('@/pages/vendor/Setting/SettingSimpConsentPage')); // 간편서명동의설정

const vendorRoute = () => {
  return [
    {
      path: '',
      element: <Navigate replace to='dashboard' />,
    },
    {
      path: 'dashboard',
      element: <LazyComponentWrapper component={DashBoard} />,
    },
    {
      path: 'members',
      element: <LazyComponentWrapper component={MemberList} />,
    },
    {
      path: 'members/detail/:id',
      element: <LazyComponentWrapper component={MemberDetail} />,
    },
    {
      path: 'members/register',
      element: <LazyComponentWrapper component={MemberRegister} />,
    },
    {
      path: 'members/update/:id',
      element: <LazyComponentWrapper component={MemberUpdate} />,
    },
    {
      path: 'contracts',
      element: <LazyComponentWrapper component={ContractList} />,
    },
    {
      path: 'contracts/detail/:id',
      element: <LazyComponentWrapper component={ContractDetail} />,
    },
    {
      path: 'contracts/update/:id',
      element: <LazyComponentWrapper component={ContractUpdate} />,
    },
    {
      path: 'billings',
      element: <LazyComponentWrapper component={BillingList} />,
    },
    {
      path: 'billings/detail/:id',
      element: <LazyComponentWrapper component={BillingDetail} />,
    },
    {
      path: 'products',
      element: <LazyComponentWrapper component={ProductList} />,
    },
    {
      path: 'setting/simpconsent',
      element: <LazyComponentWrapper component={SettingSimpConsent} />,
    },
  ];
};

export default vendorRoute;
