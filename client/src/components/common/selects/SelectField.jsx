import Arrow from '@/assets/Arrow';

const SelectField = ({
  label,
  required,
  options,
  classContainer,
  classLabel = '',
  classSelect = '',
  ...props
}) => {
  return (
    <div className={`${classContainer} mb-4`}>
      {label && (
        <label
          className={`${classLabel} block text-sm font-400 text-slate-700 mb-2
                    ${required ? "after:ml-1 after:text-red-500 after:content-['*']" : ''}`}>
          {label}
        </label>
      )}

      <div className='relative'>
        <select
          className={`${classSelect} ${classSelect.includes('rounded-lg') ? 'rounded-lg' : 'rounded-md '}  
          w-full appearance-none border border-ipt_border bg-white p-2 pr-10 text-sm focus:border-mint focus:outline-none focus:ring-mint`}
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

        <div className='pointer-events-none absolute inset-y-0 right-0 flex items-center px-2'>
          <Arrow fill='#C7CCD0' />
        </div>
      </div>
    </div>
  );
};

export default SelectField;
