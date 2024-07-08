import React, { useState, useRef } from 'react';
import InfoRow from '@/components/common/InfoRow';
import SignatureCanvas from 'react-signature-canvas';

const Signature = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [signature, setSignature] = useState(null);
  const signatureRef = useRef();

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const saveSignature = () => {
    if (signatureRef.current) {
      const dataUrl = signatureRef.current.toDataURL();
      setSignature(dataUrl);
      closeModal();
    }
  };

  const clearSignature = () => {
    if (signatureRef.current) {
      signatureRef.current.clear();
    }
  };

  return (
    <div className='relative bg-white p-1'>
      {' '}
      <div className='w-full text-left'>
        <h3 className='mb-8 text-base font-semibold text-gray-700'>
          회원님의
          <br />
          정보를 확인하고 서명해주세요.
        </h3>
      </div>
      <div className='mb-4 space-y-2 border-b border-t py-3'>
        <InfoRow label='상품' value='상품명1 외 1' />
        <InfoRow label='합계금액' value='11,000원' />
        <InfoRow label='기간' value='2024.06.31~2024.07.31' />
        <InfoRow label='약정일' value='31일' />
        <InfoRow label='결제수단' value='신한카드 111*****1111' />
      </div>
      <div className='mb-4 mt-8'>
        <label className='mb-1 block text-sm font-medium text-gray-700'>서명</label>
        <button
          onClick={openModal}
          className='flex h-32 w-full items-center justify-center rounded-md border border-mint text-gray-400'>
          {signature ? (
            <img src={signature} alt='서명' className='h-full w-full object-contain' />
          ) : (
            '서명하기'
          )}
        </button>
      </div>
      {isModalOpen && (
        <div className='fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50'>
          {' '}
          {/* z-50 추가 */}
          <div className='rounded-lg bg-white p-4'>
            <h3 className='mb-2 text-base font-semibold'>서명하기</h3>
            <SignatureCanvas
              ref={signatureRef}
              canvasProps={{
                width: 300,
                height: 150,
                className: 'border border-gray-300 rounded-lg',
              }}
            />
            <div className='mt-4 flex justify-end space-x-2'>
              <button onClick={clearSignature} className='rounded-lg bg-gray-200 px-4 py-2 text-sm'>
                지우기
              </button>
              <button
                onClick={saveSignature}
                className='rounded-lg bg-mint px-4 py-2 text-sm text-white'>
                저장하기
              </button>
              <button onClick={closeModal} className='rounded-lg bg-gray-200 px-4 py-2 text-sm'>
                취소
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Signature;
