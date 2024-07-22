import vendorRoute from '@/routes/vendorRoute';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

const BreadCrumb = () => {
  const [currentRoutes, setCurrentRoutes] = useState({
    icon: '',
    name: [],
  });

  const location = useLocation();

  const currentPaths = location.pathname
    .split('/')
    .filter(path => path)
    .slice(1);

  // <--------헤더 아이콘, 상위메뉴 선택--------
  const breadCrumbTitle = () => {
    for (const route of vendorRoute()) {
      if (currentPaths[0] === route.path) {
        setCurrentRoutes({
          icon: route.icon,
          name: [route.name],
        });

        return route;
      }
    }
    return vendorRoute()[0];
  };

  // <--------헤더 하위메뉴 선택 (재귀)--------
  const breadCrumbMenu = (route, idx) => {
    if (!route.children) return;

    for (const child of route.children) {
      const isSinglePath = currentPaths.length === 1;
      const isMatchingPath = child.path.includes(currentPaths[idx]);

      if (isSinglePath || isMatchingPath) {
        setCurrentRoutes(prevState => ({
          ...prevState,
          name: [...prevState.name, child.name],
        }));

        if (!isSinglePath) {
          breadCrumbMenu(child, idx + 1);
        }
        return;
      }
    }
  };

  useEffect(() => {
    const route = breadCrumbTitle();
    breadCrumbMenu(route, 1);
  }, [location]);

  return (
    <div className='h-14 flex flex-col'>
      <div className='flex items-center text-text_grey text-15 mb-2'>
        <div className='mr-3'>{currentRoutes.icon}</div>
        {currentRoutes.name.map((name, idx) => (
          <div className='flex' key={idx}>
            <p className='mr-3'>/</p>
            <p className={`mr-3 font-700 ${idx > 0 && 'text-text_black'}`}>{name}</p>
          </div>
        ))}
      </div>
      <p className='text-text_black text-xl font-800'>
        {currentRoutes.name[currentRoutes.name.length - 1]}
      </p>
    </div>
  );
};

export default BreadCrumb;
