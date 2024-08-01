import NextButton from '@/components/common/buttons/StatusNextButton';
import PreviousButton from '@/components/common/buttons/StatusPreButton';
import BasicInfoForm from '@/components/common/memberForm/BasicInfoForm';
import ProgressBar from '@/components/common/ProgressBar';
import useAlert from '@/hooks/useAlert';
import useStatusStepper from '@/hooks/useStatusStepper';
import { useStatusStore } from '@/stores/useStatusStore';
import { useNavigate } from 'react-router-dom';
import close from '@/assets/close.svg';
import { useMemberBasicStore } from '@/stores/useMemberBasicStore';

const MemberRegisterPage = () => {
  const start = 0;
  const end = 4;
  const { status, reset, setStatus } = useStatusStore();
  const { handleClickPrevious, handleClickNext } = useStatusStepper('memberRegister', start, end);
  const navigate = useNavigate();
  const onAlert = useAlert();

  const { basicInfo, resetBasicInfo } = useMemberBasicStore();

  return (
    <>
      <div className='up-dashboard relative flex justify-center items-center mb-4 w-full desktop:h-[18%]'>
        <ProgressBar steps={['기본정보']} />
        <img
          src={close}
          alt='back'
          className='absolute right-6 top-6 cursor-pointer w-4 h-4'
          onClick={() => navigate(-1)}
        />
      </div>
      <div className='primary-dashboard flex flex-col relative h-[1000px] large_desktop:h-[80%] '>
        <div className='flex items-center h-[50px] px-2 pb-[10px] '>
          <p className='text-text_black text-xl font-800'>기본정보</p>
        </div>
        <BasicInfoForm formType={'CREATE'} />
        <div className='absolute bottom-0 left-0 flex h-[65px] w-full justify-between px-7 pb-5 font-800 text-lg '>
          <PreviousButton
            onClick={handleClickPrevious}
            status={status}
            type={'memberRegister'}
            start={start}
            end={end}
          />
          <NextButton
            onClick={() => {
              handleClickNext();
            }}
            status={status}
            type={'memberRegister'}
            end={end}
          />
        </div>
      </div>
    </>
  );
};

export default MemberRegisterPage;
