package br.dev.rodrigocury.springdocumentprocessor.adapter.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "br.dev.rodrigocury.kafka")
public class KafkaConfigProperties {
    private String bootstrapAddress;
    private String groupId;
}