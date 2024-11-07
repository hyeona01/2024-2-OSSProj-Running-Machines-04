import React from "react";

const MyCrewProfile = () => {
  return (
    <div className="py-6 flex items-center justify-center gap-12">
      <div className="w-[30vw] h-[30vw] rounded-full overflow-hidden">
        <img
          className="w-full h-full object-cover"
          src="https://blog.mealligram.com/wp-content/uploads/2024/03/1.jpg"
          alt=""
        />
      </div>
      <div className="flex flex-col gap-3">
        <div className="text-[18px] font-semibold">서울 FRC 러닝크루</div>
        <div className="flex items-start gap-6">
          <div className="flex flex-col">
            <span className="text-[18px] font-semibold">5</span>
            <span className="text-[12px]">posts</span>
          </div>
          <div className="flex flex-col">
            <span className="text-[18px] font-semibold">32</span>
            <span className="text-[12px]">members</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyCrewProfile;
