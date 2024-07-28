import React, { useState, useEffect } from 'react';

const ThemeSwitcher = () => {
  const [theme, setTheme] = useState('mylight');

  useEffect(() => {
    document.documentElement.setAttribute('data-theme', theme);
  }, [theme]);

  const handleThemeChange = newTheme => {
    setTheme(newTheme);
  };

  return (
    <div>
      <select
        value={theme}
        onChange={e => handleThemeChange(e.target.value)}
        className='select select-bordered'>
        <option value='mylight'>Light</option>
        <option value='mydark'>Dark</option>
      </select>
    </div>
  );
};

export default ThemeSwitcher;
