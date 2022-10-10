package br.dev.rodrigocury.springdocumentprocessor.adapter.config.kafka;

import br.dev.rodrigocury.springdocumentprocessor.adapter.config.properties.KafkaConfigProperties;
import br.dev.rodrigocury.springdocumentprocessor.application.document.DocumentImpl;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaConfigProperties.class)
public class KafkaDocumentConsumerConfiguration {

    private final KafkaConfigProperties properties;

    @Bean
    public ConsumerFactory<String, DocumentImpl> documentKafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapAddress());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), documentJsonDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DocumentImpl> documentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DocumentImpl> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(documentKafkaConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, UUID> deletionKafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapAddress());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new UUIDDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UUID> deletionKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UUID> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deletionKafkaConsumerFactory());
        return factory;
    }

    public JsonDeserializer<DocumentImpl> documentJsonDeserializer() {
        JsonDeserializer<DocumentImpl> deserializer = new JsonDeserializer<>(DocumentImpl.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        return deserializer;
    }

}
