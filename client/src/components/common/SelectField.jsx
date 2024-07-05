import React from 'react';

const SelectField = ({ label, required, options, ...props }) => (
  <div className='mb-4'>
    <label className='block text-sm font-medium text-gray-700 mb-1'>
      {required && <span className='text-red-500'>*</span>} {label}
    </label>
    <div className='relative'>
      <select
        className='w-full p-2 pr-8 border border-gray-300 rounded-md text-sm appearance-none bg-white focus:outline-none focus:border-mint focus:ring-mint'
        style={{
          WebkitAppearance: 'none',
          MozAppearance: 'none',
          appearance: 'none',
        }}
        {...props}>
        {options.map((option, index) => (
          <option key={index} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
      <div className='pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700'></div>
    </div>
  </div>
);

export default SelectField;
