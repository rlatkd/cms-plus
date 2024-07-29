import { useNavigate, useParams } from 'react-router-dom';
import edit from '@/assets/edit.svg';
import remove from '@/assets/remove.svg';
import BasicInfoForm from '@/components/common/memberForm/BasicInfoForm';
import { deleteMember, getBillingListByMember, getContractListByMember } from '@/apis/member';
import { useMemberBasicStore } from '@/stores/useMemberBasicStore';
import useAlert from '@/hooks/useAlert';
import useConfirm from '@/hooks/useConfirm';

const MemDetailBasicInfo = ({ memberData }) => {
  const navigate = useNavigate();
  const onAlert = useAlert();
  const onConfirm = useConfirm();
  const { id: memberId } = useParams();
  const { setBasicInfo } = useMemberBasicStore();

  // <----- id값은 추후 변경 ----->
  const handleGoDetail = () => {
    updateBasicInfo(memberData);
    navigate(`/vendor/members/update/${memberId}`);
  };

  // <----- 회원 정보 zustand 저장 ----->
  const updateBasicInfo = data => {
    setBasicInfo({
      memberName: data.memberName,
      memberPhone: data.memberPhone,
      memberEnrollDate: data.memberEnrollDate,
      memberHomePhone: data.memberHomePhone,
      memberEmail: data.memberEmail,
      memberMemo: data.memberMemo,
      memberAddress: {
        address: data.memberAddress.address,
        addressDetail: data.memberAddress.addressDetail,
        zipcode: data.memberAddress.zipcode,
      },
    });
  };

  // <----- 회원의 계약, 청구 건수 API ----->
  const axiosContractBillingByMember = async () => {
    try {
      const contractRes = await getContractListByMember(memberId);
      const billingRes = await getBillingListByMember(memberId);
      console.log('!----회원의 계약, 청구 건수----!'); // 삭제예정
      const contractCount = contractRes.data.content.length;
      const billingCount = billingRes.data;

      let confirmMessage =
        contractCount === 0 && billingCount === 0
          ? `"${memberData.memberName}"님을 삭제 하시겠습니까?`
          : `${contractCount}건의 계약과 ${billingCount}건의 청구가 함께 삭제됩니다. "${memberData.memberName}"님을 삭제 하시겠습니까?`;

      const isDelete = await onConfirm({
        msg: confirmMessage,
        type: 'warning',
        title: '회원 삭제 확인',
      });
      if (isDelete) {
        axiosDeleteMember();
      }
    } catch (err) {
      console.error('axiosContractBillingByMember => ', err.response);
    }
  };

  // <----- 회원 삭제 API ----->
  const axiosDeleteMember = async () => {
    try {
      const res = await deleteMember(memberId);
      console.log('!----회원 삭제 성공----!'); // 삭제예정
      onAlert({
        msg: `"${memberData.memberName}"님의 정보가 삭제되었습니다!`,
        type: 'success',
        title: '삭제 성공',
      });
      navigate('/vendor/members');
    } catch (err) {
      onAlert({ msg: '회원 삭제에 실패하셨습니다.', type: 'error', title: '삭제 오류' });
      console.error('axiosdeleteMember => ', err.response);
    }
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
      <BasicInfoForm formType='DETAIL' memberData={memberData} />
    </div>
  );
};

export default MemDetailBasicInfo;
