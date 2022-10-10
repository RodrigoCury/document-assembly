package br.dev.rodrigocury.documentassembler.adapter.out.kafka;

import br.dev.rodrigocury.documentassembler.adapter.config.props.KafkaConfigProperties;
import br.dev.rodrigocury.documentassembler.adapter.out.document.po.DocumentPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Slf4j
@Component()
@RequiredArgsConstructor
public class DocumentKafkaMessageProducer {
    private final KafkaTemplate<String, DocumentPo> kafkaTemplate;
    private final KafkaTemplate<String, UUID> uuidKafkaTemplate;
    private final KafkaConfigProperties properties;

    private static final ListenableFutureCallback<SendResult<String, ?>> producerCalbacks = new ListenableFutureCallback<>() {
        public void onFailure(Throwable ex) {
            log.error(String.format("Error sending message to document broker: %s", ex.getMessage()), ex);
        }

        @Override
        public void onSuccess(SendResult<String, ?> result) {
            log.info("Message was sent Succesfully");
        }
    };

    public void sendDocumentMessage(DocumentPo msg) {
        ListenableFuture<SendResult<String, DocumentPo>> future = kafkaTemplate.send(properties.getDocumentTopic(), msg);
        future.addCallback(producerCalbacks);
    }

    public void sendDocumentConsoleMessage(DocumentPo msg) {
        ListenableFuture<SendResult<String, DocumentPo>> future = kafkaTemplate.send(properties.getDocumentConsoleTopic(), msg);
        future.addCallback(producerCalbacks);
    }

    public void sendDeletionMessage(UUID documentId) {
        ListenableFuture<SendResult<String, UUID>> future = uuidKafkaTemplate.send(properties.getDocumentDeletionTopic(), documentId);
        future.addCallback(producerCalbacks);
    }

}
