import ApexCharts from 'apexcharts';
import { useEffect } from 'react';
import { memberChart } from './memberChart';
import { contractChart } from './contractChart';
import { salesChart } from './sales';
import { billingStatus } from './billingStatus';
import { memberAge } from './memberAge';

const ChartTest = () => {
  useEffect(() => {
    let chart1 = new ApexCharts(document.querySelector('#chart1'), memberChart);
    chart1.render();

    let chart2 = new ApexCharts(document.querySelector('#chart2'), contractChart);
    chart2.render();

    let chart3 = new ApexCharts(document.querySelector('#chart3'), salesChart);
    chart3.render();

    let chart4 = new ApexCharts(document.querySelector('#chart4'), billingStatus);
    chart4.render();

    let chart5 = new ApexCharts(document.querySelector('#chart5'), memberAge);
    chart5.render();

    return () => {
      chart1.destroy();
      chart2.destroy();
      chart3.destroy();
      chart4.destroy();
      chart5.destroy();
    };
  }, []);

  return (
    <div className='w-full h-full'>
      <div id='chart1' className='w-[800px] h-[400px] m-5 border-2 border-black mb-5' />
      <div id='chart2' className='w-[800px] h-[400px] m-5 border-2 border-black mb-5' />
      <div id='chart3' className='w-[800px] h-[400px] m-5 border-2 border-black mb-5' />
      <div className='flex'>
        <div id='chart4' className='w-[400px] h-[200px] m-5 border-2 border-black mb-5' />
        <div id='chart5' className='w-[400px] h-[200px] m-5 border-2 border-black mb-5' />
      </div>
    </div>
  );
};

export default ChartTest;
