import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faUsers,
  faFileContract,
  faMoneyBillWave,
  faChartLine,
} from '@fortawesome/free-solid-svg-icons';
import { useEffect, useRef, useState } from 'react';
import { getMonthBillingInfo, getStatInfo, getTopInfo } from '@/apis/dashboard';

const formatNumber = (num) => {
  if (!num) return '0';
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

const Stats = ({ statInfo }) => (
  <div className='mb-10 grid grid-cols-1 md:grid-cols-4 gap-6'>
    <StatItem
      icon={faUsers}
      title='회원 현황'
      value={`${statInfo.totalMemberCount}명`}
      subStat={`신규: ${statInfo.newMemberCount} | 활성: ${statInfo.activeMemberCount}`}
    />
    <StatItem
      icon={faFileContract}
      title='계약 현황'
      value={`${statInfo.totalContractCount}건`}
      subStat={`신규: ${statInfo.newContractCount} | 만료예정: ${statInfo.expectedToExpiredCount}`}
    />
    <StatItem
      icon={faMoneyBillWave}
      title='청구 현황'
      value={`₩${statInfo.totalBillingPrice}`}
      subStat={`완납: ₩${formatNumber(statInfo.totalPaidPrice)} | 미납: ₩${formatNumber(statInfo.totalNotPaidPrice)}`}
    />
    <StatItem
      icon={faChartLine}
      title={'전월 대비 매출'}
      value={`+${statInfo.billingPriceGrowth}%`}
      subStat={`회원: ${statInfo.memberGrowth >= 0 ? '+' : ''}${statInfo.memberGrowth}%`}
    />
  </div>
);

const StatItem = ({ icon, title, value, subStat }) => (
  <div className='bg-white rounded-xl shadow-md p-6 transition duration-300 ease-in-out hover:shadow-lg'>
    <div className='flex items-center mb-4'>
      <div className='text-blue-500 text-2xl mr-4'>
        <FontAwesomeIcon icon={icon} color='#4FD1C5' />
      </div>
      <h3 className='text-sm font-medium text-gray-600'>{title}</h3>
    </div>
    <p className='text-2xl font-bold text-gray-800 mb-2'>{formatNumber(value)}</p>
    <div className='text-xs text-gray-500'>{subStat}</div>
  </div>
);

const BillingSummary = ({ billingInfo, month }) => (
  <div className='bg-white rounded-xl shadow-md p-6'>
    <h2 className='text-xl font-bold mb-6 text-gray-800 border-b pb-2'>{month}월 청구 현황</h2>
    <div className='space-y-6'>
      <div className='bg-blue-50 rounded-lg p-4'>
        <p className='text-sm font-medium text-blue-600 mb-1'>총 청구액</p>
        <p className='text-3xl font-bold text-blue-700'>
          ₩{formatNumber(billingInfo.totalBillingPrice)}
        </p>
      </div>
      <div className='bg-gray-50 rounded-lg p-4'>
        <p className='text-sm font-medium text-gray-700 mb-3'>청구 건수</p>
        <div className='grid grid-cols-2 gap-4'>
          <div className='bg-white rounded-md p-3 shadow-sm'>
            <p className='text-xs text-gray-500 mb-1'>총 청구</p>
            <p className='text-lg font-semibold text-gray-800'>
              {formatNumber(billingInfo.totalBillingAmount)}건
            </p>
          </div>
          <div className='bg-white rounded-md p-3 shadow-sm'>
            <p className='text-xs text-gray-500 mb-1'>완납</p>
            <p className='text-lg font-semibold text-green-600'>
              {formatNumber(billingInfo.paidBillingAmount)}건
            </p>
          </div>
          <div className='bg-white rounded-md p-3 shadow-sm'>
            <p className='text-xs text-gray-500 mb-1'>수납대기</p>
            <p className='text-lg font-semibold text-yellow-600'>
              {formatNumber(billingInfo.waitBillingAmount)}건
            </p>
          </div>
          <div className='bg-white rounded-md p-3 shadow-sm'>
            <p className='text-xs text-gray-500 mb-1'>미납</p>
            <p className='text-lg font-semibold text-red-600'>
              {formatNumber(billingInfo.nonPaidBillingAmount)}건
            </p>
          </div>
          <div className='bg-white rounded-md p-3 shadow-sm'>
            <p className='text-xs text-gray-500 mb-1'>생성</p>
            <p className='text-lg font-semibold text-blue-600'>
              {formatNumber(billingInfo.createdBillingAmount)}건
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
);

const Calendar = (mEvents, mEventClick, ref, onNext, onPrev) => (
  <div className='bg-white rounded-xl shadow-md p-6'>
    <FullCalendar
      plugins={[dayGridPlugin]}
      initialView='dayGridMonth'
      height={'auto'}
      showNonCurrentDates={true}
      headerToolbar={{
        left: '',
        center: 'title',
        right: 'mPrev mNext',
      }}
      events={mEvents}
      eventClick={mEventClick}
      ref={ref}
      customButtons={{
        mNext: {
          text: '>',
          click: onNext,
        },
        mPrev: {
          text: '<',
          click: onPrev,
        },
      }}
    />
  </div>
);

const TopMembers = ({ topFive }) => (
  <div className='bg-white rounded-xl shadow-md p-6'>
    <h2 className='text-xl font-bold mb-4 text-gray-800'>Top 5 회원</h2>
    <table className='w-full'>
      <thead>
        <tr className='bg-gray-50'>
          <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
            회원명
          </th>
          <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
            총 계약액
          </th>
          <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
            계약 수
          </th>
        </tr>
      </thead>
      <tbody className='bg-white divide-y divide-gray-200'>
        {topFive &&
          topFive.map((mem, idx) => (
            <TableRow
              key={idx}
              data={[
                mem.memberName,
                `₩${formatNumber(mem.totalContractPrice)}`,
                formatNumber(mem.contractCount),
              ]}
            />
          ))}
      </tbody>
    </table>
  </div>
);

const RecentContracts = ({ contracts }) => (
  <div className='bg-white rounded-xl shadow-md p-6'>
    <h2 className='text-xl font-bold mb-4 text-gray-800'>최근 계약</h2>
    <table className='w-full'>
      <thead>
        <tr className='bg-gray-50'>
          <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
            계약일
          </th>
          <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
            회원명
          </th>
          <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
            계약액
          </th>
          <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
            기간
          </th>
        </tr>
      </thead>
      <tbody className='bg-white divide-y divide-gray-200'>
        {contracts &&
          contracts.map((contract, idx) => (
            <TableRow
              key={idx}
              data={[
                contract.contractStartDate,
                contract.memberName,
                `₩${formatNumber(contract.totalContractPrice)}`,
                `${contract.contractMonth} 개월`,
              ]}
            />
          ))}
      </tbody>
    </table>
  </div>
);

const TableRow = ({ data }) => (
  <tr className='hover:bg-gray-50'>
    {data.map((item, index) => (
      <td key={index} className='px-3 py-4 whitespace-nowrap text-sm text-gray-500'>
        {item}
      </td>
    ))}
  </tr>
);

const DashBoardPage = () => {
  const [statInfo, setStatInfo] = useState({
    totalMemberCount: 0,
    newMemberCount: 0,
    activeMemberCount: 0,
    totalContractCount: 0,
    newContractCount: 0,
    expectedToExpiredCount: 0,
    totalBillingPrice: 0,
    totalPaidPrice: 0,
    totalNotPaidPrice: 0,
    billingPriceGrowth: 0,
    memberGrowth: 0
  });
  const [billingInfo, setBillingInfo] = useState({});
  const [topFive, setTopFive] = useState({});
  const [date, setDate] = useState(new Date());

  const calendarRef = useRef();

  const calendarGoNext = () => {
    const calendarApi = calendarRef.current.getApi();
    calendarApi.next();
    setDate(calendarApi.getDate());
  };

  const calendarGoPrev = () => {
    const calendarApi = calendarRef.current.getApi();
    calendarApi.prev();
    setDate(calendarApi.getDate());
  };

  // 일별 청구 달력 event 변환
  const convertToCalendarEvents = billingInfo => {
    const dayBillings = billingInfo.dayBillingRes;
    if (!dayBillings) return [];
    return dayBillings.map(day => {
      return {
        title: `${day.totalDayBillingCount}건 ${day.totalDayBillingPrice}원`,
        start: `${day.billingDate}`,
        color: '#ff9f89',
      };
    });
  };

  //TODO 이벤트 클릭시 하루 청구 상세 조회
  const handleCalendarEventClicked = async info => {
    const date = info.event.start;
    if (date) {
      // const res = await getStatInfo(year, month);
      // modal open with data
    }
  };

  useEffect(() => {
    const date = new Date();
    const curYear = date.getFullYear();
    const curMonth = date.getMonth() + 1;

    const fetchData = async () => {
      const [statRes, topRes] = await Promise.all([getStatInfo(curYear, curMonth), getTopInfo()]);

      setStatInfo(statRes.data);
      setTopFive(topRes.data);
    };

    fetchData();
  }, []);

  useEffect(() => {
    const curMonth = date.getMonth() + 1;
    const curYear = date.getFullYear();

    const fetchMonthBillingInfo = async () => {
      const res = await getMonthBillingInfo(curYear, curMonth);
      console.log('billinginfo', res);
      setBillingInfo(res.data);
    };

    fetchMonthBillingInfo();
  }, [date]);

  return (
    <div className='bg-gray-100 min-h-screen py-8'>
      <div className='container mx-auto px-4'>
        <Stats statInfo={statInfo} />
        <div className='grid grid-cols-1 lg:grid-cols-4 gap-6 mb-8'>
          <div className='lg:col-span-3'>
            <div className='bg-white rounded-xl shadow-md p-6'>
              <FullCalendar
                plugins={[dayGridPlugin]}
                initialView='dayGridMonth'
                height={'auto'}
                showNonCurrentDates={true}
                headerToolbar={{
                  left: '',
                  center: 'title',
                  right: 'mPrev mNext',
                }}
                events={convertToCalendarEvents(billingInfo)}
                ref={calendarRef}
                customButtons={{
                  mNext: {
                    text: '>',
                    click: function () {
                      calendarGoNext();
                    },
                  },
                  mPrev: {
                    text: '<',
                    click: function () {
                      calendarGoPrev();
                    },
                  },
                }}
              />
            </div>
          </div>
          <BillingSummary billingInfo={billingInfo} month={date.getMonth() + 1} />
        </div>
        <div className='grid grid-cols-1 lg:grid-cols-2 gap-6'>
          <TopMembers topFive={topFive.topFiveMemberRes} />
          <RecentContracts contracts={topFive.recentFiveContracts} />
        </div>
      </div>
    </div>
  );
};

export default DashBoardPage;

// import { useContext } from 'react';
// import AlertContext from '@/utils/dialog/alert/AlertContext';
// import AlertWdithContext from '@/utils/dialog/alertwidth/AlertWidthContext';
// import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';
//
// const { alert: alertComp } = useContext(AlertContext);
//
// const onAlertClick = async () => {
//   const result = await alertComp('인증번호가 전송되었습니다!');
//   console.log('onAlertClick : ', result);
// };
//
// const { alertWidth: alertCompWidth } = useContext(AlertWdithContext);
//
// const onAlertWidthClick = async () => {
//   const result = await alertCompWidth('hello world Width');
//   console.log('onAlertWidthClick : ', result);
// };
//
// const { confirm: confirmComp } = useContext(ConfirmContext);
//
// const onConfirmClick = async () => {
//   const result = await confirmComp('상품을 삭제 하시겠습니까?');
//   console.log('onConfirmClick : ', result);
// };
