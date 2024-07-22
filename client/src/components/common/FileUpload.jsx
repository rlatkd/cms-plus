const FileUpload = ({ label, onFileSelect }) => {
  const handleChangeFile = event => {
    const file = event.target.files[0];
    onFileSelect(file);
  };

  return (
    <div className='flex justify-center items-center w-1/4 h-[46px] ml-3'>
      <input type='file' id='file' className='hidden' onChange={handleChangeFile} />
      <label
        htmlFor='file'
        className='flex justify-center items-center cursor-pointer transition-all duration-200 hover:bg-mint_hover bg-mint text-white font-800 h-full w-full rounded-md'>
        {label}
      </label>
    </div>
  );
};

export default FileUpload;
