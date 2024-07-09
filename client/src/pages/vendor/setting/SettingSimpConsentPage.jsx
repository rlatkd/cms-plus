import SimpConsentQrUrlModal from '@/components/vendor/modal/SimpConsentQrUrlModal';
import { useState } from 'react';

const SettingSimpConsentPage = () => {
  const [isShowModal, setIsShowModal] = useState(false);

  return (
    <div className='primary-dashboard h-full w-full'>
      SettingSimpConsent
      <button
        className='mr-4 rounded-lg bg-mint p-3 font-bold text-white'
        onClick={() => setIsShowModal(true)}>
        url 확인
      </button>
      <SimpConsentQrUrlModal
        isShowModal={isShowModal}
        setIsShowModal={setIsShowModal}
        modalTitle={'간편서명동의 QR/URL'}
      />
    </div>
  );
};

export default SettingSimpConsentPage;
