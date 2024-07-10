import { Navigate } from 'react-router-dom';
import { lazy } from 'react';
import LazyComponentWrapper from '@/pages/utils/LazyComponentWrapper';
import User from '@/assets/User';
import Home from '@/assets/Home';
import File from '@/assets/File';
import Card from '@/assets/Card';
import Item from '@/assets/Item';
import Setting from '@/assets/Setting';

const DashBoard = lazy(() => import('@/pages/vendor/DashBoardPage')); // 대시보드

const MemberIndex = lazy(() => import('@/pages/vendor/member/MemberIndexPage')); // 회원
const MemberList = lazy(() => import('@/pages/vendor/member/MemberListPage')); // 회원목록
const MemberDetail = lazy(() => import('@/pages/vendor/member/MemberDetailPage')); // 회원상세
const MemberRegister = lazy(() => import('@/pages/vendor/member/MemberRegisterPage')); // 회원등록
const MemberIngoUpdate = lazy(() => import('@/pages/vendor/member/MemberInfoUpdatePage')); // 회원수정

const ContractIndex = lazy(() => import('@/pages/vendor/contract/ContractIndexPage')); // 계약
const ContractList = lazy(() => import('@/pages/vendor/contract/ContractListPage')); // 계약목록
const ContractDetail = lazy(() => import('@/pages/vendor/contract/ContractDetailPage')); // 계약상세
const ProductInfoUpdate = lazy(() => import('@/pages/vendor/contract/ProductInfoUpdatePage')); // 상품수정
const BillingInfoUpdate = lazy(() => import('@/pages/vendor/contract/BillingInfoUpdatePage')); // 청구수정
const PaymentInfoUpdate = lazy(() => import('@/pages/vendor/contract/PaymentInfoUpdatePage')); // 결제수정

const BillingtIndex = lazy(() => import('@/pages/vendor/billing/BillingIndexPage')); // 청구
const BillingList = lazy(() => import('@/pages/vendor/billing/BillingListPage')); // 청구목록
const BillingDetail = lazy(() => import('@/pages/vendor/billing/BillingDetailPage')); // 청구상세

const ProductList = lazy(() => import('@/pages/vendor/product/ProductListPage')); // 상품목록
const SettingSimpConsent = lazy(() => import('@/pages/vendor/setting/SettingSimpConsentPage')); // 간편서명동의설정

const vendorRoute = () => {
  return [
    {
      path: '',
      element: <Navigate replace to='dashboard' />,
    },
    {
      path: 'dashboard',
      name: '대시보드',
      icon: <Home />,
      element: <LazyComponentWrapper component={DashBoard} />,
    },
    {
      path: 'members',
      name: '회원',
      icon: <User />,
      element: <LazyComponentWrapper component={MemberIndex} />,
      children: [
        {
          path: '',
          element: <LazyComponentWrapper component={MemberList} />,
        },
        {
          path: 'detail/:id',
          element: <LazyComponentWrapper component={MemberDetail} />,
        },
        {
          path: 'register',
          element: <LazyComponentWrapper component={MemberRegister} />,
        },
        {
          path: 'update/:id',
          element: <LazyComponentWrapper component={MemberIngoUpdate} />,
        },
      ],
    },
    {
      path: 'contracts',
      name: '계약',
      icon: <File />,
      element: <LazyComponentWrapper component={ContractIndex} />,
      children: [
        {
          path: '',
          element: <LazyComponentWrapper component={ContractList} />,
        },
        {
          path: 'detail/:id',
          element: <LazyComponentWrapper component={ContractDetail} />,
        },
        {
          path: 'Product/update/:id',
          element: <LazyComponentWrapper component={ProductInfoUpdate} />,
        },
        {
          path: 'payment/update/:id',
          element: <LazyComponentWrapper component={PaymentInfoUpdate} />,
        },
        {
          path: 'billings/update/:id',
          element: <LazyComponentWrapper component={BillingInfoUpdate} />,
        },
      ],
    },
    {
      path: 'billings',
      name: '청구',
      icon: <Card />,
      element: <LazyComponentWrapper component={BillingtIndex} />,
      children: [
        {
          path: '',
          element: <LazyComponentWrapper component={BillingList} />,
        },
        {
          path: 'detail/:id',
          element: <LazyComponentWrapper component={BillingDetail} />,
        },
      ],
    },
    {
      path: 'products',
      name: '상품',
      icon: <Item />,
      element: <LazyComponentWrapper component={ProductList} />,
    },
    {
      path: 'setting/simpconsent',
      name: '설정',
      icon: <Setting />,
      element: <LazyComponentWrapper component={SettingSimpConsent} />,
    },
  ];
};

export default vendorRoute;
