import PagiNation from '@/components/common/PagiNation';
import SelectField from '@/components/common/selects/SelectField';
import InputWeb from '@/components/common/inputs/InputWeb';
import { useState } from 'react';

const typeOptions = [
  { value: 'memberName', label: '회원명' },
  { value: 'enrollDate', label: '회원가입일' },
  { value: 'contractDuration', label: '계약기간' },
  { value: 'totalContractAmount', label: '총 계약금액' },
];

const typePlaceholders = {
  memberName: '회원명 입력',
  enrollDate: '회원가입일 입력',
  contractDuration: '계약기간 입력',
  totalContractAmount: '총 계약금액 이하',
};

const StatisticList = ({
  searchType,
  setSearchType,
  searchTerm,
  setSearchTerm,
  statisticList,
  handleSelectContract,
  currentPage,
  setCurrentPage,
  totalPages,
  pageGroup,
  setPageGroup,
}) => {
  const [selectedContractId, setSelectedContractId] = useState(null);

  const onSelected = contract => {
    setSelectedContractId(contract.memberId);
    handleSelectContract(contract);
  };

  return (
    <div className='w-2/5 p-6'>
      <h2 className='text-2xl font-semibold mb-4 text-text_black'>회원 목록</h2>
      <div className='flex justify-between w-full my-2 '>
        <SelectField
          label=''
          classContainer='mr-5 w-1/3 h-full'
          classLabel='text-15 text-text_black font-700'
          classSelect='py-4 rounded-lg'
          value={searchType}
          options={typeOptions}
          onChange={e => setSearchType(e.target.value)}
        />
        <InputWeb
          id='searchTerm'
          type='text'
          label=''
          classContainer='w-full'
          placeholder={typePlaceholders[searchType]}
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
      </div>
      <table className='w-full mb-3'>
        <thead>
          <tr className='bg-table_col'>
            <th className='p-2 text-left text-text_black'>회원명</th>
            <th className='p-2 text-left text-text_black'>회원가입일</th>
            <th className='p-2 text-left text-text_black'>계약기간</th>
            <th className='p-2 text-left text-text_black'>총 계약금액</th>
          </tr>
        </thead>
        <tbody>
          {statisticList.map(contract => (
            <tr
              key={contract.memberId}
              onClick={() => onSelected(contract)}
              className={`cursor-pointer  ${
                selectedContractId === contract.memberId ? 'bg-blue-100' : 'hover:bg-gray-100'
              }`}>
              <td className='border-b border-ipt_border p-2 text-text_black'>
                {contract.memberName}
              </td>
              <td className='border-b border-ipt_border p-2 text-text_black'>{contract.enrollDate}</td>
              <td className='border-b border-ipt_border p-2 text-text_black'>
                {`${contract.contractDuration}일`}
              </td>
              <td className='border-b border-ipt_border p-2 text-text_black'>{`${contract.totalContractAmount.toLocaleString()}원`}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <PagiNation
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPages={totalPages}
        pageGroup={pageGroup}
        setPageGroup={setPageGroup}
        buttonCount={5}
      />
    </div>
  );
};

export default StatisticList;