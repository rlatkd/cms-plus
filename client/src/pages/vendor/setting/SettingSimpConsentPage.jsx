import SimpConsentQrUrlModal from '@/components/vendor/modal/SimpConsentQrUrlModal';
import { useState, useEffect } from 'react';
import ProductSelectField from '@/components/common/selects/ProductSelectField';
import Checkbox from '@/components/common/inputs/CheckBox';
import useSimpleConsentStore from '@/stores/simpleConsentStore';
import { getSimpleConsent, getAllProducts, updateSimpleConsent } from '@/apis/simpleConsent';

const SettingSimpConsentPage = () => {
  const [isShowModal, setIsShowModal] = useState(false);
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [simpleConsentId, setSimpleConsentId] = useState(null);
  const [vendorUsername, setVendorUsername] = useState('');

  const { selectedProducts, setSelectedProducts, checkedItems, setCheckedItems } =
    useSimpleConsentStore();

  useEffect(() => {
    const fetchSimpleConsentAndProducts = async () => {
      setIsLoading(true);
      try {
        const simpleConsentData = await getSimpleConsent();
        setSimpleConsentId(simpleConsentData.id);
        setVendorUsername(simpleConsentData.vendorUsername);

        const newCheckedItems = {
          '실시간 CMS': false,
          카드: false,
        };
        simpleConsentData.paymentMethods.forEach(method => {
          if (method.code === 'CMS') newCheckedItems['실시간 CMS'] = true;
          if (method.code === 'CARD') newCheckedItems['카드'] = true;
        });
        setCheckedItems(newCheckedItems);

        const allProducts = await getAllProducts();
        setProducts(allProducts);

        const selectedProductsData = allProducts.filter(product =>
          simpleConsentData.productIds.includes(product.productId)
        );
        setSelectedProducts(selectedProductsData);
      } catch (error) {
        console.error('데이터 가져오기 오류:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchSimpleConsentAndProducts();
  }, [setCheckedItems, setSelectedProducts]);

  const handleProductChange = newSelectedOptions => {
    setSelectedProducts(newSelectedOptions);
  };

  const handleRemoveProduct = product => {
    setSelectedProducts(selectedProducts.filter(p => p !== product));
  };

  const handleCheckboxChange = name => {
    setCheckedItems({
      ...checkedItems,
      [name]: !checkedItems[name],
    });
  };

  const handleSave = async () => {
    try {
      const paymentMethods = [];
      if (checkedItems['카드']) {
        paymentMethods.push('CARD');
      }
      if (checkedItems['실시간 CMS']) {
        paymentMethods.push('CMS');
      }

      const productIds = selectedProducts.map(product => product.productId);

      const updateData = {
        paymentMethods: paymentMethods,
        productIds: productIds,
      };

      await updateSimpleConsent(updateData);
      alert('설정이 저장되었습니다.');
    } catch (error) {
      console.error('설정 저장 실패:', error);
      alert('설정 저장에 실패했습니다.');
    }
  };

  return (
    <div className='primary-dashboard relative h-full w-full '>
      <div className='mx-8 my-8 flex h-[90%] flex-col '>
        <h2 className='mb-8 text-xl font-bold'>간편서명동의 설정</h2>

        <div className='flex flex-grow flex-col justify-between'>
          <div>
            <div className='mb-6'>
              <h3 className='mb-3 ml-1 text-lg font-medium'>
                상품<span className='ml-1 text-red-500'>*</span>
              </h3>
              <div className='flex items-center'>
                {isLoading ? (
                  <div>상품 목록을 불러오는 중...</div>
                ) : (
                  <ProductSelectField
                    onChange={handleProductChange}
                    label='상품을 선택해주세요.'
                    required={true}
                    options={products.map(product => ({
                      value: product,
                      label: `${product.productName}(${product.productPrice.toLocaleString()}원)`,
                    }))}
                    selectedOptions={selectedProducts}
                    className='h-13 w-64 rounded-md border border-gray-300 bg-white p-4 pr-10 text-base focus:border-teal-400 focus:outline-none focus:ring-teal-400'
                  />
                )}
                <div className='ml-4 flex flex-wrap'>
                  {selectedProducts.map((product, index) => (
                    <span
                      key={index}
                      className='mb-2 mr-2 flex items-center justify-between rounded-full bg-teal-400 px-7 py-2 text-base text-white'
                      style={{ minWidth: '200px' }}>
                      <span className='flex-grow'>{product.productName}</span>
                      <button
                        onClick={() => handleRemoveProduct(product)}
                        className='ml-2 flex-shrink-0 text-white hover:text-gray-200'>
                        ×
                      </button>
                    </span>
                  ))}
                </div>
              </div>
            </div>
            <hr className='my-6 border-gray-400' />

            <div className='mb-6'>
              <h3 className='mb-4 ml-1 text-lg font-medium'>
                결제수단<span className='ml-1 text-red-500'>*</span>
              </h3>
              <div className='ml-1 flex items-center'>
                <Checkbox
                  name='실시간 CMS'
                  label='실시간 CMS'
                  checked={checkedItems['실시간 CMS']}
                  onChange={() => handleCheckboxChange('실시간 CMS')}
                />
                <div className='w-8' />
                <Checkbox
                  name='카드'
                  label='카드'
                  checked={checkedItems['카드']}
                  onChange={() => handleCheckboxChange('카드')}
                />
              </div>
            </div>
          </div>

          <div className='mt-auto flex justify-end pt-4'>
            <button
              className='mr-2 rounded-lg bg-teal-400 px-6 py-2 font-bold text-white'
              onClick={handleSave}>
              저장
            </button>
            <button
              className='rounded-lg border border-teal-400 px-6 py-2 font-bold text-teal-400'
              onClick={() => setIsShowModal(true)}>
              URL 확인
            </button>
          </div>
        </div>

        <SimpConsentQrUrlModal
          isShowModal={isShowModal}
          setIsShowModal={setIsShowModal}
          modalTitle={'간편서명동의 QR/URL'}
        />
      </div>
    </div>
  );
};

export default SettingSimpConsentPage;
