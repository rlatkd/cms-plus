const Input = ({ label, required, ...props }) => (
  <label className='block'>
    <span
      className={`block text-sm font-medium text-slate-700 ${required ? "after:content-['*'] after:ml-0.5 after:text-red-500" : ''}`}>
      {label}
    </span>
    <input
      className='text-sm mt-1 px-3 py-2 bg-white border shadow-sm border-slate-300 placeholder-slate-400 focus:outline-none focus:border-mint focus:ring-mint block w-full rounded-md sm:text-sm focus:ring-1 placeholder:text-sm'
      {...props}
    />
  </label>
);

export default Input;
