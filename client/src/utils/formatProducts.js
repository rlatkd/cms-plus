// 테이블에서 보여지는 상품 정보 데이터 format

export const formatProducts = contractProducts => {
  const firstProduct = contractProducts[0];
  const additionalProductsCount = contractProducts.length - 1;

  return contractProducts.length > 1
    ? `${firstProduct.name} + ${additionalProductsCount}`
    : firstProduct.name;
};
