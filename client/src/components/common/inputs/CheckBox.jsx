import { Switch } from '@headlessui/react';
import { AiOutlineCheck } from 'react-icons/ai';

const Checkbox = ({ label, name, classBox, checked, onChange, disabled }) => {
  return (
    <Switch.Group>
      <div className='flex items-center'>
        <Switch
          checked={checked}
          onChange={e => onChange(e)}
          name={name}
          disabled={disabled}
          onClick={e => e.stopPropagation()}
          className={`${classBox ? `${classBox}` : 'h-5 w-5 rounded'} relative flex items-center justify-center outline-none ring-1 transition-all duration-200 
              ${!checked && !disabled ? 'bg-white ring-gray-400 ' : ''} 
              ${checked && !disabled ? 'bg-teal-400 ring-teal-400' : ''}
              ${disabled ? 'bg-gray-200 ring-gray-200' : ''} `}>
          <AiOutlineCheck
            size='0.75rem'
            className={`transition-transform duration-200 ease-out
              ${checked ? 'scale-100' : 'scale-0'} 
              ${checked && !disabled ? 'text-white' : 'text-gray-400'} `}
          />
        </Switch>
        <Switch.Label className='text-text_black ml-2 cursor-pointer'>{label}</Switch.Label>
      </div>
    </Switch.Group>
  );
};

export default Checkbox;
