// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faTrashAlt } from '@fortawesome/free-solid-svg-icons';
// import TableCol from '@/components/common/tables/TableCol';
// import { useState } from 'react';

// const BillingDetailProductTable = ({
//   cols = [],
//   data = [],
//   editable = true,
//   handleProductChange,
//   handleRemove,
// }) => {
//   const [selection, setSelection] = useState([]);
//   const [editingState, setEditingState] = useState({});

//   const handleClickCheckBox = value => {
//     setSelection(prev =>
//       prev.includes(value) ? prev.filter(item => item !== value) : [...prev, value]
//     );
//   };

//   const handleClickCheckBoxAll = e => {
//     setSelection(e.target.checked ? [...data] : []);
//   };

//   const isSelectedAll = () => selection.length === data.length && selection.length !== 0;

//   const handleEditClick = (idx, field) => {
//     if (editable) {
//       setEditingState(prev => ({ ...prev, [idx]: { ...prev[idx], [field]: true } }));
//     }
//   };

//   const handleInputChange = (idx, field, value) => {
//     if (field === 'price' && value && value.length >= 7) {
//       alert('상품 가격은 최대 99만원입니다.');
//       return;
//     } else if (field === 'quantity' && value && value.length >= 2) {
//       alert('상품 수량은 최대 9개입니다.');
//       return;
//     }
//     const numericValue = value.replace(/\D/g, '');
//     handleProductChange(idx, field, numericValue);
//   };

//   const renderEditableField = (item, idx, field) => {
//     const value = item[field];

//     return (
//       <InputWeb
//         id={field}
//         type='text'
//         placeholder={`${field === 'price' ? '가격' : '수량'}`}
//         required
//         classInput='text-center p-4 w-1/12 focus:border-mint focus:outline-none
//                     focus:ring-mint focus:ring-1 rounded-lg'
//         value={value.toLocaleString()}
//         onChange={e => handleInputChange(idx, field, e.target.value)}
//         autoComplete='off'
//         maxLength={field === 'price' ? 7 : 2}
//       />
//     );
//   };

//   return (
//     <div className=''>
//       <table className='w-full'>
//         <TableCol
//           cols={cols}
//           isSelectedAll={isSelectedAll}
//           handleClickCheckBoxAll={handleClickCheckBoxAll}
//         />
//         <tbody className='bg-white divide-y divide-gray-200'>
//           {data.map((item, idx) => (
//             <tr
//               key={item.billingProductId}
//               className={`text-text_black flex items-center border-b border-ipt_border ${selection.includes(item) ? 'bg-gray-200' : ''}`}>
//               <td className='w-16 py-4 text-sm font-700 flex justify-center'>
//                 <input
//                   className='w-4 h-4'
//                   type='checkbox'
//                   checked={selection.includes(item)}
//                   onChange={() => handleClickCheckBox(item)}
//                   onClick={e => e.stopPropagation()}
//                 />
//               </td>
//               <td className='w-16 py-4 text-sm font-700 flex justify-center'>{idx + 1}</td>
//               <td className='w-2/12 py-4 text-sm font-700 flex justify-center'>{item.name}</td>
//               <td
//                 className='w-2/12 py-4 text-sm font-700 flex justify-center'
//                 onClick={() => handleEditClick(idx, 'price')}>
//                 {renderEditableField(item, idx, 'price')}
//               </td>
//               <td
//                 className='w-2/12 py-4 text-sm font-700 flex justify-center'
//                 onClick={() => handleEditClick(idx, 'quantity')}>
//                 {renderEditableField(item, idx, 'quantity')}
//               </td>
//               <td className='w-2/12 py-4 text-sm font-700 flex justify-center'>
//                 {`${(item.quantity * item.price).toLocaleString()}원`}
//               </td>
//               {editable && (
//                 <td className='w-1/12 py-4 text-sm font-700 flex justify-center'>
//                   <button onClick={() => handleRemove(idx)}>
//                     <div className='flex'>
//                       <FontAwesomeIcon icon={faTrashAlt} className='text-red-500 text-xl mr-3' />
//                       <p className='text-red-500 text-l font-extrabold'>삭제</p>
//                     </div>
//                   </button>
//                 </td>
//               )}
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// };

// export default BillingDetailProductTable;
