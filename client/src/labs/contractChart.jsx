// <----- 2. 계약 증감 추이 ----->
/*
  1. 첫번째 라인 그래프 : 신규 계약 수

  % 요청데이터
  {
    year : 2017
  }

  % 응답데이터
  {
    memberRatio :  [100, 120, 110, 150, 140, 160, 180, 170, 190, 200, 190, 210]
  }
*/

export const contractChart = {
  series: [
    {
      name: '신규 계약 수',
      data: [100, 90, 110, 80, 140, 100, 180, 170, 160, 150, 190, 180], // 예시 데이터: 신규 계약 수
    },
  ],
  chart: {
    height: 350,
    type: 'line',
    dropShadow: {
      enabled: true,
      color: '#000',
      top: 18,
      left: 7,
      blur: 10,
      opacity: 0.2,
    },
    zoom: {
      enabled: false,
    },
    toolbar: {
      show: false,
    },
  },
  colors: ['#545454'], // 라인의 색상 설정
  dataLabels: {
    enabled: true, // 데이터 라벨 활성화
    formatter: function (val, opts) {
      return val.toFixed(0) + ' 건'; // 데이터 라벨 (신규 계약 수)
    },
    style: {
      colors: ['#FF4560'], // 데이터 라벨 색상
    },
  },
  stroke: {
    curve: 'smooth', // 곡선 형태로 설정
  },
  title: {
    text: '신규 계약 증감 추이', // 차트 제목
    align: 'left',
  },
  grid: {
    borderColor: '#e7e7e7',
    row: {
      colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.5,
    },
  },
  markers: {
    size: 1,
  },
  xaxis: {
    categories: [
      '01 Jan',
      '02 Jan',
      '03 Jan',
      '04 Jan',
      '05 Jan',
      '06 Jan',
      '07 Jan',
      '08 Jan',
      '09 Jan',
      '10 Jan',
      '11 Jan',
      '12 Jan',
    ], // X축 레이블 설정
    title: {
      text: 'Month', // X축 제목
    },
  },
  yaxis: [
    {
      title: {
        text: '신규 계약 수 (건)', // Y축 제목
      },
      labels: {
        formatter: function (value) {
          return value.toFixed(0) + ' 건'; // Y축 라벨 정수로 표시
        },
        style: {
          colors: ['#FF4560'], // Y축 라벨 색상
        },
      },
      min: 0, // Y축 최소값 설정
      max: 250, // Y축 최대값 설정
    },
  ],
  tooltip: {
    shared: true, // 툴팁을 시리즈 간에 공유
    intersect: false, // 데이터 포인트가 겹치지 않도록 설정
  },
  legend: {
    position: 'top',
    horizontalAlign: 'right',
    floating: true,
    offsetY: -25,
    offsetX: -5,
  },
};
