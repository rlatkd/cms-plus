import { useContext } from 'react';
import ConfirmContext from '@/utils/dialog/confirm/ConfirmContext';

// type:
// msg : 원하는 메세지
// type : 2가지(default, warning), "" = defalut
// title : alert제목 , "" = 효성 CMS#
// 사용법 => const confirm = useConfirm(); 정의
const useConfirm = () => {
  const { confirm: confrimComp } = useContext(ConfirmContext);

  const onConfirm = async (msg, type, title) => {
    const result = await confrimComp(msg, type, title);
    return result;
  };

  return onConfirm;
};

export default useConfirm;
