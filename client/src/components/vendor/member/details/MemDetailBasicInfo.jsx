import { useNavigate, useParams } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import remove from '@/assets/remove.svg';
import BasicInfoForm from '@/components/common/memberForm/BasicInfoForm';
import { deleteMember, getBillingListByMember, getContractListByMember } from '@/apis/member';
import { useContext, useState } from 'react';
import AlertContext from '@/utils/dialog/alert/AlertContext';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';
import { useMemberBasicStore } from '@/stores/useMemberBasicStore';

const MemDetailBasicInfo = () => {
  const { basicInfo } = useMemberBasicStore();
  const navigate = useNavigate();

  const { id: memberId } = useParams();

  // id값은 추후 변경
  const handleGoDetail = () => {
    navigate(`/vendor/members/update/${memberId}`);
  };

  // <------ 회원의 계약, 청구 건수 API ------>
  const axiosContractBillingByMember = async () => {
    try {
      const contractRes = await getContractListByMember(memberId);
      const billingRes = await getBillingListByMember(memberId);
      console.log('!----회원의 계약, 청구 건수----!'); // 삭제예정

      const contractCount = contractRes.data.content.length;
      const billingCount = billingRes.data.length;
      let isDelete = false;

      if (contractCount === 0 && billingCount === 0) {
        isDelete = await confirmClick(`"${basicInfo.memberName}"님을 삭제 하시겠습니까?`);
        if (isDelete) {
          console.log(isDelete);
          axiosdeleteMember();
        }
      } else {
        isDelete = await confirmClick(
          `"${contractCount}건"의 계약과 "${billingCount}건"의 청구가 함께 삭제됩니다. "${basicInfo.memberName}"님을 삭제 하시겠습니까?`
        );
        if (isDelete) {
          axiosdeleteMember();
        }
      }
    } catch (err) {
      console.error('axiosContractBillingByMember => ', err.response.data);
    }
  };

  // <------ 회원 삭제 API ------>
  const axiosdeleteMember = async () => {
    try {
      const res = await deleteMember(memberId);
      console.log(res);
      console.log('!----회원 삭제 성공----!'); // 삭제예정
      onAlertClick(`"${basicInfo.memberName}"님의 정보가 삭제되었습니다!`);
      navigate('/vendor/members');
    } catch (err) {
      console.error('axiosdeleteMember => ', err.response.data);
    }
  };

  // <------ Alert창 ------>
  const { alert: alertComp } = useContext(AlertContext);
  const onAlertClick = async message => {
    const result = await alertComp(message);
  };

  // <------ Confirm창 ------>
  const { confirm: confrimComp } = useContext(ConfirmContext);
  const confirmClick = async message => {
    return await confrimComp(message);
  };

  return (
    <div className='flex flex-col sub-dashboard pb-6  mb-5 h-640 w-full'>
      <div className='flex justify-between items-center border-b border-ipt_border px-2 pt-1 pb-3'>
        <p className='text-text_black text-xl font-800'>기본정보</p>

        <div className='flex'>
          <button
            className='flex justify-between items-center px-4 py-2 text-text_grey
            font-700 rounded-md border border-text_grey cursor-pointer'
            onClick={axiosContractBillingByMember}>
            <img src={remove} alt='remove' className='mr-2 ' />
            <p>회원삭제</p>
          </button>

          <button
            className='flex justify-between items-center px-4 py-2 ml-4 text-mint
            font-700 rounded-md border border-mint cursor-pointer'
            onClick={handleGoDetail}>
            <img src={edit} alt='edit' className='mr-2 ' />
            <p>회원수정</p>
          </button>
        </div>
      </div>
      <BasicInfoForm formType='DETAIL' />
    </div>
  );
};

export default MemDetailBasicInfo;
