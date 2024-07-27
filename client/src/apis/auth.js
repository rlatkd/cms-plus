import { publicAxios } from '.';

// 회원가입
export const postJoin = async info => {
  const res = await publicAxios.post('/v1/vendor/auth/join', info);
  return res;
};

// 로그인
export const postLogin = async info => {
  const res = await publicAxios.post('/v1/vendor/auth/login', info);
  return res;
};

// 로그아웃
export const deleteLogout = async () => {
  const res = await publicAxios.delete('/v1/vendor/auth/logout');
  return res;
};

// 아이디 중복확인
export const getCheckUsername = async username => {
  const res = await publicAxios.get('/v1/vendor/auth/check-username', {
    params: { username: username },
  });
  return res;
};

// 리프레쉬 토큰 재발급
export const postRefreshToken = async () => {
  const res = await publicAxios.post('/v1/vendor/auth/refresh');
  return res;
};

// 인증번호 요청
export const postRequestAuthenticationNumber = async data => {
  const res = await publicAxios.post('/v1/vendor/auth/request-number', data);
  return res;
};

// 아이디 찾기
export const postFindIdentifier = async data => {
  const res = await publicAxios.post('/v1/vendor/auth/id-inquiry', data);
  return res;
};

// 비밀번호 찾기
export const postFindPassword = async data => {
  const res = await publicAxios.post('/v1/vendor/auth/pw-inquiry', data);
  return res;
};

// 비밀번호 재설정
export const postResetPassword = async data => {
  const res = await publicAxios.post('/v1/vendor/auth/pw-reset', data);
  return res;
};
