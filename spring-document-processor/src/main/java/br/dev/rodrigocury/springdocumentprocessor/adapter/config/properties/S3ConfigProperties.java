package br.dev.rodrigocury.springdocumentprocessor.adapter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "br.dev.rodrigocury.s3")
public class S3ConfigProperties {
    String secretKeyId;
    String accessKey;
    String uri;
}
