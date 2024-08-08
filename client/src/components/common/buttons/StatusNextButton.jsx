const NextButton = ({ onClick, status, type = '', end, onPayment }) => {
  let buttonText = '다음';

  if (status === 0) {
    if (type === 'simpconsent') {
      buttonText = '시작하기';
    }
    if (type === 'invoice') {
      buttonText = '청구서 확인';
    }
  } else if (status === end) {
    buttonText = '확인';
    if (type === 'memberRegister') {
      buttonText = '저장';
    }
  }

  const handlePayment = async () => {
    if (status === end - 2 && onPayment) {
      await onPayment();
    }
    onClick();
  };

  return (
    <button
      tabIndex='-1'
      className={` ${type === 'memberRegister' ? 'w-28' : 'flex-1'}   focus:outline-none 
                  ml-auto rounded-lg bg-mint text-white transition-all duration-200 hover:bg-mint_hover`}
      onClick={handlePayment}>
      {buttonText}
    </button>
  );
};

export default NextButton;
