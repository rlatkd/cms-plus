import BillingDetailBilling from '@/components/vendor/billing/BillingDetailBilling';
import BillingDetailMember from '@/components/vendor/billing/BillingDetailMember';
import BillingDetailPayment from '@/components/vendor/billing/BillingDetailPayment';
import BillingDetailProduct from '@/components/vendor/billing/BillingDetailProduct';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getBillingDetail } from '@/apis/billing';
import edit from '@/assets/edit.svg';
import remove from '@/assets/remove.svg';
import BillingDetailEditButtons from '@/components/vendor/billing/BillingDetailEditButtons';
import BillingDetailButtons from '@/components/vendor/billing/BillingDetailButtons';

// 수정 취소시 원래대로 돌000아와야함

const BillingDetailPage = () => {

  // 청구 상세 정보
  const [billingData, setBillingData] = useState({
    memberId: 0,
    memberName: '',
    memberPhone: '',
    contractId: 0,
    paymentType: { title: '', code: '' },
    paymentMethod: null,
    billingId: 0,
    billingName: '',
    billingType: { title: '', code: '' },
    billingStatus: { title: '', code: '' },
    billingCreatedDate: '',
    billingDate: '',
    billingMemo: '',
    billingProducts: [],
    billingPrice: 0,
  });

  // 청구 수정 정보
  const [billingReq, setBillingReq] = useState({
    billingProducts: [],
    billingDate: '',
    billingMemo: ''
  });

  // 수정 상태 여부
  const [editable, setEditable] = useState(false);

  const billingId = useParams();

  // 수정 저장
  const onEditSave = () => {
    // todo post
    setEditable(false);
  };

  const onEditCancel = () => {
    setBillingReq(
      {
        billingProducts: [...(billingData.billingProducts)],
        billingDate: `${billingData.billingDate}`,
        billingMemo: `${billingData.billingMemo}`
      }
    );
    setEditable(false);
  }

  const onBillingMemoChange = memo => {
    setBillingReq(data => {
      return {
        ...data,
        billingMemo: `${memo}`,
      };
    });
  };

  const onBillingProductChange = products => {
    setBillingReq(data => {
      return {
        ...data,
        billingProducts: [...products],
      };
    });
  };

  // 청구 상세 조회
  // 청구 수정 정보 초기화
  useEffect(() => {

    // 수정 취소 시 원래 정보로 돌아오기 위해 수정정보 따로 객체 깊은복사
    const transformReqData = (data) => {
      return {
          billingMemo: `${data.billingMemo}`,
          billingDate: `${data.billingDate}`,
          billingProducts: (data.billingProducts) ? data.billingProducts.map(product => {
            return {
              ...product,
              name: `${product.name}`,
            };            
          }) : []
      };
    };

    const axiosBillingDetail = async () => {
      try {
        const res = await getBillingDetail(billingId.id);
        console.log('!----청구 상세 조회 성공----!');
        console.log(res.data);
        setBillingData(res.data);
        setBillingReq(transformReqData(res.data));
      } catch (err) {
        console.error(err);
        console.error('axiosBillingDetail => ', err.response.data);
      }
    };

    axiosBillingDetail();
  }, []);

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
          onChange={onBillingMemoChange}
        />
      </div>

      <div className='flex flex-col border-b border-ipt_border my-7 mx-4'>
        <BillingDetailProduct
          billingData={billingReq}
          editable={editable}
          onChange={onBillingProductChange}
        />
      </div>

      <div className='flex justify-end items-center px-2 pt-1 pb-3'>
        {editable ? (
          <BillingDetailEditButtons onCancel={onEditCancel} onSave={onEditSave} />
        ) : (
          <BillingDetailButtons onEdit={() => setEditable(true)} />
        )}
      </div>
    </div>
  );
};

export default BillingDetailPage;
