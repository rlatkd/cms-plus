import { privateAxios } from '.';

// 회원 등록
export const createMember = async memberData => {
  try {
    const res = await privateAxios.post('/v1/vendor/management/members', memberData);
    return res;
  } catch (err) {
    console.error('회원 등록 실패 => ', err.response);
    throw err;
  }
};

// 회원 목록 조회
export const getMemberList = async (searchParams = {}) => {
  try {
    const res = await privateAxios.get('/v1/vendor/management/members', {
      params: {
        ...searchParams,
      },
    });
    return res;
  } catch (err) {
    console.error('회원 목록 조회 실패 => ', err.response);
    throw err;
  }
};

// 회원 상세 조회
export const getMemberDetail = async memberId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/management/members/${memberId}`);
    return res;
  } catch (err) {
    console.error('회원 상세 조회 실패 => ', err.response.data);
    throw err;
  }
};

// 회원 상세 조회 - 계약리스트
export const getMemberDetailContractList = async (memberId, searchParams = {}) => {
  try {
    const res = await privateAxios.get(`/v1/vendor/management/members/contracts/${memberId}`, {
      params: {
        ...searchParams,
      },
    });
    return res;
  } catch (err) {
    console.error('회원 상세 - 계약리스트 조회 실패', err.response.data);
    throw err;
  }
};

// 회원 수정
export const updateMember = async (memberId, memberData) => {
  try {
    const res = await privateAxios.put(`/v1/vendor/members/${memberId}`, memberData);
    return res;
  } catch (err) {
    console.error('회원 수정 실패 => ', err.response.data);
    throw err;
  }
};

// 회원 삭제
export const deleteMember = async memberId => {
  try {
    const res = await privateAxios.delete(`/v1/vendor/members/${memberId}`);
    return res;
  } catch (err) {
    console.error('회원 삭제 실패 => ', err.response.data);
    throw err;
  }
};
