package com.nada.server.commons;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Utils {
    private final AmazonS3Client amazonS3Client;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile file) throws IOException {

        String fileName = LocalDateTime.now().format(formatter);

        amazonS3Client.putObject(new PutObjectRequest(bucket, "cards/"+fileName, file.getInputStream(), null)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteBackground(String url){
        String key = url.split("https://nada-server.s3.ap-northeast-2.amazonaws.com/")[1];
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}