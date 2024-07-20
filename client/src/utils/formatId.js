export const formatId = id => {
    if (!id) return '';

    return String(id).padStart(8, '0');
};
