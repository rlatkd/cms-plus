 export const formatExpiryDate = value => {
    const cleaned = value.replace(/\D/g, '');
    let formatted = cleaned;

    if (cleaned.length >= 2) {
      formatted = `${cleaned.slice(0, 2)}/${cleaned.slice(2)}`;
    }

    return formatted.slice(0, 5);
  };