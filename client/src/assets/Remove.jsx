const Remove = props => (
  <svg
    width='23'
    height='23'
    viewBox='0 0 23 23'
    fill='none'
    xmlns='http://www.w3.org/2000/svg '
    onClick={props.onClick}>
    <path
      d='M13.4163 10.5417V16.2917M9.58301 10.5417V16.2917M5.74967 6.70833V18.2083C5.74967 18.7167 5.95161 19.2042 6.31105 19.5636C6.6705 19.9231 7.15801 20.125 7.66634 20.125H15.333C15.8413 20.125 16.3289 19.9231 16.6883 19.5636C17.0477 19.2042 17.2497 18.7167 17.2497 18.2083V6.70833M3.83301 6.70833H19.1663M6.70801 6.70833L8.62467 2.875H14.3747L16.2913 6.70833'
      stroke={`${props.stroke ? props.stroke : '#7B809A'}`}
      strokeWidth='2'
      strokeLinecap='round'
      strokeLinejoin='round'
    />
    <style>
      {`
          svg:hover path {
            stroke: red;
          }
        `}
    </style>
  </svg>
);

export default Remove;
