import React from 'react';
import PagiNation from '@/components/common/PagiNation';
import { formatProducts } from '@/utils/formatProducts';

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
  setPageGroup 
}) => {
  return (
    <div className="w-2/5 p-6 overflow-auto">
      <h2 className="text-2xl font-semibold mb-4">계약 목록</h2>
      <div className="flex space-x-2 mb-4">
        <select
          value={searchType}
          onChange={(e) => setSearchType(e.target.value)}
          className="p-2 border rounded"
        >
          <option value="memberName">회원이름</option>
          <option value="contractDay">약정일</option>
          <option value="contractProducts">상품</option>
          <option value="contractPrice">계약금액</option>
        </select>
        <input
          placeholder="검색어 입력"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="flex-1 p-2 border rounded"
        />
      </div>
      <table className="w-full">
        <thead>
          <tr className="bg-gray-100">
            <th className="p-2 text-left">회원이름</th>
            <th className="p-2 text-left">약정일</th>
            <th className="p-2 text-left">상품</th>
            <th className="p-2 text-left">계약금액</th>
          </tr>
        </thead>
        <tbody>
          {contractList.map((contract) => (
            <tr
              key={contract.contractId}
              onClick={() => handleSelectContract(contract)}
              className="cursor-pointer hover:bg-gray-50"
            >
              <td className="border p-2">{contract.memberName}</td>
              <td className="border p-2">{`${contract.contractDay}일`}</td>
              <td className="border p-2">{formatProducts(contract.contractProducts)}</td>
              <td className="border p-2">{`${contract.contractPrice.toLocaleString()}원`}</td>
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