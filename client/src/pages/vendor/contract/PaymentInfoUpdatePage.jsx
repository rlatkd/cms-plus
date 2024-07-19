import { useNavigate } from 'react-router-dom';

const PaymentInfoUpdatePage = () => {
  const navigate = useNavigate();

  return (
    <div className='flex h-full w-full flex-col'>
      <div className='sub-dashboard relative mb-5 h-1/5 w-full'>progressivee</div>
      <div className='primary-dashboard relative h-4/5 w-full'>
        PaymentInfoUpdatePage
        <div className='absolute bottom-0 left-0 flex h-24 w-full justify-between p-6 font-bold'>
          Button
        </div>
      </div>
    </div>
  );
};

export default PaymentInfoUpdatePage;
