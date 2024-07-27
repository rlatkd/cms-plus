import { useEffect, useRef, useState } from 'react';
import Checkbox from '@/components/common/inputs/CheckBox';
import Arrow from '@/assets/Arrow';

const ProductSelectFieldcopy = ({
  label,
  placeholder,
  required,
  options,
  onChange,
  selectedOptions,
  classContainer,
  classButton,
  ...props
}) => {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const dropdownRef = useRef(null);

  // <----- 검색 필터링 ----->
  const filteredOptions = options.filter(option =>
    option.label.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // <-----
  const toggleOption = product => {
    const newSelectedOptions = selectedOptions.find(p => p.productId === product.productId)
      ? selectedOptions.filter(p => p.productId !== product.productId)
      : [{ ...product, quantity: 1 }, ...selectedOptions];
    onChange(newSelectedOptions);
  };

  const handleClickOutside = event => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div className={`${classContainer} relative`} ref={dropdownRef}>
      <label
        className={`block text-text_black text-15 font-700 mb-2 ml-2 
                  ${required ? "after:ml-1 after:text-red-500 after:content-['*']" : ''}`}>
        {label}
      </label>
      <button
        onClick={() => setIsOpen(!isOpen)}
        className={`${classButton} text-text_grey border border-ipt_border focus:ring-1 focus:outline-none focus:ring-mint focus:border-mint_hover font-400 rounded-lg text-sm p-4 text-center flex items-center justify-between`}
        type='button'>
        {placeholder}
        <Arrow fill='#C7CCD0' />
      </button>

      {isOpen && (
        <div className='z-10 absolute mt-1 bg-white rounded-lg shadow w-80 p-3'>
          <div>
            <input
              type='text'
              className='w-full p-2 ps-5 mb-3 text-sm text-text_black border border-gray-300 rounded-lg bg-gray-50 focus:ring-1 focus:outline-none focus:ring-mint focus:border-mint_hover'
              placeholder='검색'
              value={searchTerm}
              onChange={e => setSearchTerm(e.target.value)}
            />
          </div>
          <ul className=' h-48 pb-3 overflow-y-auto text-sm text-gray-700 scrollbar-custom'>
            {filteredOptions.map((option, index) => (
              <li key={index}>
                <div className='flex items-center font-400 py-2 px-1 hover:bg-gray-100'>
                  <Checkbox
                    name={`item${index}`}
                    label={option.value}
                    checked={
                      selectedOptions.find(p => p.productId === option.productId) !== undefined
                    }
                    onChange={() => toggleOption(option)}
                  />
                </div>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default ProductSelectFieldcopy;
