import { useState, useEffect, useCallback } from 'react';
import { getStatisticList } from '@/apis/statistic';
import useDebounce from '@/hooks/useDebounce';
import StatisticList from '@/components/common/statistics/StatisticList';
import useAlert from '@/hooks/useAlert';

const Statistics = () => {
  const [statisticList, setStatisticList] = useState([]);
  const [searchType, setSearchType] = useState('memberName');
  const [searchTerm, setSearchTerm] = useState('');
  const [totalPages, setTotalPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageGroup, setPageGroup] = useState(0);
  const [selectedContract, setSelectedContract] = useState(null);
  const debouncedSearchTerm = useDebounce(searchTerm, 500);

  const onAlert = useAlert();

  const fetchStatisticList = useCallback(
    async (page = currentPage) => {
      try {
        const res = await getStatisticList({
          [searchType]: searchTerm,
          page,
          size: 10,
        });
        setStatisticList(res.data);
        setTotalPages(Math.ceil(res.data.length / 10)); // 임시로 페이지 수 계산
      } catch (err) {
        console.error('통계 - 계약 목록 조회 실패', err);
        onAlert('계약 목록을 불러오는데 실패했습니다.', 'error');
      }
    },
    [searchType, searchTerm, currentPage, onAlert]
  );

  useEffect(() => {
    fetchStatisticList();
    setCurrentPage(1);
    setPageGroup(0);
  }, [debouncedSearchTerm, fetchStatisticList]);

  useEffect(() => {
    fetchStatisticList(currentPage);
  }, [currentPage, fetchStatisticList]);

  const handleSelectContract = useCallback((contract) => {
    setSelectedContract(contract);
  }, []);

  return (
    <div className='primary-dashboard flex flex-col h-full w-full'>
      <div className='flex flex-1 overflow-hidden'>
        <StatisticList
          searchType={searchType}
          setSearchType={setSearchType}
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
          statisticList={statisticList}
          handleSelectContract={handleSelectContract}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalPages={totalPages}
          pageGroup={pageGroup}
          setPageGroup={setPageGroup}
        />

        <div className='w-px bg-ipt_border' />

        <div className='w-3/5 p-6 flex flex-col h-full overflow-hidden'>
          <h2 className='text-2xl font-semibold mb-4 text-text_black'>회원 계약 갱신 예측</h2>
          {selectedContract ? (
            <div className='flex-1 overflow-hidden flex flex-col'>
              <p className='text-center text-xl text-gray-500'>
                선택된 회원: {selectedContract.memberName}
              </p>
              <p className='text-center text-xl text-gray-500'>
                가입일: {selectedContract.enrollDate}
              </p>
              <p className='text-center text-xl text-gray-500'>
                계약기간: {selectedContract.contractDuration}일
              </p>
              <p className='text-center text-xl text-gray-500'>
                총 계약금액: {selectedContract.totalContractAmount.toLocaleString()}원
              </p>
              {/* 여기에 추가적인 예측 정보를 표시할 수 있습니다 */}
            </div>
          ) : (
            <div className='flex-1 flex items-center justify-center'>
              <p className='text-center text-xl text-gray-500'>회원을 선택해주세요.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Statistics;