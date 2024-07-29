const Arrow = ({ rotation = 0, ...props }) => (
  <div className='flex items-center justify-center'>
    <svg
      width='20'
      height='20'
      viewBox='0 0 20 20'
      xmlns='http://www.w3.org/2000/svg'
      style={{ transform: `rotate(${rotation}deg)`, transition: 'transform 0.3s ease-in-out' }}
      {...props}>
      <path
        d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z'
        fill={props.fill}
      />
    </svg>
  </div>
);

export default Arrow;
