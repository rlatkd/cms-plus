const fieldToName = {
    billingType: '청구타입'
}

const convertBadReqMsg = e => {
    const errors = e.response.data.errors;
    return errors.map(fieldError => `${fieldToName[fieldError.field] || ''} ${fieldError.reason}`);
}

export default convertBadReqMsg;