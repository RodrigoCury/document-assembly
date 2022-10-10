package br.dev.rodrigocury.documentassembler.adapter.config;

import br.dev.rodrigocury.documentassembler.adapter.config.props.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaConfigProperties properties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapAddress());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic documentTopic() {
        return new NewTopic(properties.getDocumentTopic(), 1, (short) 1);
    }

    @Bean
    public NewTopic documentConsoleTopic() {
        return new NewTopic(properties.getDocumentConsoleTopic(), 1, (short) 1);
    }

}
