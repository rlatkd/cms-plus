import React, { useEffect, useRef, useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import formatNumber from '@/utils/format/formatNumber';
import { STATUS_COLORS } from '@/pages/vendor/DashBoardPage';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';

const getEventPriorityStatus = statusCounts => {
  if (statusCounts.NON_PAID > 0) return 'NON_PAID';
  if (statusCounts.WAITING_PAYMENT > 0) return 'WAITING_PAYMENT';
  if (statusCounts.PAID > 0) return 'PAID';
  if (statusCounts.CREATED > 0) return 'CREATED';
  return 'NONE';
};

const getEventColor = status => {
  const color = STATUS_COLORS[status];
  return color ? color.split(' ')[0] : 'bg-gray-200';
};

export const convertToCalendarEvents = billingInfo => {
  const dayBillings = billingInfo?.dayBillingRes || [];
  return dayBillings
    .map(day => ({
      start: day.billingDate,
      allDay: true,
      extendedProps: {
        totalPrice: day.totalDayBillingPrice,
        totalCount: day.totalDayBillingCount,
        statusCounts: day.statusCounts,
      },
    }))
    .filter(event => event.start);
};

const EventTooltip = ({ event }) => {
  const { totalPrice, totalCount, statusCounts } = event.extendedProps;
  return (
    <div className='bg-white p-3 rounded-lg shadow-lg border border-gray-200 text-sm'>
      <p className='mb-2'>
        <span className='font-semibold'>총 청구액:</span> ₩{formatNumber(totalPrice)}
      </p>
      <p className='mb-2'>
        <span className='font-semibold'>총 건수:</span> {formatNumber(totalCount)}건
      </p>
      <div className='grid grid-cols-2 gap-2'>
        <p>
          <span
            className={`inline-block w-3 h-3 rounded-full ${STATUS_COLORS.PAID.split(' ')[0]} mr-1`}
          />
          <span className='font-semibold'>완납:</span> {statusCounts.PAID || 0}건
        </p>
        <p>
          <span
            className={`inline-block w-3 h-3 rounded-full ${STATUS_COLORS.WAITING_PAYMENT.split(' ')[0]} mr-1`}
          />
          <span className='font-semibold'>수납 대기중:</span> {statusCounts.WAITING_PAYMENT || 0}건
        </p>
        <p>
          <span
            className={`inline-block w-3 h-3 rounded-full ${STATUS_COLORS.NON_PAID.split(' ')[0]} mr-1`}
          />
          <span className='font-semibold'>미납:</span> {statusCounts.NON_PAID || 0}건
        </p>
        <p>
          <span
            className={`inline-block w-3 h-3 rounded-full ${STATUS_COLORS.CREATED.split(' ')[0]} mr-1`}
          />
          <span className='font-semibold'>생성:</span> {statusCounts.CREATED || 0}건
        </p>
      </div>
    </div>
  );
};

const DashBoardCalendar = ({ events, onEventClick, calendarRef, onNext, onPrev }) => {
  const [tooltipEvent, setTooltipEvent] = useState(null);
  const [tooltipPosition, setTooltipPosition] = useState({ top: 0, left: 0 });
  const tooltipRef = useRef(null);
  const calendarElRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = event => {
      if (tooltipRef.current && !tooltipRef.current.contains(event.target)) {
        setTooltipEvent(null);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleEventMouseEnter = info => {
    const rect = info.el.getBoundingClientRect();
    const calendarRect = calendarElRef.current.getBoundingClientRect();

    setTooltipPosition({
      top: rect.bottom - calendarRect.top,
      left: rect.left - calendarRect.left,
    });
    setTooltipEvent(info.event);
  };

  const handleEventMouseLeave = () => {
    setTooltipEvent(null);
  };

  return (
    <div className='bg-white rounded-xl shadow-md p-6 relative' ref={calendarElRef}>
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        initialView='dayGridMonth'
        height='auto'
        showNonCurrentDates={true}
        headerToolbar={{
          left: 'title',
          center: '',
          right: 'mPrev,mNext',
        }}
        events={Array.isArray(events) ? events : []}
        eventClick={onEventClick}
        ref={calendarRef}
        customButtons={{
          mNext: {
            text: '다음',
            click: onNext,
            className: 'fc-next-button',
          },
          mPrev: {
            text: '이전',
            click: onPrev,
            className: 'fc-prev-button',
          },
        }}
        eventContent={eventInfo => {
          const { totalPrice, totalCount, statusCounts } = eventInfo.event.extendedProps;
          const priorityStatus = getEventPriorityStatus(statusCounts);
          const backgroundColor = getEventColor(priorityStatus);

          return (
            <div
              className={`p-1 text-xs rounded-sm ${backgroundColor} ${STATUS_COLORS[priorityStatus].split(' ')[1]}`}>
              <div className='font-bold whitespace-nowrap overflow-hidden text-ellipsis'>
                {formatNumber(totalCount)}건
              </div>
              <div className='whitespace-nowrap overflow-hidden text-ellipsis'>
                ₩{formatNumber(totalPrice)}
              </div>
            </div>
          );
        }}
        eventMouseEnter={handleEventMouseEnter}
        eventMouseLeave={handleEventMouseLeave}
        eventBorderColor='transparent'
        eventBackgroundColor='transparent'
        dayCellDidMount={arg => {
          arg.el.style.height = '100px';
        }}
        dayHeaderContent={arg => {
          const days = ['일', '월', '화', '수', '목', '금', '토'];
          return days[arg.dow];
        }}
        titleFormat={{ year: 'numeric', month: 'long' }}
        locale='ko'
        buttonText={{
          today: '오늘',
          month: '월',
          week: '주',
          day: '일',
          list: '목록',
        }}
        buttonIcons={{
          prev: 'chevron-left',
          next: 'chevron-right',
        }}
      />
      {tooltipEvent && (
        <div
          ref={tooltipRef}
          style={{
            position: 'absolute',
            top: `${tooltipPosition.top}px`,
            left: `${tooltipPosition.left}px`,
            zIndex: 1000,
          }}
          className='transition-opacity duration-300 ease-in-out opacity-100'>
          <EventTooltip event={tooltipEvent} />
        </div>
      )}
    </div>
  );
};

export default DashBoardCalendar;
