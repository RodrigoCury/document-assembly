package br.dev.rodrigocury.documentassembler.adapter.config;

import br.dev.rodrigocury.documentassembler.adapter.config.props.KafkaConfigProperties;
import br.dev.rodrigocury.documentassembler.adapter.out.document.po.DocumentPo;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = KafkaConfigProperties.class)
public class DocumentKafkaProducerConfig {

    private final KafkaConfigProperties properties;

    @Bean
    public ProducerFactory<String, DocumentPo> documentProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                properties.getBootstrapAddress());

        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, UUID> uuidProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                properties.getBootstrapAddress());

        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                UUIDSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, DocumentPo> documentKafkaTemplate(
        @Qualifier(value = "documentProducerFactory")
        ProducerFactory<String, DocumentPo> documentProducerFactory
    ) {
        return new KafkaTemplate<>(documentProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, UUID> uuidKafkaTemplate(
            @Qualifier(value = "uuidProducerFactory")
            ProducerFactory<String, UUID> documentProducerFactory
    ) {
        return new KafkaTemplate<>(documentProducerFactory);
    }

}