import LoginForm from '@/components/LoginForm';
import backgroundImgSide from '@/assets/backgroundImgSide.png';


const LoginPage = () => {

  return (
    <>
      <LoginForm />
      <img
        className=' absolute right-0 top-0 h-[88vh] w-[44vw] hidden mobile:block'
        src={backgroundImgSide}
        alt='background'
      />
    </>
  );
};

export default LoginPage;
