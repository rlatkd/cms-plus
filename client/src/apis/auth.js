import { publicAxios } from '.';

// 회원가입
export const postJoin = async info => {
  try {
    const res = await publicAxios.post('/v1/vendor/auth/join', info);
    return res;
  } catch (err) {
    console.error('회원가입 실패 =>', err.response.data);
    throw err;
  }
};

// 로그인
export const postLogin = async info => {
  try {
    const res = await publicAxios.post('/v1/vendor/auth/login', info);
    return res;
  } catch (err) {
    console.error('로그인 실패 =>', err.response.data);
    throw err;
  }
};

// 로그아웃
export const deleteLogout = async () => {
  try {
    const res = await publicAxios.delete('/v1/vendor/auth/logout');
    return res;
  } catch (err) {
    console.error('로그아웃 실패', err.response.data);
    throw err;
  }
};

// 아이디 중복확인
export const getCheckUsername = async username => {
  try {
    const res = await publicAxios.get('/v1/vendor/auth/check-username', {
      params: { username: username },
    });
    return res;
  } catch (err) {
    console.error('아이디 중복확인 =>', err.response.data);
    throw err;
  }
};

// 리프레쉬 토큰 재발급
export const postRefreshToken = async () => {
  try {
    const res = await publicAxios.post('/v1/vendor/auth/refresh');
    return res;
  } catch (err) {
    console.error('리프레쉬 토큰 재발급 실패', err.response.data);
    throw err;
  }
};
