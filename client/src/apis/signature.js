import { publicUploadFileAxios } from '.';

export const uploadSignature = async formData => {
  try {
    const response = await publicUploadFileAxios.post('/v1/simple-consent/sign', formData);
    return response.data.fileUrl;
  } catch (error) {
    console.error('Error uploading signature:', error);
    throw error;
  }
};
