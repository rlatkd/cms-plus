import SignupForm from '@/components/SignupForm';
import backgroundImgUp from '@/assets/backgroundImgUp.png';

const SignupPage = () => {
  return (
    <div className='p-3 relative'>
      <img className='h-[40vh] w-[100vw]' src={backgroundImgUp} alt='background' />
      <SignupForm />
    </div>
  );
};

export default SignupPage;
