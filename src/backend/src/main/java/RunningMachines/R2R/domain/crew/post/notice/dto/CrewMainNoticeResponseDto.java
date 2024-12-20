package RunningMachines.R2R.domain.crew.post.notice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CrewMainNoticeResponseDto {
    private String crewTitle;
    private String crewProfileImage;
    private int postCount;
    private int memberCount;
    private List<NoticePostSimpleResponseDto> noticePost;

    public static CrewMainNoticeResponseDto of(String crewTitle,String crewProfileImage, int postCount, int memberCount, List<NoticePostSimpleResponseDto> noticePost) {
        return CrewMainNoticeResponseDto.builder()
                .crewTitle(crewTitle)
                .crewProfileImage(crewProfileImage)
                .postCount(postCount)
                .memberCount(memberCount)
                .noticePost(noticePost)
                .build();
    }
}
