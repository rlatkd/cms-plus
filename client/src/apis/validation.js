import { publicAxios } from '.';

// 카드 인증을 위한 함수
export const verifyCard = async cardData => {
  try {
    const res = await publicAxios.post('/v1/simple-consent/card/verify', cardData);
    return res.data;
  } catch (err) {
    console.error('Error verifying card:', err);
    throw err;
  }
};

// CMS 인증을 위한 함수
export const verifyCMS = async cmsData => {
  try {
    const res = await publicAxios.post('/v1/simple-consent/cms/verify', cmsData);
    return res.data;
  } catch (err) {
    console.error('Error verifying CMS:', err);
    throw err;
  }
};
