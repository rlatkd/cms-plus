import React, { useState, useEffect } from 'react';
import { formatPhone } from '@/utils/format/formatPhone';
import { Tooltip } from 'react-tooltip';
import 'react-tooltip/dist/react-tooltip.css';
import BaseModal from '@/components/common/BaseModal';
import { FaExclamationCircle } from 'react-icons/fa';

const ReqSimpConsentErrorModal = ({ errors, isShowModal, icon, setIsShowModal, modalTitle }) => {
  const [hoveredIndex, setHoveredIndex] = useState(null);

  useEffect(() => {
    if (isShowModal) {
      setHoveredIndex(null);
    }
  }, [isShowModal]);

  const handleMouseEnter = index => {
    setHoveredIndex(index);
  };

  const handleMouseLeave = () => {
    setHoveredIndex(null);
  };

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-5/6'}
      width={'w-5/6'}>
      <div className='flex flex-col h-full'>
        <div className='mb-4 text-2xl font-bold text-center'>
          간편동의 요청 실패:
          <span className='text-gray-700'>{errors && errors[0].total}건</span> 중
          <span className='text-red-600'>{errors ? errors.length : '-'}건</span> 실패
        </div>
        <div className='flex-grow overflow-auto rounded-lg mb-4'>
          <table className='w-full'>
            <thead>
              <tr className='bg-gray-200 text-gray-700 uppercase text-sm leading-normal'>
                <th className='py-3 px-6 text-left' />
                <th className='py-3 px-6 text-left'>회원이름</th>
                <th className='py-3 px-6 text-left'>휴대전화</th>
                <th className='py-3 px-6 text-left'>결제방식</th>
                <th className='py-3 px-6 text-left'>약정일</th>
                <th className='py-3 px-6 text-left'>계약상태</th>
              </tr>
            </thead>
            <tbody className='text-gray-900 text-sm'>
              {errors &&
                errors.map(({ from, res }, idx) => (
                  <tr
                    key={idx}
                    className={`transition-colors ${
                      idx !== errors.length - 1 ? 'border-b border-gray-200' : ''
                    } ${hoveredIndex === idx ? 'bg-blue-50' : 'hover:bg-gray-100'}`}
                    onMouseEnter={() => handleMouseEnter(idx)}
                    onMouseLeave={handleMouseLeave}>
                    <td className='py-3 px-6'>
                      <div className='flex items-center'>
                        <FaExclamationCircle
                          className={`cursor-help transition-all duration-300 ${
                            hoveredIndex === idx ? 'text-red-600 scale-125' : 'text-red-400'
                          }`}
                          data-tooltip-id={`error-tooltip-${idx}`}
                        />
                      </div>
                    </td>
                    <td className='py-3 px-6'>{from.memberName}</td>
                    <td className='py-3 px-6'>{formatPhone(from.memberPhone)}</td>
                    <td className='py-3 px-6'>{from.paymentType}</td>
                    <td className='py-3 px-6'>{from.contractDay}</td>
                    <td className='py-3 px-6'>{from.contractStatus}</td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
        <div className='flex justify-center'>
          <button
            onClick={() => setIsShowModal(false)}
            className='px-5 py-2 bg-mint text-white rounded-lg hover:bg-mint_hover transition-colors text-base font-semibold focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-mint shadow-md'>
            확인
          </button>
        </div>
      </div>
      {errors &&
        errors.map((error, idx) => (
          <Tooltip
            key={idx}
            id={`error-tooltip-${idx}`}
            place='top'
            isOpen={hoveredIndex === idx}
            content={error.res.message}
            style={{
              backgroundColor: '#f8d7da',
              color: '#721c24',
              padding: '10px',
              borderRadius: '6px',
              fontSize: '14px',
              boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
              maxWidth: '300px',
              opacity: 1,
              transition: 'opacity 0.3s ease-in-out',
            }}
          />
        ))}
    </BaseModal>
  );
};

export default ReqSimpConsentErrorModal;
