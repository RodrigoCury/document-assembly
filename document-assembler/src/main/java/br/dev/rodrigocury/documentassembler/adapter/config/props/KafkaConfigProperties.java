package br.dev.rodrigocury.documentassembler.adapter.config.props;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "br.dev.rodrigocury.kafka")
public class KafkaConfigProperties {
    private String documentTopic;
    private String documentConsoleTopic;
    private String documentDeletionTopic;
    private String bootstrapAddress;
}