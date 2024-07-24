import { publicUploadFileAxios } from '.';

export const uploadSignature = async formData => {
  try {
    const res = await publicUploadFileAxios.post('/v1/simple-consent/sign', formData);
    return res.data.fileUrl;
  } catch (err) {
    console.error('Error uploading signature:', err);
    throw err;
  }
};
