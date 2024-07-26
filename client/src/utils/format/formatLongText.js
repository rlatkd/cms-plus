const formatLongText = (text, maxLength) => {
  if (!text || text.length < maxLength) {
    return text;
  }
  return text.substring(0, maxLength - 3) + '...';
};

export default formatLongText;
