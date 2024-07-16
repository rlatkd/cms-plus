const MoveButton = ({ imgSrc, buttonText, onClick }) => {
  return (
    <div className=' flex justify-center items-center rounded-lg bg-white border border-mint hover:border-mint_hover mr-1'>
      <button
        className='flex items-center rounded-lg bg-mint py-2 px-5 font-700 text-white text-sm border border-white hover:bg-mint_hover transition-all duration-200'
        onClick={onClick}>
        <img className='mr-2 w-5 h-5' src={imgSrc} alt='button icon' />
        <p>{buttonText}</p>
      </button>
    </div>
  );
};

export default MoveButton;
