import clock from '@/assets/clock.svg';

const ConDetailHeader = ({ contractData }) => {
  return (
    <div className='sub-dashboard pb-6 mb-5 h-24 w-full flex justify-around items-center font-800 '>
      <div className='flex items-center text-text_grey '>
        <p className='text-xl text-text_black mr-2'>{contractData.contractName}</p>
        <p>({String(contractData.contractId).padStart(8, '0')})</p>
      </div>

      <div className='flex items-center text-text_grey'>
        <img src={clock} alt='clock' className='w-5 h-5 mr-2' />
        <p className='text-text_black mr-1'>등록일시 : </p>
        <span className=' mr-2'>{contractData.createdDateTime.split('T')[0]}</span>
        <span>{contractData.createdDateTime.split('T')[1]}</span>
      </div>

      <div className='flex items-center text-text_grey'>
        <img src={clock} alt='clock' className='w-5 h-5 mr-2' />
        <p className='text-text_black mr-1'>변경일시 : </p>
        <span className=' mr-2'>{contractData.modifiedDateTime.split('T')[0]}</span>
        <span>{contractData.modifiedDateTime.split('T')[1]}</span>
      </div>
    </div>
  );
};

export default ConDetailHeader;