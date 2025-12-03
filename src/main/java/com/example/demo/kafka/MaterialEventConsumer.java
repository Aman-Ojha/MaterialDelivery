package com.example.demo.kafka;

import com.example.demo.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialEventConsumer {

    private final DeliveryService deliveryService;
    private final MaterialDeliveryEventProducer producer;

    @KafkaListener(
            topics = "material-events",
            groupId = "port-service",
            concurrency = "3",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(
            @Valid MaterialDeliveredEvent event,
            Acknowledgment ack,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        try {
            deliveryService.recordDeliveryFromEvent(event);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to process event {} (key={}), sending to DLT", event, key, e);
            producer.send(event);
            ack.acknowledge(); 
        }
    }

    @KafkaListener(
            topics = "material-events.dlt",
            groupId = "port-service-dlt",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleDlt(
            MaterialDeliveredEvent event,
            @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage,
            @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {
        log.error("DLT received - event: {}, key: {}, error: {}", event, key, errorMessage);
        // TODO: persist to DB / send alert / store for manual retry
    }
}