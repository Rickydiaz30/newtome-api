package com.newtome.newtomeapi.uploads;

import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.*;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.regions.Region;

import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final S3Presigner presigner = S3Presigner.builder()
            .region(Region.US_EAST_1)
            .build();

    private final String bucket = "newtome-images-115944781330-us-east-1-an";

    @PostMapping("/presigned-url")
    public Map<String, String> getPresignedUrl(@RequestParam String filename) {

        String key = "listings/" + UUID.randomUUID() + "-" + filename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();

        URL url = presigner.presignPutObject(presignRequest).url();

        String imageUrl = "https://" + bucket + ".s3.amazonaws.com/" + key;

        return Map.of(
                "uploadUrl", url.toString(),
                "imageUrl", imageUrl
        );
    }
}