import { privateAxios } from '.';

/* 간편서명동의 데이터 가져오기 */
export const getSimpleConsent = async () => {
  const res = await privateAxios.get('/v1/vendor/simple-consent');
  return res.data;
};

/* 간편서명동의 설정 전체 상품리스트 가져오기 */
export const getAllProducts = async () => {
  const res = await privateAxios.get('/v1/vendor/product/all/no-cond');
  return res.data;
};

/* 간편서명동의 설정 업데이트 */
export const updateSimpleConsent = async data => {
  const res = await privateAxios.put('/v1/vendor/simple-consent', data);
  return res.data;
};

/* 간편서명동의 가능한 옵션 가져오기 */
export const getAvailableOptions = async () => {
  const res = await privateAxios.get('/v1/vendor/simple-consent/available-options');
  return res.data;
};

/* 간편서명동의 링크 발송하기 */
export const sendReqSimpConsent = async contractId => {
  const res = await privateAxios.get(`/v1/vendor/simple-consent/send/${contractId}`);
  return res.data;
};

/* 간편서명동의 회원 데이터 보내기 */
export const sendSimpleConsentData = async userData => {
  try {
    const res = await privateAxios.post('/v1/simple-consent', userData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return res.data;
  } catch (err) {
    console.error('간편서명동의 회원 데이터 보내기 실패:', err);
    throw err;
  }
};

// 간편서명동의 URL 전송
export const sendSimpleConsentUrl = async urlData => {
  const res = await privateAxios.post('/v1/kafka/messaging/simpconsent', urlData);
  return res;
};
