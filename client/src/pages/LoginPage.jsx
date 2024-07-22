import LoginForm from '@/components/LoginForm';
import { ConfigProvider, DatePicker, Space } from 'antd';
import koKR from 'antd/es/locale/ko_KR';
const onChange = (date, dateString) => {
  console.log(date, dateString);
};

const LoginPage = () => {
  const customStyles = {
    datePicker: {
      width: '200px',
      fontSize: '16px', // 텍스트 크기 변경
      color: '#000000', // 텍스트 색상 변경
    },
  };
  return (
    <>
      <LoginForm />
      <img
        className=' absolute right-0 top-0 h-[88vh] w-[44vw] hidden mobile:block'
        src='/src/assets/backgroundImgSide.png'
        alt='background'
      />
      <ConfigProvider locale={koKR}>
        <div style={{ padding: '20px' }}>
          <Space direction='vertical'>
            <DatePicker onChange={onChange} style={customStyles.datePicker} />
            <DatePicker format='YYYY-MM-DD' onChange={onChange} style={customStyles.datePicker} />
          </Space>
        </div>
      </ConfigProvider>
    </>
  );
};

export default LoginPage;
