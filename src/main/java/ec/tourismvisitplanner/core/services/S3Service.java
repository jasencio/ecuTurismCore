package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.models.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class S3Service {
    private final S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public File uploadFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        LocalDate today = LocalDate.now();
        String folderPath = today.getYear() + "/" + today.getMonthValue() + "/" + today.getDayOfMonth();

        String fileName = UUID.randomUUID() + fileExtension;

        String fullPathFolder = folderPath + "/" + fileName;

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fullPathFolder)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return File.builder().fullPath(fullPathFolder).publicUrl("https://" + bucketName + ".s3.amazonaws.com/").build();
    }

    public void deleteFile(String fullFilePath) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fullFilePath)
                .build();

        s3Client.deleteObject(deleteRequest);
    }

    public File uploadFileFromStream(ByteArrayInputStream inputStream, String fileName) throws IOException {
        try (inputStream) {
            String fileExtension = "";
            if (fileName != null && fileName.contains(".")) {
                fileExtension = fileName.substring(fileName.lastIndexOf("."));
            }
            LocalDate today = LocalDate.now();
            String folderPath = today.getYear() + "/" + today.getMonthValue() + "/" + today.getDayOfMonth();
            String uniqueFileName = UUID.randomUUID() + fileExtension;
            String key = folderPath + "/" + uniqueFileName;

            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentType("image/jpeg")
                            .build(),
                    RequestBody.fromInputStream(inputStream, inputStream.available()));

            String url = s3Client.utilities().getUrl(GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build()).toString();

            return File.builder().fullPath(key).publicUrl(url).build();
        }
    }
}