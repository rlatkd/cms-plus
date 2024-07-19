import InputWeb from '@/components/common/inputs/InputWeb';
import ProductSelectField from '@/components/common/selects/ProductSelectField';
import Table from '@/components/common/tables/Table';

const cols = [
  { key: 'order', label: 'No.', width: 'w-1/12' },
  { key: 'productName', label: '상품', width: 'w-2/12' },
  { key: 'productPrice', label: '총 금액', width: 'w-2/12' },
  { key: 'totalPrice', label: '금액', width: 'w-2/12' },
  { key: 'productQuantity', label: '수량', width: 'w-2/12' },
];

const BillingDetailProduct = () => {
  const billingProducts = [];
  const handleProductChange = () => {};

  return (
    <>
      <div className='mx-4'>
        <label className={`block text-text_black text-15 font-700 mb-2 ml-2`}>상품 추가</label>
        <div className='flex justify-between'>
          <ProductSelectField
            onChange={handleProductChange}
            label='상품을 선택해주세요.'
            options={[
              { value: 'JPA(5,000원)', label: 'JPA(5,000원)' },
              { value: 'JPA2(3,000원)', label: 'JPA2(3,000원)' },
              { value: 'JPA3(3,000원)', label: 'JPA3(3,000원)' },
              { value: 'JPA4(3,000원)', label: 'JPA4(3,000원)' },
              { value: 'JPA5(3,000원)', label: 'JPA5(3,000원)' },
              { value: 'JPA6(3,000원)', label: 'JPA6(3,000원)' },
              { value: 'JPA7(3,000원)', label: 'JPA7(3,000원)' },
            ]}
            selectedOptions={billingProducts}
            className='h-13 w-64 rounded-md border border-gray-300 bg-white p-4 pr-10 text-base focus:border-teal-400 focus:outline-none focus:ring-teal-400'
          />
          <InputWeb 
            id='billingTotal' 
            label='' 
            placeholder={`12345원`} 
            type='text' 
            disabled={true}
            labelEnabled={false}
            classInput='text-right'
            />
        </div>
      </div>
      <div className='flex flex-col mb-5 pb-3'>
        <div className='flex flex-col h-full justify-between pt-5 px-5 '>
          <Table cols={cols} rows={billingProducts} currentPage={1} />
        </div>
      </div>
    </>
  );
};

export default BillingDetailProduct;
