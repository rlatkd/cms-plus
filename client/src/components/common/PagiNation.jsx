import Arrow from '@/assets/Arrow';

const buttonBaseStyle = 'mx-1 rounded-lg font-700 text-text_black flex items-center justify-center';

const PagiNation = ({
  currentPage,
  setCurrentPage,
  totalPages,
  pageGroup,
  setPageGroup,
  buttonCount,
}) => {
  const startPage = pageGroup * buttonCount + 1;
  const endPage = Math.min(startPage + buttonCount - 1, totalPages);

  // 페이지 변경 이벤트핸들러
  const handlePageChange = newPage => {
    if (newPage === currentPage) return;

    setCurrentPage(newPage);
  };

  // 이전 페이지 그룹 변경 이벤트핸들러
  const handlePrevPageGroup = () => {
    if (pageGroup < 0) return;

    setPageGroup(prev => prev - 1);
    setCurrentPage((pageGroup - 1) * buttonCount + 1);
  };

  // 다음 페이지 그룹 변경 이벤트핸들러
  const handleNextPageGroup = () => {
    setPageGroup(prev => prev + 1);
    setCurrentPage((pageGroup + 1) * buttonCount + 1);
  };

  return (
    <div className='flex justify-center p-2 '>
      <div className='flex items-center '>
        {/* 이전 버튼 */}
        <button
          key='prev'
          className={`${buttonBaseStyle} h-8 `}
          onClick={handlePrevPageGroup}
          disabled={pageGroup === 0}>
          <Arrow rotation={'90'} fill={pageGroup === 0 ? '#C7CCD0' : '#344767'} />
          <span
            className={`${pageGroup === 0 ? 'text-ipt_border' : 'hover:border-b text-text_black'}  mx-1 `}>
            이전
          </span>
        </button>

        {/* 1,2,3,4,5 버튼 영역 */}
        <div className='flex justify-around'>
          {Array.from({ length: endPage - startPage + 1 }, (_, idx) => {
            const pageNum = startPage + idx;
            return (
              <button
                key={pageNum}
                className={`${buttonBaseStyle} w-8 h-8  ${pageNum === currentPage ? 'text-white bg-mint hover:bg-mint_hover ' : 'hover:border border-text_black'}`}
                onClick={() => handlePageChange(pageNum)}>
                {pageNum}
              </button>
            );
          })}
        </div>

        {/* 다음 버튼 */}
        <button
          key='next'
          className={`${buttonBaseStyle} h-8`}
          onClick={handleNextPageGroup}
          disabled={endPage >= totalPages}>
          <span
            className={`${endPage >= totalPages ? 'text-ipt_border' : 'hover:border-b text-text_black'}  mx-1 `}>
            다음
          </span>
          <Arrow rotation={'270'} fill={endPage >= totalPages ? '#C7CCD0' : '#344767'} />
        </button>
      </div>
    </div>
  );
};

export default PagiNation;
