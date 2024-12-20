package RunningMachines.R2R.global.s3;

import RunningMachines.R2R.global.exception.CustomException;
import RunningMachines.R2R.global.exception.ErrorCode;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Provider {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // S3에 GPX 업로드 (원본파일명 그대로)
    public String uploadGPX(MultipartFile file, S3RequestDto s3RequestDto) {

        String originalFilename = file.getOriginalFilename(); // 원본 파일명
        String fileName = s3RequestDto.getDirName() + "/" + s3RequestDto.getUserId() + "/" + originalFilename;

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), new ObjectMetadata()));
        } catch (IOException e) {
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
        return amazonS3Client.getUrl(bucket, fileName).toString(); // 업로드 후 URL 반환
    }

    // S3에 파일 업로드
    public String uploadFile(MultipartFile file, S3RequestDto s3RequestDto){

        String originalFilename = file.getOriginalFilename(); // 원본 파일명
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 확장자명

        String fileName = s3RequestDto.getDirName() + "/" + s3RequestDto.getUserId() + "/" + UUID.randomUUID() + extension; //변경된 파일 명

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.addUserMetadata("originfilename", URLEncoder.encode(originalFilename, StandardCharsets.UTF_8)); // 메타데이터에 원본 파일명 추가

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
        }catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // TODO - 모델링 서버랑 연동하는 거 고려해서 추가 개발 (위경도 입력 받도록)
    public List<String> getCourseFiles() {
        List<String> gpxs = new ArrayList<>();

        try {
            // course 디렉토리에서 최대 5개의 파일 가져오기
            ListObjectsV2Request request = new ListObjectsV2Request()
                    .withBucketName(bucket) // 버킷 이름
                    .withPrefix("course/null/")  // course 디렉토리의 파일
                    .withMaxKeys(10);        // 최대 10개만 가져오기

            ListObjectsV2Result result = amazonS3Client.listObjectsV2(request);

            // 가져온 객체의 Key를 리스트에 추가
            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                gpxs.add(objectSummary.getKey());
            }
        } catch (Exception e) {
            log.error("Error fetching file list from S3 bucket: {}", e.getMessage(), e);
        }
        return gpxs;
    }

    // 원본 파일명 가져오기
    public String getOriginalFileName(String transformedFileName) {
        try {
            ObjectMetadata metadata = amazonS3Client.getObjectMetadata(bucket, transformedFileName);
            return metadata.getUserMetadata().getOrDefault("original-fileName", transformedFileName);
        } catch (Exception e) {
            log.error("Error retrieving original filename: {}", e.getMessage(), e);
        }
        return null;
    }

    public URL getFileUrl(String fileKey) {
        return amazonS3Client.getUrl(bucket, fileKey);
    }

    // 사용자의 러닝 기록 GPX를 S3에 저장
    public String uploadGpxUserCourseFile(InputStream gpxFileStream, Long userId) {
        String fileName = "course" + "/" + "userCourse" + "/" + userId + "/" + UUID.randomUUID() + ".gpx"; //변경된 파일 명

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/gpx+xml");
        try {
            metadata.setContentLength(gpxFileStream.available());
        } catch (IOException e) {
            log.error("Failed to get GPX file length for userId: {}", e.getMessage(), e);
        }

        amazonS3Client.putObject(bucket, fileName, gpxFileStream, metadata);

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 사용자의 러닝 기록을 GPX로 변환 후 S3에 저장
//    public String saveWaypointsAsGpx(List<UserCourseRequestDto.Waypoint> waypoints, Long userId) {
//        try {
//            // 웨이포인트 리스트로 GPX 파일 생성
//            InputStream gpxFileStream = GpxFileGenerator.createGpxFile(waypoints);
//
//            // S3에 GPX 파일 업로드
//            String gpxFileUrl = uploadGpxUserCourseFile(gpxFileStream, userId);
//            log.info("GPX 파일 업로드 성공: {}", gpxFileUrl);
//
//            return gpxFileUrl;
//        } catch (Exception e) {
//            log.error("GPX 파일 생성 및 업로드 실패", e);
//            throw new CustomException(ErrorCode.GPX_UPLOAD_FAILED);
//        }
//    }
}
