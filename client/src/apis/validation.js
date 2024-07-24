import { publicAxios } from '.';

// 카드 인증을 위한 함수
export const verifyCard = async cardData => {
  try {
    const response = await publicAxios.post('/v1/simple-consent/card/verify', cardData);
    return response.data;
  } catch (error) {
    console.error('Error verifying card:', error);
    throw error;
  }
};

// CMS 인증을 위한 함수
export const verifyCMS = async cmsData => {
  try {
    const response = await publicAxios.post('/v1/simple-consent/cms/verify', cmsData);
    return response.data;
  } catch (error) {
    console.error('Error verifying CMS:', error);
    throw error;
  }
};
