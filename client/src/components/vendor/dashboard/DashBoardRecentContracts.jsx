import formatNumber from '@/utils/format/formatNumber';
import { useNavigate } from 'react-router-dom';

const RecentContracts = ({ contracts }) => {
  const navigate = useNavigate();

  const handleContractClick = contractId => {
    navigate(`/vendor/contracts/detail/${contractId}`);
  };

  return (
    <div className='bg-white rounded-xl shadow-md p-6 transition duration-300 ease-in-out hover:shadow-lg'>
      <h2 className='text-xl font-800 mb-4 text-text_black'>최근 계약</h2>
      <div className='overflow-x-auto'>
        <table className='w-full'>
          <thead>
            <tr className='bg-table_col'>
              <th className='p-3 text-left text-xs font-400 text-text_black uppercase tracking-wider'>
                계약 생성일
              </th>
              <th className='p-3 text-left text-xs font-400 text-text_black uppercase tracking-wider'>
                회원명
              </th>
              <th className='p-3 text-left text-xs font-400 text-text_black uppercase tracking-wider'>
                계약액
              </th>
              <th className='p-3 text-left text-xs font-400 text-text_black uppercase tracking-wider'>
                기간
              </th>
            </tr>
          </thead>
          <tbody className='bg-white divide-y divide-gray-200'>
            {contracts?.map(contract => (
              <tr
                key={contract.contractId}
                className='hover:bg-gray-50 cursor-pointer transition duration-150 ease-in-out'
                onClick={() => handleContractClick(contract.contractId)}>
                <td className='px-3 py-4 whitespace-nowrap text-sm text-text_grey'>
                  {contract.contractCreatedDate}
                </td>
                <td className='px-3 py-4 whitespace-nowrap text-sm font-700 text-text_black'>
                  {contract.memberName}
                </td>
                <td className='px-3 py-4 whitespace-nowrap text-sm text-text_grey'>
                  ₩{formatNumber(contract.totalContractPrice)}
                </td>
                <td className='px-3 py-4 whitespace-nowrap text-sm text-text_grey'>
                  {contract.contractMonth}개월
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default RecentContracts;
