package br.dev.rodrigocury.springdocumentprocessor.adapter.config.s3;

import br.dev.rodrigocury.springdocumentprocessor.adapter.config.properties.S3ConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.net.URI;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(S3ConfigProperties.class)
public class S3Configuration {

    private final S3ConfigProperties properties;

    public static String bucketName = "documents";

    private AwsCredentials credential() {
        return AwsBasicCredentials.create(
            properties.getSecretKeyId(),
            properties.getAccessKey()
        );
    }

    @Bean
    public S3Client s3Client() {
        Region region = Region.US_EAST_1;

        S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create(properties.getUri()))
                .region(region)
                .credentialsProvider(this::credential)
                .build();

        createBucket(s3Client);

        return s3Client;
    }

    @Bean
    public SmartInitializingSingleton importProcessor(S3Client client) {
        return () -> {
            createBucket(client);
        };

    }

    public void createBucket(S3Client client) {

        ListBucketsResponse listBucketsResponse = client.listBuckets();
        boolean bucketExists = listBucketsResponse.buckets()
                .stream()
                .anyMatch(bucket -> bucket.name().equals(bucketName));

        if (!bucketExists) {
            try {
                S3Waiter s3Waiter = client.waiter();
                CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build();

                client.createBucket(bucketRequest);
                HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                        .bucket(bucketName)
                        .build();

                s3Waiter.waitUntilBucketExists(bucketRequestWait);
                log.info(bucketName +" is ready");

            } catch (S3Exception e) {
                e.printStackTrace();
            }

        }
    }
}
