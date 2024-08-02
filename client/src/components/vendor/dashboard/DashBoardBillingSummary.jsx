import { STATUS_COLORS } from '@/pages/vendor/DashBoardPage';
import formatNumber from '@/utils/format/formatNumber';

const BillingSummary = ({ billingInfo, month }) => (
  <div className='bg-white rounded-xl p-6'>
    <div className='flex justify-between items-center mb-6 border-b border-ipt_border pb-4'>
      <h2 className='text-xl font-700 text-text_black'>{month}월 청구 현황</h2>
      <p className='text-sm font-700 text-text_black'>
        총
        <span className='font-800 text-indigo-600'>
          {formatNumber(billingInfo.totalBillingAmount)}건
        </span>
      </p>
    </div>
    <div className='space-y-6'>
      <div className='rounded-lg p-4'>
        <p className='text-sm font-medium text-text_black mb-1'>총 청구액</p>
        <p className='text-3xl font-bold text-text_black whitespace-nowrap overflow-hidden text-ellipsis'>
          ₩{formatNumber(billingInfo.totalBillingPrice)}
        </p>
      </div>
      <div className='bg-gray-50 rounded-lg p-4'>
        <p className='text-sm font-medium text-gray-700 mb-3'>청구 상태</p>
        <div className='grid grid-cols-2 gap-4'>
          {[
            {
              label: '완납',
              value: billingInfo.statusCounts?.PAID,
              color: STATUS_COLORS.PAID,
            },
            {
              label: '수납 대기중',
              value: billingInfo.statusCounts?.WAITING_PAYMENT,
              color: STATUS_COLORS.WAITING_PAYMENT,
            },
            {
              label: '미납',
              value: billingInfo.statusCounts?.NON_PAID,
              color: STATUS_COLORS.NON_PAID,
            },
            {
              label: '생성',
              value: billingInfo.statusCounts?.CREATED,
              color: STATUS_COLORS.CREATED,
            },
          ].map(({ label, value, color }) => (
            <div key={label} className={`${color} rounded-md p-3 shadow-sm`}>
              <p className='text-xs font-medium mb-1'>{label}</p>
              <p className='text-lg font-semibold whitespace-nowrap overflow-hidden text-ellipsis'>
                {formatNumber(value || 0)}건
              </p>
            </div>
          ))}
        </div>
      </div>
    </div>
  </div>
);

export default BillingSummary;
