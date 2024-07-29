// <----- 4. 결제방식당 청구 상태 ----->
/*

  % 요청데이터
  {
    year : 2017
    month : 6
    billingstatus : "미납"  (ENUM이랑 동일하게 보낼 예정)
  }

  % 응답데이터
  {
    paymentData : [44, 55, 41]
  }
*/
export const billingStatus = {
  series: [44, 55, 41], // 예시 데이터: 납부자결제, 자동결제, 가상계좌
  chart: {
    type: 'donut',
  },
  labels: ['납부자결제', '자동결제', '가상계좌'], // 차트 레이블
  responsive: [
    {
      breakpoint: 480,
      options: {
        chart: {
          width: 200,
        },
        legend: {
          position: 'bottom',
        },
      },
    },
  ],
};
