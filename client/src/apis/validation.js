import { publicAxios } from '.';

// 카드 인증을 위한 함수
export const verifyCard = async cardData => {
  const res = await publicAxios.post('/v1/simple-consent/card/verify', cardData);
  return res.data;
};

// CMS 인증을 위한 함수
export const verifyCMS = async cmsData => {
  const res = await publicAxios.post('/v1/simple-consent/cms/verify', cmsData);
  return res.data;
};
