import SelectField from '@/components/common/selects/SelectField';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useMemberPaymentStore } from '@/stores/useMemberPaymentStore';
import FileUpload from '@/components/common/FileUpload';
import cardOptions from '@/utils/bank/cardOptions';
import InputCalendar from '@/components/common/inputs/InputCalendar';

const CmsMethodForm = ({ paymentMethod, formType }) => {
  const {
    paymentMethodInfoReq_Cms,
    setPaymentMethodInfoReq_Cms,
    paymentTypeInfoReq_Auto,
    setPaymentTypeInfoReq_Auto,
  } = useMemberPaymentStore();

  // <------ 셀렉터 필드 은행명 변경 ------>
  const handleChangeSelect = e => {
    setPaymentMethodInfoReq_Cms({ bank: e.target.value });
  };

  // <------ 인풋 필드 입력값 변경 ------>
  const handleChangeInput = e => {
    const { id, value } = e.target;
    setPaymentMethodInfoReq_Cms({ [id]: value });
  };

  // <------ 파일 업로드 ------>
  const handleUploadFile = file => {
    // TODO
    // simpleConsentReqDateTime 동의 요청시간 일단은 현재시각으로 설정
    const currentTime = new Date().toISOString();
    setPaymentTypeInfoReq_Auto({
      consentImgUrl: URL.createObjectURL(file),
      consetImgName: file.name,
      simpleConsentReqDateTime: currentTime,
    });
  };

  // TODO
  // <------ 정규표현식 예외처리 ------>

  return (
    <>
      <div className='flex mt-6 mb-2'>
        <div className='flex flex-1 items-end '>
          <SelectField
            label='은행'
            classContainer='w-1/4'
            classLabel='text-15 text-text_black font-700 ml-2'
            classSelect='py-3 pr-20 p-4 rounded-lg'
            options={cardOptions}
            required
            value={paymentMethodInfoReq_Cms.bank}
            onChange={handleChangeSelect}
          />
          <InputWeb
            id='accountOwner'
            label='예금주'
            placeholder='최대 20자리'
            type='text'
            required
            classContainer='w-3/4 ml-3 mb-4'
            classInput='py-3 pr-20'
            value={paymentMethodInfoReq_Cms.accountOwner}
            onChange={handleChangeInput}
          />
        </div>
        {/* TODO */}
        {/* 파일 삭제 버튼이 필요함 */}
        <div className='flex flex-1 items-end mb-4 ml-5 '>
          <InputWeb
            id='agreement'
            label='동의서'
            placeholder='최대 20자리'
            type='close'
            classContainer='w-3/4'
            classInput='py-3 pr-20'
            value={paymentTypeInfoReq_Auto.consetImgName}
            readOnly
          />
          <FileUpload label='동의서 파일 선택' onFileSelect={handleUploadFile} />
        </div>
      </div>
      <div className='flex'>
        <InputCalendar
          id='accountOwnerBirth'
          label='생년월일'
          placeholder='생년월일8자리'
          required
          height='45px'
          width='100%'
          classContainer='flex-1'
          value={paymentMethodInfoReq_Cms.accountOwnerBirth}
          handleChangeValue={handleChangeInput}
        />
        <div className='relative flex flex-1 items-end ml-5'>
          <InputWeb
            id='accountNumber'
            label='계좌번호'
            placeholder='최대 14자리'
            type='text'
            required
            classContainer='w-3/4'
            classInput='py-3 pr-20'
            value={paymentMethodInfoReq_Cms.accountNumber}
            onChange={handleChangeInput}
          />
          <button className='h-[46px] w-1/4 bg-mint rounded-lg font-800 text-white transition-all duration-200 hover:bg-mint_hover ml-3'>
            계좌번호 인증
          </button>
        </div>
      </div>
    </>
  );
};

export default CmsMethodForm;
