import formatNumber from '@/utils/format/formatNumber';
import { useNavigate } from 'react-router-dom';

const TopMembers = ({ topFive }) => {
  const navigate = useNavigate();

  const handleMemberClick = memberId => {
    navigate(`/vendor/members/detail/${memberId}`);
  };

  return (
    <div className='bg-white rounded-xl shadow-md p-6 transition duration-300 ease-in-out hover:shadow-lg'>
      <h2 className='text-xl font-bold mb-4 text-gray-800'>Top 5 회원</h2>
      <div className='overflow-x-auto'>
        <table className='w-full'>
          <thead>
            <tr className='bg-gray-50'>
              <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                회원명
              </th>
              <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                총 계약액
              </th>
              <th className='p-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                계약 수
              </th>
            </tr>
          </thead>
          <tbody className='bg-white divide-y divide-gray-200'>
            {topFive?.map(mem => (
              <tr
                key={mem.memberId}
                className='hover:bg-gray-50 cursor-pointer transition duration-150 ease-in-out'
                onClick={() => handleMemberClick(mem.memberId)}>
                <td className='px-3 py-4 whitespace-nowrap text-sm font-medium text-gray-900'>
                  {mem.memberName}
                </td>
                <td className='px-3 py-4 whitespace-nowrap text-sm text-gray-500'>
                  ₩{formatNumber(mem.totalContractPrice)}
                </td>
                <td className='px-3 py-4 whitespace-nowrap text-sm text-gray-500'>
                  {formatNumber(mem.contractCount)}건
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default TopMembers;
