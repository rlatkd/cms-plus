const NextButton = ({ onClick, status, type = '', end }) => {
  let buttonText = '다음';

  if (status === 0) {
    if (type === 'simpconsent') {
      buttonText = '시작하기';
    } else if (type === 'invoice') {
      buttonText = '청구서 확인';
    }
  } else if (status === end) {
    buttonText = '확인';
  }

  const width = type === 'memberregister' ? 'w-28' : 'flex-1';

  return (
    <button className={`${width} ml-auto rounded-lg bg-mint text-white`} onClick={onClick}>
      {buttonText}
    </button>
  );
};

export default NextButton;
