const RadioGroup = ({
  label,
  name,
  options,
  classLabel,
  selectedOption,
  onChange,
  required = false,
}) => {
  return (
    <div className='mb-4'>
      <label className={`${classLabel} mb-2 block `}>
        <span
          className={`block text-15 font-700 text-text_black ${
            required ? "after:ml-0.5 after:text-red-500 after:content-['*']" : ''
          }`}>
          {label}
        </span>
      </label>
      <div className='flex items-center'>
        {options.map(option => (
          <label key={option.value} className='mr-4 flex items-center cursor-pointer'>
            <div className='relative flex items-center'>
              <input
                type='radio'
                name={name}
                value={option.value}
                checked={selectedOption === option.value}
                onChange={() => onChange(option.value)}
                className='sr-only'
              />
              <div
                className={`mr-2 flex h-4 w-4 items-center justify-center rounded-full border bg-white ${selectedOption === option.value ? 'border-teal-400' : 'border-gray-300'}`}>
                {selectedOption === option.value && (
                  <div className='h-2 w-2 rounded-full bg-teal-400' />
                )}
              </div>
              <span className='text-sm text-text_black'>{option.label}</span>
            </div>
          </label>
        ))}
      </div>
    </div>
  );
};

export default RadioGroup;
