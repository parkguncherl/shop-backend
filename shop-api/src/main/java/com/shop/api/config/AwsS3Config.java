package com.shop.api.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AwsS3Config {

    @Value("${aws.s3.access.key}")
    private String ACCESS_KEY;

    @Value("${aws.s3.secret.key}")
    private String SECRET_KEY;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        //리전 : 아시아 태평양(서울) ap-northeast-2
        //arn:aws:s3:::binblur-file
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // 원하는 리전으로 변경
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        return S3Presigner.builder()
                .region(Region.AP_NORTHEAST_2) // 자신의 리전으로 변경
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}
