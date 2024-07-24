import React from 'react';
import PagiNation from '@/components/common/PagiNation';
import SelectField from '@/components/common/selects/SelectField';
import InputWeb from '@/components/common/inputs/InputWeb';
import { formatProducts } from '@/utils/format/formatProducts';

const typeOtions = [
  { value: 'memberName', label: '회원명' },
  { value: 'contractDay', label: '약정일' },
  { value: 'contractProducts', label: '상품명' },
  { value: 'contractPrice', label: '계약금액' },
];

const typePlaceholers = {
  memberName: '회원명 입력',
  contractDay: '약정일 입력',
  contractProducts: '상품명 입력',
  contractPrice: '계약금액 이하',
};

const ContractList = ({
  searchType,
  setSearchType,
  searchTerm,
  setSearchTerm,
  contractList,
  handleSelectContract,
  currentPage,
  setCurrentPage,
  totalPages,
  pageGroup,
  setPageGroup,
}) => {
  return (
    <div className='w-2/5 p-6 overflow-auto'>
      <h2 className='text-2xl font-semibold mb-4 text-text_black'>계약 목록</h2>
      <div className='flex justify-between w-full my-2 '>
        <SelectField
          label=''
          classContainer='mr-5 w-1/3 h-full'
          classLabel='text-15 text-text_black font-700'
          classSelect='py-4 rounded-lg'
          value={searchType}
          options={typeOtions}
          onChange={e => setSearchType(e.target.value)}
        />
        <InputWeb
          id='searchTerm'
          type='text'
          label=''
          classContainer='w-full'
          placeholder={typePlaceholers[searchType]}
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
      </div>
      <table className='w-full mb-3'>
        <thead>
          <tr className='bg-table_col'>
            <th className='p-2 text-left text-text_black'>회원명</th>
            <th className='p-2 text-left text-text_black'>약정일</th>
            <th className='p-2 text-left text-text_black'>상품</th>
            <th className='p-2 text-left text-text_black'>계약금액</th>
          </tr>
        </thead>
        <tbody>
          {contractList.map(contract => (
            <tr
              key={contract.contractId}
              onClick={() => handleSelectContract(contract)}
              className='cursor-pointer hover:bg-gray-100'>
              <td className='border-b border-ipt_border p-2 text-text_black'>
                {contract.memberName}
              </td>
              <td className='border-b border-ipt_border p-2 text-text_black'>{`${contract.contractDay}일`}</td>
              <td className='border-b border-ipt_border p-2 text-text_black'>
                {formatProducts(contract.contractProducts)}
              </td>
              <td className='border-b border-ipt_border p-2 text-text_black'>{`${contract.contractPrice.toLocaleString()}원`}</td>
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

export default ContractList;
