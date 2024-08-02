import { getMemberBasicInfoList } from '@/apis/member';
import BaseModal from '@/components/common/BaseModal';
import InputWeb from '@/components/common/inputs/InputWeb';
import PagiNation from '@/components/common/PagiNation';
import SelectField from '@/components/common/selects/SelectField';
import { useMemberBasicStore } from '@/stores/useMemberBasicStore';
import { formatId } from '@/utils/format/formatId';
import { formatPhone } from '@/utils/format/formatPhone';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const typeOtions = [
  { value: 'memberId', label: '회원번호' },
  { value: 'memberName', label: '회원이름' },
  { value: 'memberPhone', label: '휴대전화' },
];

const typePlaceholers = {
  memberId: '회원번호 입력',
  memberName: '회원명 입력',
  memberPhone: '휴대전화 입력',
};

const MemberChooseModal = ({ icon, isShowModal, setIsShowModal, modalTitle }) => {
  const [memberList, setMemberList] = useState([]);
  const [searchType, setSearchType] = useState('memberName');
  const [searchTerm, setSearchTerm] = useState('');
  const [totalPages, setTotalPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageGroup, setPageGroup] = useState(0);
  const { setBasicInfo } = useMemberBasicStore();

  const navigate = useNavigate();

  const handleSelectMember = member => {
    setBasicInfo({
      memberName: member.name,
      memberPhone: member.phone,
      memberEnrollDate: member.enrollDate,
      memberHomePhone: member.homePhone,
      memberEmail: member.email,
      memberAddress: {
        address: member.address.address,
        addressDetail: member.address.addressDetail,
        zipcode: member.address.zipcode,
      },
      memberMemo: member.memo,
    });
    setIsShowModal(false);
    navigate('/vendor/contracts/register', { state: { type: 'old' } });
  };

  const fetchMemberList = async (page = 1) => {
    try {
      const res = await getMemberBasicInfoList({
        [searchType]: searchTerm,
        page: page,
        size: 8,
      });
      setMemberList(res.data.content);
      setTotalPages(res.data.totalPage || 1);
    } catch (err) {
      console.error('계약생성 - 회원 기본정보 목록 조회 실패', err);
    }
  };

  useEffect(() => {
    fetchMemberList(currentPage);
  }, [currentPage]);

  useEffect(() => {
    fetchMemberList();
    setCurrentPage(1);
    setPageGroup(0);
  }, [searchTerm]);

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-640'}
      width={'w-640'}>
      <div className='relative w-full h-full p-1'>
        <div className='flex justify-between w-full mt-1 mb-1'>
          <SelectField
            classContainer='mr-3 w-1/3 h-full'
            classLabel='text-15 text-text_black font-700'
            classSelect='py-3 rounded-lg'
            value={searchType}
            options={typeOtions}
            onChange={e => setSearchType(e.target.value)}
          />
          <InputWeb
            id='searchTerm'
            type='text'
            classContainer='w-full'
            classInput='py-3'
            placeholder={typePlaceholers[searchType]}
            value={searchTerm}
            onChange={e => setSearchTerm(e.target.value)}
          />
        </div>
        <table className='w-full h-[415px] mb-3'>
          <thead>
            <tr className='flex  bg-table_col shadow-column'>
              <th className='w-3/12 py-2 pl-6 text-left text-text_black font-800 '>회원번호</th>
              <th className='w-2/12 py-2 pl-6 text-left text-text_black font-800 '>회원이름</th>
              <th className='w-4/12 py-2 pl-8 text-left text-text_black font-800 '>휴대전화</th>
              <th className='w-4/12 py-2 pl-8 text-left text-text_black font-800 '>회원등록일</th>
            </tr>
          </thead>
          <tbody className='flex flex-col justify-between h-full text-sm '>
            {memberList &&
              memberList.map(member => (
                <tr
                  key={member.id}
                  onClick={() => handleSelectMember(member)}
                  className='flex items-center h-full border-b border-ipt_border cursor-pointer hover:bg-ipt_disa'>
                  <td className='w-3/12 pl-6 text-left text-text_black'>{formatId(member.id)}</td>
                  <td className='w-2/12 pl-6 text-left text-text_black'>{member.name}</td>
                  <td className='w-4/12 pl-8 text-left text-text_black'>
                    {formatPhone(member.phone)}
                  </td>
                  <td className='w-4/12 pl-8 text-left text-text_black'>{member.enrollDate}</td>
                </tr>
              ))}
          </tbody>
        </table>
        <div className='absolute -bottom-4 w-full '>
          <PagiNation
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            totalPages={totalPages}
            pageGroup={pageGroup}
            setPageGroup={setPageGroup}
            buttonCount={5}
          />
        </div>
      </div>
    </BaseModal>
  );
};

export default MemberChooseModal;
