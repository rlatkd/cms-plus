const Input = ({ label, required, className, isValid, errorMsg, ...props }) => (
  <label className={`block ${className}`}>
    <span
      className={`block text-sm font-medium text-slate-700 ${
        required ? "after:ml-0.5 after:text-red-500 after:content-['*']" : ''
      }`}
    >
      {label}
    </span>
    <input
      className={`mt-1 w-full rounded-md border ${
        isValid === false
          ? 'border-red-500 focus:border-red-500 focus:ring-red-500'
          : 'border-ipt_border focus:border-mint focus:ring-mint'
      } bg-white px-3 py-2 text-sm placeholder-slate-400 shadow-sm placeholder:text-sm focus:outline-none focus:ring-1 sm:text-sm`}
      {...props}
    />
    {isValid === false && errorMsg && (
      <p className="mt-1 text-xs text-red-500">{errorMsg}</p>
    )}
  </label>
);

export default Input;