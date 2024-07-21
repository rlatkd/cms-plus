import Checkbox from '@/components/common/inputs/CheckBox';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';

const paymentMethods = [
  { value: 'CARD', label: '카드' },
  { value: 'ACCOUNT', label: '계좌' },
];

const BuyerTypeForm = ({ paymentType, formType }) => {
  const { paymentTypeInfoReq_Buyer, setAvailableMethods } = useMemberPaymentStore();

  // <------ 체크박스 선택값 변경 ------>
  const handleChangeCheckbox = value => {
    const updatedMethods = paymentTypeInfoReq_Buyer.availableMethods.includes(value)
      ? paymentTypeInfoReq_Buyer.availableMethods.filter(m => m !== value)
      : [...paymentTypeInfoReq_Buyer.availableMethods, value];
    setAvailableMethods(updatedMethods);
  };
  return (
    <>
      <p className='mb-4 ml-1 text-15 text-text_black font-700'>
        결제수단<span className='ml-1 text-red-500'>*</span>
      </p>
      <div className='flex items-center text-sm text-text_black'>
        {paymentMethods.map(method => (
          <div key={method.value} className='flex items-center w-20'>
            <Checkbox
              name={method.value}
              label={method.label}
              checked={paymentTypeInfoReq_Buyer.availableMethods.includes(method.value)}
              onChange={() => handleChangeCheckbox(method.value)}
            />
          </div>
        ))}
      </div>
    </>
  );
};

export default BuyerTypeForm;
