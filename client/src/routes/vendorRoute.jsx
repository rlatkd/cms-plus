import { Navigate } from 'react-router-dom';
import User from '@/assets/User';
import Home from '@/assets/Home';
import File from '@/assets/File';
import Card from '@/assets/Card';
import Item from '@/assets/Item';
import Setting from '@/assets/Setting';
import DashBoard from '@/pages/vendor/DashBoardPage';
import MemberIndex from '@/pages/vendor/member/MemberIndex';
import MemberList from '@/pages/vendor/member/MemberListPage';
import MemberDetail from '@/pages/vendor/member/MemberDetailPage';
import MemberRegister from '@/pages/vendor/member/MemberRegisterPage';
import MemberInfoUpdate from '@/pages/vendor/member/MemberInfoUpdatePage';
import ContractIndex from '@/pages/vendor/contract/ContractIndex';
import ContractList from '@/pages/vendor/contract/ContractListPage';
import ContractDetail from '@/pages/vendor/contract/ContractDetailPage';
import ProductInfoUpdate from '@/pages/vendor/contract/ProductInfoUpdatePage';
import BillingInfoUpdate from '@/pages/vendor/contract/BillingInfoUpdatePage';
import PaymentInfoUpdate from '@/pages/vendor/contract/PaymentInfoUpdatePage';
import BillingIndex from '@/pages/vendor/billing/BillingIndex';
import BillingList from '@/pages/vendor/billing/BillingListPage';
import BillingDetail from '@/pages/vendor/billing/BillingDetailPage';
import ProductIndex from '@/pages/vendor/product/ProductIndex';
import ProductList from '@/pages/vendor/product/ProductListPage';
import SettingIndex from '@/pages/vendor/setting/SettingIndex';
import SettingSimpConsent from '@/pages/vendor/setting/SettingSimpConsentPage';

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
      element: <DashBoard />,
    },
    {
      path: 'members',
      name: '회원',
      icon: <User />,
      element: <MemberIndex />,
      children: [
        {
          path: '',
          name: '회원 목록',
          menu: true,
          element: <MemberList />,
        },
        {
          path: 'detail/:id',
          name: '회원 상세 정보',
          menu: false,
          element: <MemberDetail />,
        },
        {
          path: 'register',
          name: '회원 등록',
          menu: false,
          element: <MemberRegister />,
        },
        {
          path: 'update/:id',
          name: '회원 정보 수정',
          menu: false,
          element: <MemberInfoUpdate />,
        },
      ],
    },
    {
      path: 'contracts',
      name: '계약',
      icon: <File />,
      element: <ContractIndex />,
      children: [
        {
          path: '',
          name: '계약 목록',
          menu: true,
          element: <ContractList />,
        },
        {
          path: 'detail/:id',
          name: '계약 상세 정보',
          menu: false,
          element: <ContractDetail />,
        },
        {
          path: 'Product/update/:id',
          name: '상품 정보 수정',
          menu: false,
          element: <ProductInfoUpdate />,
        },
        {
          path: 'payment/update/:id',
          name: '결제 정보 수정',
          menu: false,
          element: <PaymentInfoUpdate />,
        },
        {
          path: 'billings/update/:id',
          name: '청구 정보 수정',
          menu: false,
          element: <BillingInfoUpdate />,
        },
      ],
    },
    {
      path: 'billings',
      name: '청구',
      icon: <Card />,
      element: <BillingIndex />,
      children: [
        {
          path: '',
          name: '청구 목록',
          menu: true,
          element: <BillingList />,
        },
        {
          path: 'detail/:id',
          name: '청구 상세 정보',
          menu: false,
          element: <BillingDetail />,
        },
      ],
    },
    {
      path: 'products',
      name: '상품',
      icon: <Item />,
      element: <ProductIndex />,
      children: [
        {
          path: '',
          name: '상품 목록',
          menu: true,
          element: <ProductList />,
        },
      ],
    },
    {
      path: 'setting',
      name: '설정',
      icon: <Setting />,
      element: <SettingIndex />,
      children: [
        {
          path: '',
          name: '',
          menu: false,
          element: <Navigate to='simpconsent' replace />,
        },
        {
          path: 'simpconsent',
          name: '간편서명동의 설정',
          menu: true,
          element: <SettingSimpConsent />,
        },
      ],
    },
  ];
};

export default vendorRoute;
