import { useState, useRef } from 'react';
import BaseModal from '@/components/common/BaseModal';
import QRCode from 'qrcode.react';
import send from '@/assets/send.svg';

// 전화번호 포맷팅 함수 import
import { formatPhone, removeDashes } from '@/utils/format/formatPhone';
import { sendSimpleConsentUrl } from '@/apis/simpleConsent';

const SimpConsentQrUrlModal = ({ isShowModal, setIsShowModal, modalTitle }) => {
  const [url, setUrl] = useState('www.cms.site');
  const [phoneNumber, setPhoneNumber] = useState('');
  const qrRef = useRef(null);

  const handleCopyUrl = () => {
    navigator.clipboard.writeText(url).then(() => {
      alert('URL이 클립보드에 복사되었습니다.');
    });
  };

  console.log(phoneNumber.replaceAll('-', ''));

  const handleSendSimpleConsentUrl = async () => {
    const urlData = {
      text: `간편동의 URL입니다. \n${url}`,
      method: 'SMS',
      phoneNumber: phoneNumber.replaceAll('-', ''),
    };
    try {
      const res = await sendSimpleConsentUrl(urlData);
      console.log(res.data);
      alert('링크가 전송되었습니다.');
    } catch (err) {
      console.error('axiosSimpleConsentUrl => ', err.response.data);
      alert('링크 전송에 실패했습니다.');
    }
  };

  const handleDownloadQR = () => {
    if (qrRef.current) {
      const canvas = qrRef.current.querySelector('canvas');
      const image = canvas.toDataURL('image/png').replace('image/png', 'image/octet-stream');
      const link = document.createElement('a');
      link.href = image;
      link.download = 'qrcode.png';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  };

  const handlePhoneNumberChange = e => {
    const input = e.target.value;
    const cleaned = removeDashes(input);
    const formatted = formatPhone(cleaned);
    setPhoneNumber(formatted);
  };

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={send}
      height={'h-640'}
      width={'w-480'}>
      <div className='flex flex-col items-center p-2'>
        <div ref={qrRef} className='mb-4 rounded border border-gray-200 p-2'>
          <QRCode value={url} size={200} />
        </div>
        <button
          onClick={handleDownloadQR}
          className='mb-4 w-full rounded-lg bg-mint py-2 font-semibold text-white'>
          QR 다운로드
        </button>

        <div className='mb-4 w-full'>
          <label className='mb-2 block text-sm font-medium text-gray-700'>URL</label>
          <div className='flex flex-col sm:flex-row'>
            <input
              type='text'
              value={url}
              onChange={e => setUrl(e.target.value)}
              className='mb-2 flex-grow rounded-lg border border-gray-300 px-3 py-2 sm:mb-0 sm:mr-2'
              disabled
            />
            <div className='flex-shrink-0 sm:w-36'>
              <button
                onClick={handleCopyUrl}
                className='w-full whitespace-nowrap rounded-lg bg-mint px-4 py-2 text-white'>
                URL 링크 복사
              </button>
            </div>
          </div>
        </div>

        <div className='w-full'>
          <label className='mb-2 block text-sm font-medium text-gray-700'>연락처</label>
          <div className='flex flex-col sm:flex-row'>
            <input
              type='tel'
              value={phoneNumber}
              onChange={handlePhoneNumberChange}
              className='mb-2 flex-grow rounded-lg border border-gray-300 px-3 py-2 sm:mb-0 sm:mr-2'
              placeholder="'-' 없이 입력"
              maxLength={13}
            />
            <div className='flex-shrink-0 sm:w-36'>
              <button
                onClick={handleSendSimpleConsentUrl}
                className='w-full whitespace-nowrap rounded-lg bg-mint px-4 py-2 text-white'>
                링크 전송
              </button>
            </div>
          </div>
        </div>
      </div>
    </BaseModal>
  );
};

export default SimpConsentQrUrlModal;
