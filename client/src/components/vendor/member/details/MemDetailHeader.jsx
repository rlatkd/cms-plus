import User from '@/assets/User';
import clock from '@/assets/clock.svg';

const MemDetailHeader = ({ memberData }) => {
  return (
    <div className='sub-dashboard pb-6 mb-5 h-24 w-full flex justify-around items-center font-800 '>
      <div className='flex items-center text-text_grey'>
        <User fill='#344767' className='w-5 h-5 mr-2' />
        <p className='text-xl text-text_black mr-2'>{memberData.memberName}</p>
        <p className=' mr-2'>회원님</p>
        <p>({String(memberData.memberId).padStart(8, '0')})</p>
      </div>

      <div className='flex items-center text-text_grey'>
        <img src={clock} alt='clock' className='w-5 h-5 mr-2' />
        <p className='text-text_black mr-1'>등록일시 : </p>
        <span className=' mr-2'>{memberData.createdDateTime.replace('T', ' ').split('.')[0]}</span>
      </div>

      <div className='flex items-center text-text_grey'>
        <img src={clock} alt='clock' className='w-5 h-5 mr-2' />
        <p className='text-text_black mr-1'>변경일시 : </p>
        <span className=' mr-2'>{memberData.modifiedDateTime.replace('T', ' ').split('.')[0]}</span>
      </div>
    </div>
  );
};

export default MemDetailHeader;
