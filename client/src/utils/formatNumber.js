const formatNumber = (number, includeDecimal = false) => {
  if (number === undefined || number === null) return '0';
  
  const parts = number.toString().split('.');
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  
  return includeDecimal && parts[1] ? parts.join('.') : parts[0];
};

export default formatNumber;