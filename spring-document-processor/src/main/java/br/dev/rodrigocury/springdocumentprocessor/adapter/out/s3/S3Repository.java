package br.dev.rodrigocury.springdocumentprocessor.adapter.out.s3;

import br.dev.rodrigocury.springdocumentprocessor.application.exceptions.UnableToDeleteException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.dev.rodrigocury.springdocumentprocessor.adapter.config.s3.S3Configuration.bucketName;

@Repository
@AllArgsConstructor
public class S3Repository {

    private final S3Client s3Client;

    public UUID uploadFile(byte[] bytes) {
        UUID objectKey = UUID.randomUUID();
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey.toString())
                .build();

        s3Client.putObject(putOb, RequestBody.fromBytes(bytes));

        return objectKey;
    }

    public InputStream getFile(UUID objectKey) {
        GetObjectRequest objectRequest = GetObjectRequest
                .builder()
                .key(objectKey.toString())
                .bucket(bucketName)
                .build();

        return s3Client.getObject(objectRequest);
    }

    public void deleteFiles(List<UUID> s3References) {
        List<ObjectIdentifier> keys = s3References.stream()
            .map((id) -> ObjectIdentifier.builder()
                .key(id.toString())
                .build()
            ).toList();

        Delete del = Delete.builder()
                .objects(keys)
                .build();

        try {
            DeleteObjectsRequest multiObjectDeleteRequest = DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(del)
                    .build();

            s3Client.deleteObjects(multiObjectDeleteRequest);
        } catch (S3Exception e) {
            throw new UnableToDeleteException();
        }
    }
}
