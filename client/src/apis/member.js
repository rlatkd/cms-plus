import { privateAxios } from '.';

// 회원 등록
export const postCreateMember = async memberData => {
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

// 회원 기본정보 목록 조회
export const getMemberBasicInfoList = async (searchParams = {}) => {
  try {
    const res = await privateAxios.get('/v1/vendor/management/basicinfo/members', {
      params: {
        ...searchParams,
      },
    });
    return res;
  } catch (err) {
    console.error('회원 기보정보 목록 조회 실패 => ', err.response);
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

// 회원 수정 - 기본 정보
export const updateMemberBaic = async (memberId, memberData) => {
  try {
    const res = await privateAxios.put(`/v1/vendor/management/members/${memberId}`, memberData);
    return res;
  } catch (err) {
    console.error('회원 수정 실패 => ', err.response.data);
    throw err;
  }
};

// 회원 삭제
export const deleteMember = async memberId => {
  try {
    const res = await privateAxios.delete(`/v1/vendor/management/members/${memberId}`);
    return res;
  } catch (err) {
    console.error('회원 삭제 실패 => ', err.response.data);
    throw err;
  }
};

// 회원 삭제 - 회원의 진행중인 계약수
export const getContractListByMember = async memberId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/management/members/contracts/${memberId}`);
    return res;
  } catch (err) {
    console.error('회원 삭제 - 회원의 진행중인 계약수 => ', err.response.data);
    throw err;
  }
};

// 회원 삭제 - 회원의 진행중인 청구수
export const getBillingListByMember = async memberId => {
  try {
    const res = await privateAxios.get(`/v1/vendor/management/members/${memberId}/billing`);
    return res;
  } catch (err) {
    console.error('회원 삭제 - 회원의 진행중인 청구수 => ', err.response.data);
    throw err;
  }
};

// 회원 엑셀 -> json
export const convertMember = async formData => {
  try {
    const res = await privateAxios.post('/v1/vendor/management/convert', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return res;
  } catch (err) {
    console.error('회원 엑셀 -> json 실패', err.response);
    throw err;
  }
};

// 회원 json -> 저장
export const uploadMembers = async members => {
  try {
    const res = await privateAxios.post('/v1/vendor/management/upload', members);
    return res;
  } catch (err) {
    console.error('회원 json -> 저장 실패', err.response);
    throw err;
  }
};
