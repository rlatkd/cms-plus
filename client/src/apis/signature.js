import { publicUploadFileAxios } from '.';

export const uploadSignature = async formData => {
  const res = await publicUploadFileAxios.post('/v1/simple-consent/sign', formData);
  return res.data.fileUrl;
};
