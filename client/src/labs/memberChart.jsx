// <----- 1. 회원 증감 추이 ----->
/*
  1. 막대그래프 : 전달 대비 회원 증가수
  2. 꺽은선그래프 : 전달 대비 회원 증가 퍼센트

  % 요청데이터
  {
    year : 2017
  }

  % 응답데이터
  {
    memberCount : [100, 120, 110, 150, 140, 160, 180, 170, 190, 200, 190, 210]
    memberRatio : [0, 20, -8.33, 36.36, -6.67, 14.29, 12.5, -5.56, 11.76, 5.26, -5, 10.53]
  }
*/

export const memberChart = {
  series: [
    {
      name: '전체 회원 수',
      type: 'column',
      data: [100, 120, 110, 150, 140, 160, 180, 170, 190, 200, 190, 210], // 예시 데이터
    },
    {
      name: '증가/감소율',
      type: 'line',
      data: [0, 20, -8.33, 36.36, -6.67, 14.29, 12.5, -5.56, 11.76, 5.26, -5, 10.53], // 예시 데이터 (퍼센트 증가/감소량)
    },
  ],
  chart: {
    height: 350, // 차트 높이 설정
    type: 'line', // 기본 차트 타입 설정
    zoom: {
      enabled: false, // 줌 기능 비활성화
    },
  },
  plotOptions: {
    bar: {
      columnWidth: '40%', // 막대 너비 조정 (예: 50%)
    },
  },
  stroke: {
    width: [0, 2], // 막대그래프와 꺾은선 그래프의 선 너비 설정
    curve: 'smooth', // 꺾은선 그래프의 선을 부드럽게 설정
  },
  colors: ['#0081e3', '#19fe24'], // 각 시리즈의 색상 설정 (막대그래프, 꺾은선 그래프 순)
  title: {
    text: '회원 수와 증가/감소율', // 차트 제목
  },
  dataLabels: {
    enabled: true, // 데이터 라벨 활성화
    enabledOnSeries: [1], // 두 시리즈 모두 데이터 라벨 활성화
    style: {
      colors: ['#000000', '#FF4560'], // 막대그래프 숫자 색상, 꺾은선 숫자 색상
    },
    background: {
      // enabled: false, // 데이터 라벨 배경 비활성화
    },
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
    ], // X축 레이블
  },
  yaxis: [
    {
      title: {
        text: '전체 회원 수', // 첫 번째 Y축 제목
      },
      labels: {
        formatter: function (value) {
          return value.toFixed(0); // 정수로 표시
        },
      },
    },
    {
      opposite: true, // 두 번째 Y축을 오른쪽에 배치
      title: {
        text: '증가/감소율 (%)', // 두 번째 Y축 제목
      },
      labels: {
        formatter: function (value) {
          return value + '%'; // 퍼센트로 표시
        },
      },
      min: -100, // 두 번째 Y축 최소값 설정
      max: 100, // 두 번째 Y축 최대값 설정
    },
  ],
  tooltip: {
    shared: true, // 툴팁을 시리즈 간에 공유
    intersect: false, // 데이터 포인트가 겹치지 않도록 설정
  },
  legend: {
    horizontalAlign: 'center', // 범례를 수평 가운데 정렬
  },
};
