import React, { useEffect } from 'react';
import { useAddressStore } from '@/stores/useAddressStore';
import { useUserDataStore } from '@/stores/useUserDataStore';

const AddressInput = () => {
  const { zipcode, address, addressDetail, setZipcode, setAddress, setAddressDetail } =
    useAddressStore();
  const { setUserData } = useUserDataStore();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
    script.async = true;
    document.head.appendChild(script);
    return () => {
      document.head.removeChild(script);
    };
  }, []);

  const handleAddressSearch = () => {
    new window.daum.Postcode({
      oncomplete: function (data) {
        console.log('Selected address:', data);
        setZipcode(data.zonecode);
        setAddress(data.address);
        // 주소 정보를 useUserDataStore에도 저장
        setUserData({
          memberDTO: {
            zipcode: data.zonecode,
            address: data.address,
          },
        });
        document.querySelector('input[name=address_detail]').focus();
      },
      width: '100%',
      height: '100%',
    }).open();
  };

  const handleAddressDetailChange = e => {
    const newAddressDetail = e.target.value;
    setAddressDetail(newAddressDetail);
    // 주소 상세 정보를 useUserDataStore에도 저장
    setUserData({
      memberDTO: {
        addressDetail: newAddressDetail,
      },
    });
  };

  return (
    <div className='block'>
      <span className='mb-1 block text-sm font-medium text-slate-700'>주소</span>
      <div className='mb-2 flex'>
        <input
          type='text'
          name='zipcode'
          value={zipcode}
          className='flex-grow min-w-0 text-sm rounded-md border border-slate-300 bg-white px-3 py-2 placeholder-slate-400 shadow-sm placeholder:text-sm focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm h-10'
          placeholder='우편번호'
          readOnly
          autoComplete='postal-code'
        />
        <button
          onClick={handleAddressSearch}
          className='rounded-md ml-1 bg-gray-200 px-4 h-10 flex items-center justify-center'
          type='button'>
          🔍
        </button>
      </div>
      <input
        type='text'
        name='address'
        value={address}
        className='mb-2 w-full text-sm  rounded-md border border-slate-300 bg-white px-3 py-2 placeholder-slate-400 shadow-sm placeholder:text-sm focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm'
        placeholder='주소'
        readOnly
        autoComplete='street-address'
      />
      <input
        type='text'
        name='address_detail'
        value={addressDetail}
        onChange={handleAddressDetailChange}
        className='w-full text-sm rounded-md border border-slate-300 bg-white px-3 py-2 placeholder-slate-400 shadow-sm placeholder:text-sm focus:border-mint focus:outline-none focus:ring-1 focus:ring-mint sm:text-sm'
        placeholder='상세 주소'
        autoComplete='address-line2'
      />
    </div>
  );
};

export default AddressInput;
