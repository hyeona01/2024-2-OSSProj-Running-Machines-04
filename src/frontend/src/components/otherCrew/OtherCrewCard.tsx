import { ReactComponent as CTOIcon } from "@/assets/icons/CTOIcon.svg";
import { ReactComponent as CrewPeopleIcon } from "@/assets/icons/CrewPeopleIcon.svg";
import { CrewResponse } from "@/types/crew";

type OtherCrewCardPrps = {
  crew: CrewResponse;
};
const OtherCrewCard = ({ crew }: OtherCrewCardPrps) => {
  return (
    <div className="flex justify-between items-center px-8 py-6">
      <div className="flex gap-2">
        <div className="w-[60px] h-[60px] rounded-full overflow-hidden">
          <img
            className="w-full h-full object-cover"
            src={crew.profileImageUrl}
            // src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTr2fqdlFDppEAoCuV34aZIe61WnpTwVygxuNMM3szqDMMZRF-dL7jxDKQFBRrXIppNoZ0&usqp=CAU"
            alt="프로필"
          />
        </div>
        <div className="flex flex-col justify-center gap-1">
          <div className="text-[18px]">{crew.title}</div>
          <div className="text-[12px] flex items-center gap-1.5">
            <CrewPeopleIcon />
            가입인원 {crew.memberCount}
          </div>
        </div>
      </div>
      <CTOIcon />
    </div>
  );
};

export default OtherCrewCard;
