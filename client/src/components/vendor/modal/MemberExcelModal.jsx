import { convertMember, uploadMembers } from '@/apis/member';
import BaseModal from '@/components/common/BaseModal';
import { useState, useContext } from 'react';
import AlertWdithContext from '@/utils/dialog/alertwidth/AlertWidthContext';
import 'react-tooltip/dist/react-tooltip.css';
import { Tooltip } from 'react-tooltip';
import { FaExclamationCircle, FaUpload, FaSave } from 'react-icons/fa';

const headers = [
  { name: '회원명', key: 'memberName', required: true },
  { name: '휴대전화', key: 'memberPhone', required: true },
  { name: '우편번호', key: 'zipcode', required: false },
  { name: '주소', key: 'address', required: false },
  { name: '상세주소', key: 'addressDetail', required: false },
  { name: '등록일', key: 'memberEnrollDate', required: true },
  { name: '유선전화', key: 'memberHomePhone', required: false },
  { name: '이메일', key: 'memberEmail', required: true },
];

const MemberExcelModal = ({ icon, isShowModal, setIsShowModal, modalTitle, axiosMemberList }) => {
  const [file, setFile] = useState(null);
  const [data, setData] = useState([]);
  const [errors, setErrors] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

const { alertWidth: alertCompWidth } = useContext(AlertWdithContext);

const onAlertClick = async (msg) => {
  const result = await alertCompWidth(msg);
  console.log('onAlertWidthClick : ', result);
};

  const handleFileChange = e => {
    setFile(e.target.files[0]);
  };

  const handleUpload = async () => {
    if (!file) return;

    setIsLoading(true);
    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await convertMember(formData);
      setData(response.data);
      setErrors([]);
    } catch (error) {
      console.error('Error uploading file:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleCellChange = (index, key, value) => {
    const newData = [...data];
    newData[index][key] = value;
    setData(newData);
  };

  const handleSubmit = async () => {
    try {
      const errors = await uploadMembers(data);
      if (errors.data.length > 0) {
        setData(errors.data.map(e => e.notSaved));
        setErrors(errors.data.map(e => e.message));
        onAlertClick(
          `총: ${data.length} 성공: ${data.length - errors.data.length} 실패: ${errors.data.length}`
        );
        return;
      }

      alert('회원 정보가 모두 성공적으로 등록되었습니다.');
      setData([]);
      setErrors([]);
      setIsShowModal(false);
    } catch (error) {
      console.error('Error submitting data:', error);
      onAlertClick('회원 정보 등록 중 오류가 발생했습니다.');
    }
  };

  return (
    <BaseModal
      isShowModal={isShowModal}
      setIsShowModal={setIsShowModal}
      modalTitle={modalTitle}
      icon={icon}
      height={'h-[90vh]'}
      width={'w-[95vw]'}>
      <div className='bg-white p-8 rounded-lg w-full h-full flex flex-col'>
        <div className='mb-6 flex items-center justify-between'>
          <div className='flex items-center flex-1'>
            <label htmlFor='file-upload' className='cursor-pointer bg-blue-50 hover:bg-blue-100 text-blue-700 font-semibold py-2 px-4 rounded-full inline-flex items-center transition duration-300 ease-in-out'>
              <FaUpload className="mr-2" />
              파일 선택
              <input
                id='file-upload'
                type='file'
                onChange={handleFileChange}
                className='hidden'
                accept='.xlsx, .xls'
              />
            </label>
            <span className="ml-3 text-sm text-gray-600">{file ? file.name : '선택된 파일 없음'}</span>
          </div>
          {data.length <= 0 ? (
            <button
              onClick={handleUpload}
              className='ml-4 bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-8 rounded-full transition duration-300 ease-in-out focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 flex items-center'
              disabled={isLoading}>
              <FaUpload className="mr-2" />
              업로드
            </button>
          ) : (
            <button
              onClick={handleSubmit}
              className='bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-6 rounded-full transition duration-300 ease-in-out focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-opacity-50 flex items-center'>
              <FaSave className="mr-2" />
              등록
            </button>
          )}
        </div>

        {isLoading && (
          <div className='text-center mb-4'>
            <div className='animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500 mx-auto'></div>
            <p className='mt-2 text-gray-600'>업로드 중...</p>
          </div>
        )}

        <div className='flex-1 overflow-hidden'>
          <div className='overflow-x-auto overflow-y-auto h-full shadow-md rounded-lg'>
            <table className='w-full table-auto'>
              <thead className='sticky top-0 bg-gray-100'>
                <tr>
                  {headers.map((header, index) => (
                    <th
                      key={index}
                      className='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>
                      {header.name}
                      {header.required && <span className='text-red-500 ml-1'>*</span>}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody className='bg-white divide-y divide-gray-200'>
                {data.length > 0 ? (
                  data.map((row, rowIndex) => (
                    <tr key={rowIndex} className={rowIndex % 2 === 0 ? 'bg-gray-50' : 'bg-white'}>
                      {headers.map((header, cellIndex) => (
                        <td key={cellIndex} className='px-6 py-4 whitespace-nowrap'>
                          <div className='flex items-center'>
                            {errors && errors.length > 0 && cellIndex === 0 && (
                              <div className="mr-2">
                                <FaExclamationCircle
                                  className="text-red-500 cursor-help"
                                  data-tooltip-id={`error-${rowIndex}`}
                                  data-tooltip-html={errors[rowIndex].replace('\n', '<br>')}
                                />
                                <Tooltip id={`error-${rowIndex}`} place="top" type="error" effect="solid" />
                              </div>
                            )}
                            <input
                              type='text'
                              value={row[header.key]}
                              onChange={e => handleCellChange(rowIndex, header.key, e.target.value)}
                              className='w-full text-sm text-gray-900 bg-transparent border-b border-gray-300 focus:outline-none focus:border-blue-500 transition duration-300'
                            />
                          </div>
                        </td>
                      ))}
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={headers.length} className='px-6 py-4 text-center text-gray-500'>
                      데이터가 없습니다. 엑셀 파일을 업로드해주세요.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </BaseModal>
  );
};

export default MemberExcelModal;