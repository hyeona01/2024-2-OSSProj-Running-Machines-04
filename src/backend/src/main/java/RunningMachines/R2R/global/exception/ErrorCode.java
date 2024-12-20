package RunningMachines.R2R.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 일반 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청한 정보를 찾을 수 없습니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "COMMON405", "정보가 이미 존재합니다."),

    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4000", "사용자가 이미 존재합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "사용자를 찾을 수 없습니다."),
    USER_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER4002", "비밀번호가 일치하지 않습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "USER4003", "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "USER4004", "유효하지 않은 토큰입니다."),

    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE5000", "서버 에러: 파일 업로드에 실패했습니다."),
    FILE_NAME_ENCODING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE5001", "서버 에러: 파일명 인코딩에 실패했습니다."),

    GPX_PARSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "COURSE5000", "서버 에러: GPX 파싱에 실패했습니다."),
    GPX_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "COURSE5001", "서버 에러: GPX 업로드에 실패했습니다."),

    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "COURSE4000", "코스를 찾을 수 없습니다."),

    USER_COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_COURSE4000", "사용자 코스를 찾을 수 없습니다."),

    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "TAG4000", "태그를 찾을 수 없습니다."),

    PREFER_NOT_FOUND(HttpStatus.NOT_FOUND, "PREFER4000", "선호도가 입력되어 있지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ErrorResponse<Void> getErrorResponse() {
        return ErrorResponse.onFailure(code, message);
    }
}
