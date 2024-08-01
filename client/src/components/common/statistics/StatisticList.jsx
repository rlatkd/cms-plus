import { useState, useEffect } from 'react';

const StatisticList = ({
  statisticList,
  filteredList,
  setFilteredList,
  handleSelectContract,
}) => {
  const [selectedContractId, setSelectedContractId] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  const onSelected = contract => {
    setSelectedContractId(contract.contractId);
    handleSelectContract(contract);
  };

  useEffect(() => {
    const filtered = statisticList.filter(contract => 
      String(contract.contractId).toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredList(filtered);
  }, [searchTerm, statisticList, setFilteredList]);

  return (
    <div className='w-2/5 p-6'>
      <h2 className='text-2xl font-semibold mb-4 text-text_black'>계약 목록</h2>
      <div className='mb-4 flex'>
        <input
          type="text"
          className='flex-grow p-2 border rounded'
          placeholder="계약 ID로 검색해주세요."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>
      <div className='overflow-auto' style={{maxHeight: 'calc(100vh - 250px)'}}>
        <table className='w-full mb-3'>
          <thead>
            <tr className='bg-table_col'>
              <th className='p-2 text-left text-text_black'>계약ID</th>
              <th className='p-2 text-left text-text_black'>회원가입연도</th>
              <th className='p-2 text-left text-text_black'>계약기간</th>
              <th className='p-2 text-left text-text_black'>총 계약금액</th>
            </tr>
          </thead>
          <tbody>
          {filteredList.map((contract, index) => (
            <tr
              key={`${contract.contractId}-${index}`}
              onClick={() => onSelected(contract)}
              className={`cursor-pointer  ${
                selectedContractId === contract.contractId ? 'bg-blue-100' : 'hover:bg-gray-100'
              }`}>
              <td className='border-b border-ipt_border p-2 text-text_black'>
                {contract.contractId}
              </td>
              <td className='border-b border-ipt_border p-2 text-text_black'>{contract.enrollYear}</td>
              <td className='border-b border-ipt_border p-2 text-text_black'>
                {`${contract.contractDuration}일`}
              </td>
              <td className='border-b border-ipt_border p-2 text-text_black'>{`${contract.totalContractAmount.toLocaleString()}원`}</td>
            </tr>
          ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default StatisticList;