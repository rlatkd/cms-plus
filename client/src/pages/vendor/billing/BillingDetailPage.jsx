import { useEffect, useState, useCallback, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getAllProductList } from '@/apis/product';
import {
  deleteBilling,
  getBillingDetail,
  sendInvoice,
  updateBilling,
  cancelSendInvoice,
  payRealTimeBilling,
  cancelPayBilling,
  getBillingProducts,
} from '@/apis/billing';
import BillingDetailMember from '@/components/vendor/billing/detail/BillingDetailMember';
import BillingDetailPayment from '@/components/vendor/billing/detail/BillingDetailPayment';
import BillingDetailBilling from '@/components/vendor/billing/detail/BillingDetailBilling';
import BillingDetailProduct from '@/components/vendor/billing/detail/BillingDetailProduct';
import BillingDetailEditButtons from '@/components/vendor/billing/detail/BillingDetailEditButtons';
import BillingDetailButtons from '@/components/vendor/billing/detail/BillingDetailButtons';
import { cols } from '@/utils/tableElements/billingProductElement';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';
import useAlert from '@/hooks/useAlert';

const BillingDetailPage = () => {
  const [billingData, setBillingData] = useState({
    member: {},
    billing: {},
    contractId: null,
    paymentType: null,
    paymentMethod: null,
    fieldToState: {},
  });

  const [billingReq, setBillingReq] = useState({
    billingProducts: [],
    billingDate: '',
    invoiceMessage: '',
  });
  const [editable, setEditable] = useState(false);
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [originalBillingReq, setOriginalBillingReq] = useState(null);

  const { id: billingId } = useParams();
  const navigate = useNavigate();

  const onAlert = useAlert();

  const { confirm: confrimComp } = useContext(ConfirmContext);
  const onConfirm = async (msg, type, title) => {
    return await confrimComp(msg, type, title);
  };

  const fetchBillingDetail = useCallback(async () => {
    setIsLoading(true);
    try {
      const res = await getBillingDetail(billingId);
      setBillingData(res.data);
      setBillingReq(prevReq => ({
        ...prevReq,
        billingDate: res.data.billing.billingDate,
        invoiceMessage: res.data.billing.invoiceMessage,
      }));
      console.log(res);
    } catch (err) {
      console.error('청구 상세 정보 조회 실패:', err);
    } finally {
      setIsLoading(false);
    }
  }, [billingId]);

  const fetchBillingProducts = useCallback(async () => {
    try {
      const res = await getBillingProducts(billingId);
      setBillingReq(prevReq => ({
        ...prevReq,
        billingProducts: res.data,
      }));
    } catch (err) {
      console.error('청구상품 조회 실패', err);
    }
  }, [billingId]);

  const fetchAllProducts = useCallback(async () => {
    try {
      const res = await getAllProductList();
      setProducts(res.data);
    } catch (err) {
      console.error('전체 상품 조회 실패', err);
    }
  }, []);

  useEffect(() => {
    fetchBillingDetail();
    fetchBillingProducts();
    fetchAllProducts();
  }, [fetchBillingDetail, fetchBillingProducts, fetchAllProducts]);

  const handleEditStart = () => {
    setOriginalBillingReq({ ...billingReq });
    setIsEditing(true);
    setEditable(true);
  };

  const handleEditCancel = () => {
    setBillingReq(originalBillingReq);
    setIsEditing(false);
    setEditable(false);
  };

  const handleInvoiceMessageChange = message => {
    console.log(message);
    setBillingReq(prev => ({ ...prev, invoiceMessage: message }));
  };
  const handleBillingDateChange = date => {
    setBillingReq(prev => ({ ...prev, billingDate: date }));
  };
  const handleBillingProductChange = newProducts => {
    setBillingReq(prev => ({ ...prev, billingProducts: newProducts }));
  };

  const handleEditSave = async () => {
    try {
      await updateBilling(billingId, billingReq);
      onAlert('청구가 수정되었습니다. 수정된 내용을 확인해주세요!', 'success', '청구 수정성공');
      setIsEditing(false);
      setEditable(false);
    } catch (err) {
      handleEditCancel();
      onAlert('청구가 수정되었습니다!', 'error', '청구 수정 실패', err);
      console.error('청구 수정 실패:', err);
    } finally {
      fetchBillingDetail();
      fetchBillingProducts();
    }
  };

  const handleRemove = async () => {
    const confirm = await onConfirm('청구를 삭제하시겠습니까?', 'warning', '청구 삭제');
    if (!confirm) {
      return;
    }
    try {
      await deleteBilling(billingId);
      console.log('delete');
      onAlert('청구가 삭제되었습니다!', 'default', '청구 삭제성공');
      navigate(-1);
    } catch (err) {
      console.error('청구 삭제 실패:', err);
    }
  };

  const handleSend = async () => {
    try {
      await sendInvoice(billingId);
      onAlert('청구서가 발송되었습니다.', 'default', '청구서 발송');
      fetchBillingDetail();
    } catch (err) {
      console.error('Failed to send invoice:', err);
    }
  };

  const handleCancelSend = async () => {
    const confirm = await onConfirm(
      '청구서 발송을 취소하시겠습니까?',
      'warning',
      '청구서 발송취소'
    );
    if (!confirm) {
      return;
    }
    try {
      await cancelSendInvoice(billingId);
      onAlert('청구서 발송이 취소되었습니다.', 'default', '청구서 발송취소');
      fetchBillingDetail();
    } catch (err) {
      console.error('Failed to cancel invoice send:', err);
    }
  };

  const handlePay = async () => {
    const confirm = await onConfirm('실시간 결제를 하시겠습니까?', 'warning', '실시간 결제');
    if (!confirm) {
      return;
    }
    try {
      await payRealTimeBilling(billingId);
      onAlert('청구가 결제되었습니다!', 'default', '실시간 결제');
      fetchBillingDetail();
    } catch (err) {
      console.error('Failed to pay billing:', err);
    }
  };

  const handleCancelPay = async () => {
    const confirm = await onConfirm('결제를 취소하시겠습니까?', 'warning', '결제취소');
    if (!confirm) {
      return;
    }
    try {
      await cancelPayBilling(billingId);
      onAlert('결제가 취소되었습니다!', 'default', '결제취소');
      fetchBillingDetail();
    } catch (err) {
      console.error('Failed to cancel billing payment:', err);
    }
  };

  if (isLoading) {
    return <div className='flex justify-center items-center h-screen'>로딩 중...</div>;
  }

  return (
    <div className='primary-dashboard w-full'>
      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailMember memberData={billingData.member} />
      </div>

      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailPayment
          contractId={billingData.contractId}
          paymentType={billingData.paymentType}
          paymentMethod={billingData.paymentMethod}
        />
      </div>

      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailBilling
          billingData={billingData.billing}
          billingReq={billingReq}
          editable={editable}
          onInvoiceMessageChange={handleInvoiceMessageChange}
          onBillingDateChange={handleBillingDateChange}
        />
      </div>

      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailProduct
          billingProducts={billingReq.billingProducts}
          products={products}
          editable={editable}
          onChange={handleBillingProductChange}
          billingId={billingId}
          cols={cols}
        />
      </div>

      <div className='flex justify-end items-center px-2 pt-1 pb-3'>
        {isEditing ? (
          <BillingDetailEditButtons onCancel={handleEditCancel} onSave={handleEditSave} />
        ) : (
          <BillingDetailButtons
            fieldToState={billingData.fieldToState}
            invoiceSendTime={billingData.billing.invoiceSendDateTime}
            paidDateTime={billingData.billing.paidDateTime}
            onEdit={handleEditStart}
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
