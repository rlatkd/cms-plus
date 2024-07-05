const RadioGroup = ({ label, name, options, selectedOption, onChange }) => {
  return (
    <div className='mb-4'>
      <label className='block text-gray-700 text-sm font-bold mb-2'>
        <span className='text-essential'>*</span>
        {label}
      </label>
      <div className='flex items-center'>
        {options.map(option => (
          <label key={option.value} className='mr-4 flex items-center'>
            <input
              type='radio'
              name={name}
              value={option.value}
              checked={selectedOption === option.value}
              onChange={() => onChange(option.value)}
              className='mr-2'
            />
            {option.label}
          </label>
        ))}
      </div>
    </div>
  );
};

export default RadioGroup;
