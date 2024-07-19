import BillingDetailBilling from "@/components/vendor/billing/BillingDetailBilling";
import BillingDetailMember from "@/components/vendor/billing/BillingDetailMember";
import BillingDetailPayment from "@/components/vendor/billing/BillingDetailPayment";
import BillingDetailProduct from "@/components/vendor/billing/BillingDetailProduct";
import BillingDetailButton from "@/components/vendor/billing/BillingDetailButton";

const BillingDetailPage = () => {

  return (
    <div className='primary-dashboard w-full'>
      <BillingDetailMember />
      <BillingDetailPayment />
      <BillingDetailBilling />
      <BillingDetailProduct />
      <BillingDetailButton />
    </div>
  );
};

export default BillingDetailPage;
