const AddressInput = () => (
    <div className='block'>
      <span className='block text-sm font-medium text-slate-700 mb-1'>주소</span>
      <div className='flex space-x-2 mb-2'>
        <input
          type='text'
          name='zipcode'
          className='flex-grow px-3 py-2 bg-white border shadow-sm border-slate-300 placeholder-slate-400 focus:outline-none focus:border-mint focus:ring-mint rounded-md sm:text-sm focus:ring-1 placeholder:text-sm'
          placeholder='우편번호'
        />
        <button className='px-4 py-2 bg-gray-200 rounded-md'>🔍</button>
      </div>
      <input
        type='text'
        name='address'
        className='w-full px-3 py-2 bg-white border shadow-sm border-slate-300 placeholder-slate-400 focus:outline-none focus:border-mint focus:ring-mint rounded-md sm:text-sm focus:ring-1 mb-2 placeholder:text-sm'
        placeholder='주소'
      />
      <input
        type='text'
        name='address_detail'
        className='w-full px-3 py-2 bg-white border shadow-sm border-slate-300 placeholder-slate-400 focus:outline-none focus:border-mint focus:ring-mint rounded-md sm:text-sm focus:ring-1 placeholder:text-sm'
        placeholder='상세 주소'
      />
    </div>
  );
  
  export default AddressInput;
  