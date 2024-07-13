import { privateAxios } from '.';

// 회원 목록 조회
export const getMemberList = async () => {
  try {
    const res = await privateAxios.get('/v1/vendor/management/members', {
      params: {
        page: 1,
        size: 3,
      },
    });
    return res;
  } catch (err) {
    console.log('회원 목록 조회', err.response);
    throw err;
  }
};
