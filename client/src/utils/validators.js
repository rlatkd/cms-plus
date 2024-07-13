import { regex } from './regex';

export const validateField = (id, value) => {
  switch (id) {
    case 'name': // 1.회원명 정규식
      return regex.name.test(value);

    case 'username': // 2.아이디 정규식
      return regex.username.test(value);

    case 'password': // 3.비밀번호 정규식
      return regex.password.test(value);

    case 'email': // 4.이메일 정규식
      return regex.email.test(value);

    case 'phone': // 5.휴대전화 정규식
      return regex.phone.test(value);

    case 'homePhone': // 4.유선전화 정규식
      return regex.homePhone.test(value);

    default:
      return true;
  }
};
