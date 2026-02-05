package com.shop.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class CloudflareR2Config {

    @Value("${cloudflare.r2.accountId}")
    private String ACCOUNT_ID;

    @Value("${cloudflare.r2.access.key}")
    private String ACCESS_KEY;

    @Value("${cloudflare.r2.secret.key}")
    private String SECRET_KEY;


    /**
     * Creates a new CloudflareR2Client with the provided configuration
     */
    @Bean
    @Qualifier("cloudflareR2Client")
    public S3Client CloudflareR2Client() {
        S3Config config = new S3Config(
                ACCOUNT_ID,
                ACCESS_KEY,
                SECRET_KEY
        );
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                config.getAccessKey(),
                config.getSecretKey()
        );

        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        return S3Client.builder()
                .endpointOverride(URI.create(config.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto")) // Required by SDK but not used by R2
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    /**
     * Configuration class for R2 credentials and endpoint
     * - accountId: Your Cloudflare account ID
     * - accessKey: Your R2 Access Key ID (see: https://developers.cloudflare.com/r2/api/tokens)
     * - secretKey: Your R2 Secret Access Key (see: https://developers.cloudflare.com/r2/api/tokens)
     */
    @Getter
    public static class S3Config {
        private final String accountId;
        private final String accessKey;
        private final String secretKey;
        private final String endpoint;

        public S3Config(String accountId, String accessKey, String secretKey) {
            this.accountId = accountId;
            this.accessKey = accessKey;
            this.secretKey = secretKey;
            this.endpoint = String.format("https://%s.r2.cloudflarestorage.com", accountId);
        }
    }

}
