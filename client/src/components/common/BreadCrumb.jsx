import vendorRoute from '@/routes/vendorRoute';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

const BreadCrumb = () => {
  const [currentRoute, setCurrentRoute] = useState({
    icon: '',
    name: [],
  });

  const location = useLocation();

  const currentPaths = location.pathname
    .split('/')
    .filter(path => path)
    .slice(1);

  const breadCrumbTitle = () => {
    for (const route of vendorRoute()) {
      if (currentPaths[0] === route.path) {
        setCurrentRoute({
          icon: route.icon,
          name: [route.name],
        });

        return route;
      }
    }
  };

  const breadCrumbMenu = route => {
    if (route.child) {
      setCurrentRoute({});
    }
  };

  const breadCrumbSetting = () => {
    for (const route of vendorRoute()) {
      if (currentPaths[1] === route.path) {
        setCurrentRoute({
          icon: route.icon,
          name: route.name,
          childName: route.name,
        });

        if (route.children) {
          if (!currentPaths[2]) {
            setCurrentRoute({
              icon: route.icon,
              name: route.name,
              childName: route.children[0].name,
            });
            return;
          } else {
            for (const child of route.children) {
              if (child.path.indexOf(currentPaths[2]) != -1) {
                setCurrentRoute({
                  icon: route.icon,
                  name: route.name,
                  childName: child.name,
                });
                return;
              }
            }
          }
        }
        return;
      }
    }
  };

  useEffect(() => {
    const route = breadCrumbTitle();
    breadCrumbSetting(route);
  }, [location]);

  return (
    <div className='h-14 flex flex-col'>
      <div className='flex items-center text-text_grey mb-1'>
        <div className='mr-3'>{currentRoute.icon}</div>

        <p className='mr-3'>/</p>
        <p className='mr-3'>{currentRoute.name}</p>
        <p className='mr-3'>/</p>
        <p className='text-text_black'>{currentRoute.childName}</p>
      </div>
      <p className='text-text_black text-xl font-800'>{currentRoute.childName}</p>
    </div>
  );
};

export default BreadCrumb;
