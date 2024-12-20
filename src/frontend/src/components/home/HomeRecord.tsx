import { ReactComponent as HomeBgImg } from "@/assets/images/HomeBgImg.svg";
import { useHomeDataGet } from "@/hooks/useHome";
import Spinner from "../common/Spinner";

const HomeRecord = () => {
  const { data, isLoading } = useHomeDataGet();

  return (
    <>
      {isLoading ? (
        <Spinner />
      ) : (
        <div className="py-5 flex flex-col justify-center items-center relative border-b-4 border-[#F3F3F3]">
          <HomeBgImg className="absolute top-0 left-0" />
          <div className="flex items-end gap-3">
            <div className="text-[16px]">오늘 달린 거리는</div>
            <div className="bg-signatureGradient text-transparent bg-clip-text text-[50px] font-bold">
              {data?.totalDistance}
            </div>
          </div>
          <div className="flex items-end gap-3">
            <div className="bg-signatureGradient text-transparent bg-clip-text text-[50px] font-bold">
              {data?.averagePace}
            </div>
            <div className="text-[16px]">평균 페이스를 기록했어요.</div>
          </div>
          <div className="flex items-end gap-3">
            <div className="text-[16px]">총 활동 시간</div>
            <div className="bg-signatureGradient text-transparent bg-clip-text text-[50px] font-bold">
              {data?.totalDuration}
            </div>
            <div className="text-[16px]">이에요.</div>
          </div>
        </div>
      )}
    </>
  );
};

export default HomeRecord;
