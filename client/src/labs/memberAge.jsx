// <----- 5. 연령대별 회원수 ----->
/*
  % 요청데이터 없음

  % 응답데이터
  {
    memberCount : [44, 55, 57, 56, 61, 58, 63, 60]
  }
*/

export const memberAge = {
  series: [
    {
      name: '연령대별 인원',
      data: [44, 55, 57, 56, 61, 58, 63, 60], // 예시 데이터: 연령대별 인원
    },
  ],
  chart: {
    type: 'bar',
    height: 350,
  },
  plotOptions: {
    bar: {
      horizontal: false,
      columnWidth: '55%',
      endingShape: 'rounded',
    },
  },
  dataLabels: {
    enabled: false,
  },
  stroke: {
    show: true,
    width: 2,
    colors: ['transparent'],
  },
  xaxis: {
    categories: ['10대', '20대', '30대', '40대', '50대', '60대', '70대', '80대'], // 연령대
  },
  yaxis: {
    title: {
      text: '인원 (명)',
    },
  },
  fill: {
    opacity: 1,
  },
  tooltip: {
    y: {
      formatter: function (val) {
        return val + ' 명'; // 툴팁 형식: 명
      },
    },
  },
};
