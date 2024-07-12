import { publicAxios } from '.';

// 회원가입
export const postJoin = async info => {
  try {
    return await publicAxios.post('/v1/vendor/auth/join', info);
  } catch (err) {
    console.error('회원가입 실패 =>', err.response.data);
    throw err;
  }
};

// 로그인
export const postLogin = async info => {
  try {
    return await publicAxios.post('/v1/vendor/auth/login', info);
  } catch (err) {
    console.error('로그인 실패 =>', err.response.data);
    throw err;
  }
};

// 아이디 중복확인
export const getCheckUsername = async username => {
  try {
    return await publicAxios.get('/v1/vendor/auth/check-username', {
      params: { username: username },
    });
  } catch (err) {
    console.error('아이디 중복확인 =>', err.response.data);
    throw err;
  }
};
