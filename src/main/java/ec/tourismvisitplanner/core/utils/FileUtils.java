package ec.tourismvisitplanner.core.utils;

import ec.tourismvisitplanner.core.models.File;
import ec.tourismvisitplanner.core.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final S3Service s3Service;

    public File saveImageBase64(String base64Image) throws IOException {
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Create a ByteArrayInputStream from the decoded bytes
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

        // Upload to S3
        return s3Service.uploadFileFromStream(inputStream, "image.jpg");
    }
}
