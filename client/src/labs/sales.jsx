// <----- 3. 매출액 ----->
/*
  1. 라인그래프 : 매출액

  % 요청데이터
  {
    year : 2017
    month : 10
  }

  % 응답데이터
  {
    sales : [
        40000, 70000, 30000, 120000, 90000, 290000, 60000, 150000, 220000, 90000, 120000, 180000,
        50000, 130000, 30000, 170000, 20000, 100000, 60000, 75000, 82000, 30000, 200000, 110000,
        50000, 140000, 70000, 90000, 60000, 170000,
      ]
  }
*/

const getDaysInMonth = (year, month) => {
  return new Date(year, month, 0).getDate();
};

const year = 2024;
const month = 6; // October (0-based month index, so 10 represents November)

// Generate categories based on the provided year and month
const categories = Array.from({ length: getDaysInMonth(year, month) }, (_, i) => {
  const date = new Date(year, month - 1, i + 1); // month - 1 to adjust for 0-based month index
  return date.toISOString().split('T')[0]; // 'yyyy-mm-dd' 형식으로 날짜 생성
});

export const salesChart = {
  series: [
    {
      name: 'Sales',
      data: [
        40000, 70000, 30000, 120000, 90000, 290000, 60000, 150000, 220000, 90000, 120000, 180000,
        50000, 130000, 30000, 170000, 20000, 100000, 60000, 75000, 82000, 30000, 200000, 110000,
        50000, 140000, 70000, 90000, 60000, 170000,
      ], // 30일치 예시 데이터: 매출액 (들쑥날쑥)
    },
  ],
  chart: {
    height: 350,
    type: 'line',
  },
  stroke: {
    width: 5,
    curve: 'smooth',
  },
  xaxis: {
    type: 'datetime',
    categories: categories,
    tickAmount: 10,
    labels: {
      formatter: function (value, timestamp, opts) {
        return opts.dateFormatter(new Date(timestamp), 'dd MMM');
      },
    },
  },
  title: {
    text: '매출액 추이',
    align: 'left',
    style: {
      fontSize: '16px',
      color: '#666',
    },
  },
  markers: {
    size: 5, // 마커 크기 설정
    hover: {
      size: 8, // 호버 시 마커 크기 설정
    },
  },
  tooltip: {
    enabled: true, // 툴팁 활성화
    y: {
      formatter: function (val) {
        return val.toLocaleString() + ' 원'; // 툴팁의 금액 형식 설정
      },
    },
  },
  yaxis: {
    labels: {
      formatter: function (value) {
        return value.toLocaleString(); // Y축 라벨에 쉼표 추가
      },
      style: {
        colors: ['#000000'], // Y축 라벨 색상
      },
    },
    title: {
      text: '매출액 (원)', // Y축 제목
    },
  },
};
