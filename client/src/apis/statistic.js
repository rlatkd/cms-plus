import { privateAxios } from '.';

export const getStatisticList = async (searchParams = {}) => {
    const res = await privateAxios.get('/api/v1/statistics/member-contracts', {
      params: {
        ...searchParams,
      },
    });
    return res;
  };

// export const getStatisticList = async (searchParams = {}) => {
//     const res = await privateAxios.get('/api/v1/statistics/member-contracts', {
//       params: {
//         ...searchParams,
//       },
//     });
//     return res;
//   };