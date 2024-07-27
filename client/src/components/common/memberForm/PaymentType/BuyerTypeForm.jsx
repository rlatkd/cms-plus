import Checkbox from '@/components/common/inputs/CheckBox';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';

const paymentMethods = [
  { value: 'CARD', label: '카드' },
  { value: 'ACCOUNT', label: '계좌' },
];

const BuyerTypeForm = ({ paymentType, formType }) => {
  const { paymentTypeInfoReq_Buyer, setAvailableMethods } = useMemberPaymentStore();

  // <----- 체크박스 선택값 변경 ----->
  const handleChangeCheckbox = value => {
    const updatedMethods = paymentTypeInfoReq_Buyer.availableMethods.includes(value)
      ? paymentTypeInfoReq_Buyer.availableMethods.filter(m => m !== value)
      : [...paymentTypeInfoReq_Buyer.availableMethods, value];

    // 납부자결제 수단은 최소 한 개 이상
    if (!updatedMethods || updatedMethods.length < 1) {
      alert('최소 한 개 이상의 수단이 등록되어야 합니다.');
      return;
    }

    setAvailableMethods(updatedMethods);
  };

  const makeAvailableMethods = methods => {
    if (!methods) {
      methods = [];
    }
    if (methods.length < 1) {
      methods.push('CARD');
    }
    return methods;
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
              classBox='h-4 w-4 rounded-sm '
              checked={makeAvailableMethods(paymentTypeInfoReq_Buyer.availableMethods).includes(
                method.value
              )}
              onChange={() => handleChangeCheckbox(method.value)}
            />
          </div>
        ))}
      </div>
    </>
  );
};

export default BuyerTypeForm;
