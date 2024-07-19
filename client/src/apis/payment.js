import { privateAxios } from '.';

// 납부자-카드 결제
export const requestCardPayment = async paymentData => {
  try {
    const res = await privateAxios.post('/v1/kafka/payment/card', paymentData);
    return res;
  } catch (err) {
    console.error('납부자 카드 결제 실패 => ', err.response.data);
    throw err;
  }
};

// 납부자-계좌 결제
export const requestAccountPayment = async paymentData => {
  try {
    const res = await privateAxios.post('/v1/kafka/payment/account', paymentData);
    return res;
  } catch (err) {
    console.error('납부자 카드 결제 실패 => ', err.response.data);
    throw err;
  }
};

// 가상계좌 결제
export const requestVirtualAccountPayment = async paymentData => {
  try {
    const res = await privateAxios.post('/v1/kafka/payment/virtual-account', paymentData);
    return res;
  } catch (err) {
    console.error('가상계좌 결제 실패 => ', err.response.data);
    throw err;
  }
};
