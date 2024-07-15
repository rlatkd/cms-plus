// 페이지 버튼 렌더링 함수
const PagiNation = ({
  currentPage,
  setCurrentPage,
  totalPages,
  pageGroup,
  setPageGroup,
  page,
  setPage,
}) => {
  const startPage = pageGroup * 5 + 1;
  const endPage = Math.min(startPage + 4, totalPages);
  const buttons = [];

  // 페이지 변경 이벤트핸들러
  const handlePageChange = newPage => {
    if (newPage !== currentPage) {
      setCurrentPage(newPage);
      setPage(newPage);
    }
  };

  // 페이지 그룹 변경 이벤트핸들러
  const handlePageGroupChange = direction => {
    if (direction === 'next') {
      setPageGroup(prev => prev + 1);
      setPage((pageGroup + 1) * 5 + 1);
      setCurrentPage((pageGroup + 1) * 5 + 1);
    } else if (direction === 'prev' && pageGroup > 0) {
      setPageGroup(prev => prev - 1);
      setPage((pageGroup - 1) * 5 + 1);
      setCurrentPage((pageGroup - 1) * 5 + 1);
    }
  };

  // 동적으로 css 함수 로직 개선 필요 (밑에 return div로 옮기고 싶음)
  for (let i = startPage; i <= endPage; i++) {
    buttons.push(
      <button
        key={i}
        className={`mx-1 px-3 py-1 border rounded-lg w-8 h-8 flex items-center justify-center ${i === page ? 'bg-mint hover:bg-mint_hover text-white' : 'bg-white border border-white'}`}
        onClick={() => handlePageChange(i)}>
        {i}
      </button>
    );
  }

  return (
    <div className='flex items-center'>
      {/* 이전 버튼 */}
      <button
        key='prev'
        className={`mx-1 px-3 py-1 border rounded w-24 h-8 flex items-center justify-center ${pageGroup === 0 ? 'invisible' : 'bg-white border border-white'}`}
        onClick={() => handlePageGroupChange('prev')}
        disabled={pageGroup === 0}>
        {'<'}&nbsp;&nbsp;&nbsp;{'이전'}
      </button>

      {/* 1,2,3,4,5 버튼 영역 */}
      <div className='flex justify-center'>{buttons}</div>

      {/* 다음 버튼 */}
      <button
        key='next'
        className={`mx-1 px-3 py-1 border rounded w-24 h-8 flex items-center justify-center ${endPage >= totalPages ? 'invisible' : 'bg-white border border-white'}`}
        onClick={() => handlePageGroupChange('next')}
        disabled={endPage >= totalPages}>
        {'다음'}&nbsp;&nbsp;&nbsp;{'>'}
      </button>
    </div>
  );
};

export default PagiNation;
