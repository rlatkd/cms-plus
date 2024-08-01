const PreviousButton = ({ onClick, status, start, type = '', end }) => {
  if (status === start || status === end) {
    return null;
  }

  return (
    <button
      tabIndex='-1'
      className={`${type === 'memberRegister' ? 'w-28' : 'w-1/3'}
                mr-3 rounded-lg border border-mint text-mint bg-white`}
      onClick={onClick}>
      이전
    </button>
  );
};

export default PreviousButton;
