import { useEffect, useState, useCallback } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getAllProductList } from '@/apis/product';
import { deleteBilling, getBillingDetail, sendInvoice, updateBilling, cancelSendInvoice, payBilling, cancelPayBilling } from '@/apis/billing';
import BillingDetailMember from '@/components/vendor/billing/BillingDetailMember';
import BillingDetailPayment from '@/components/vendor/billing/BillingDetailPayment';
import BillingDetailBilling from '@/components/vendor/billing/BillingDetailBilling';
import BillingDetailProduct from '@/components/vendor/billing/BillingDetailProduct';
import BillingDetailEditButtons from '@/components/vendor/billing/BillingDetailEditButtons';
import BillingDetailButtons from '@/components/vendor/billing/BillingDetailButtons';

const BillingDetailPage = () => {
  const [billingData, setBillingData] = useState({
    // ... (기존 상태 유지)
  });
  
  const [billingReq, setBillingReq] = useState({
    billingProducts: [],
    billingDate: '',
    billingMemo: ''
  });
  const [editable, setEditable] = useState(false);
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);  // 로딩 상태 추가

  const { id: billingId } = useParams();
  const navigate = useNavigate();

  const fetchBillingDetail = useCallback(async () => {
    setIsLoading(true);  // 데이터 로딩 시작
    try {
      const res = await getBillingDetail(billingId);
      setBillingData(res.data);
      setBillingReq(transformReqData(res.data));
    } catch (err) {
      console.error('Failed to fetch billing detail:', err);
    } finally {
      setIsLoading(false);  // 데이터 로딩 완료
    }
  }, [billingId]);

  const fetchAllProducts = useCallback(async () => {
    try {
      const res = await getAllProductList();
      setProducts(res.data);
    } catch (err) {
      console.error('Failed to fetch product list:', err);
    }
  }, []);

  useEffect(() => {
    fetchBillingDetail();
    fetchAllProducts();
  }, [fetchBillingDetail, fetchAllProducts]);

  const transformReqData = (data) => ({
    billingMemo: data.billingMemo,
    billingDate: data.billingDate,
    billingProducts: data.billingProducts?.map(product => ({
      ...product,
      name: product.name,
    })) || []
  });

  const handleEditSave = async () => {
    try {
      await updateBilling(billingId, billingReq);
      alert('청구가 수정되었습니다.');
      setEditable(false);
      fetchBillingDetail();
    } catch (err) {
      alert('청구 수정에 실패했습니다.');
      console.error('Failed to update billing:', err);
    }
  };

  const handleRemove = async () => {
    try {
      await deleteBilling(billingId);
      alert('청구가 삭제되었습니다.');
      navigate(-1);
    } catch (err) {
      alert('청구 삭제에 실패했습니다.');
      console.error('Failed to delete billing:', err);
    }
  };

  const handleSend = async () => {
    try {
      await sendInvoice(billingId);
      alert('청구서가 발송되었습니다.');
      fetchBillingDetail();
    } catch (err) {
      alert('청구서 발송에 실패했습니다.');
      console.error('Failed to send invoice:', err);
    }
  };

  const handleCancelSend = async () => {
    try {
      await cancelSendInvoice(billingId);
      alert('청구서 발송이 취소되었습니다.');
      fetchBillingDetail();
    } catch (err) {
      alert('청구서 발송 취소에 실패했습니다.');
      console.error('Failed to cancel invoice send:', err);
    }
  };

  const handlePay = async () => {
    try {
      await payBilling(billingId);
      alert('청구가 결제되었습니다.');
      fetchBillingDetail();
    } catch (err) {
      alert('청구 결제에 실패했습니다.');
      console.error('Failed to pay billing:', err);
    }
  };

  const handleCancelPay = async () => {
    try {
      await cancelPayBilling(billingId);
      alert('청구 결제가 취소되었습니다.');
      fetchBillingDetail();
    } catch (err) {
      alert('청구 결제 취소에 실패했습니다.');
      console.error('Failed to cancel billing payment:', err);
    }
  };

  const handleEditCancel = () => {
    setBillingReq(transformReqData(billingData));
    setEditable(false);
  };

  const handleBillingMemoChange = memo => {
    setBillingReq(prev => ({ ...prev, billingMemo: memo }));
  };
  const handleBillingDateChange = date => {
    setBillingReq(prev => ({ ...prev, billingDate: date }));
  };

  const handleBillingProductChange = products => {
    setBillingReq(prev => ({ ...prev, billingProducts: products }));
  };

  if (isLoading) {
    return <div className="flex justify-center items-center h-screen">로딩 중...</div>;
  }

  return (
    <div className='primary-dashboard w-full'>
      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailMember billingData={billingData} />
      </div>

      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailPayment billingData={billingData} />
      </div>

      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailBilling
          billingData={billingData}
          billingReq={billingReq}
          editable={editable}
          onBillingMemoChange={handleBillingMemoChange}
          onBillingDateChange={handleBillingDateChange}
        />
      </div>

      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailProduct
          billingData={billingReq}
          products={products}
          editable={editable}
          onChange={handleBillingProductChange}
          billingId={billingId}
        />
      </div>

      <div className='flex justify-end items-center px-2 pt-1 pb-3'>
        {editable ? (
          <BillingDetailEditButtons onCancel={handleEditCancel} onSave={handleEditSave} />
        ) : (
          <BillingDetailButtons 
            canUpdate={billingData.canBeUpdated}
            canDelete={billingData.canBeDeleted}
            canPay={billingData.canBePaid}
            canSendInvoice={billingData.canSendInvoice}
            canCancelInvoice={billingData.canCancelInvoice}
            canPayCanceled={billingData.canPayCanceled}
            invoiceSendTime={billingData.invoiceSendDateTime}
            paidDateTime={billingData.paidDateTime}
            onEdit={() => setEditable(true)} 
            onRemove={handleRemove}
            onSend={handleSend}
            onCancelSend={handleCancelSend}
            onPay={handlePay}
            onCancelPay={handleCancelPay}
          />
        )}
      </div>
    </div>
  );
};

export default BillingDetailPage;