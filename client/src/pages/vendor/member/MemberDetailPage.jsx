import MemDetailBasicInfo from '@/components/vendor/member/details/MemDetailBasicInfo';
import MemDetailContractList from '@/components/vendor/member/details/MemDetailContractList';
import MemDetailDisplay from '@/components/vendor/member/details/MemDetailDisplay';
import MemDetailHeader from '@/components/vendor/member/details/MemDetailHeader';

const MemberDetailPage = () => {
  return (
    <div>
      <MemDetailHeader />
      <MemDetailDisplay />
      <MemDetailBasicInfo />
      <MemDetailContractList />
    </div>
  );
};

export default MemberDetailPage;
