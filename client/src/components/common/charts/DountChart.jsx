import { useEffect, useRef } from 'react';
import styled from 'styled-components';

const DountChart = ({ color, percent, size }) => {
  const circleRef = useRef(null);

  useEffect(() => {
    if (circleRef.current) {
      const length = circleRef.current.getTotalLength();
      circleRef.current.style.strokeDasharray = `${length}px ${length}px`;
      circleRef.current.style.strokeDashoffset = `${length}px`;
      setTimeout(() => {
        circleRef.current.style.transition = 'stroke-dashoffset 1s ease';
        circleRef.current.style.strokeDashoffset = `${length - (percent * length)}px`;
      }, 100);
    }
  }, [percent]);

  return (
    <Chart color={color} size={size}>
      <svg viewBox="0 0 36 36">
        <path
          d="M18 2.0845
            a 15.9155 15.9155 0 0 1 0 31.831
            a 15.9155 15.9155 0 0 1 0 -31.831"
          fill="none"
          stroke="#eee"
          strokeWidth="3"
        />
        <path
          ref={circleRef}
          d="M18 2.0845
            a 15.9155 15.9155 0 0 1 0 31.831
            a 15.9155 15.9155 0 0 1 0 -31.831"
          fill="none"
          stroke={color}
          strokeWidth="3"
        />
      </svg>
      <Percent color={color}>{Math.round(percent * 100)}%</Percent>
    </Chart>
  );
};

const Chart = styled.div`
  position: relative;
  width: ${props => props.size};
  height: ${props => props.size};
`;

const Percent = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 14px;
  font-weight: bold;
  color: ${props => props.color};
`;

export default DountChart;
