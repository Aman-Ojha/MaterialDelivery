package com.example.demo.kafka;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.kafka.core.KafkaTemplate;

@Component
@RequiredArgsConstructor
@Validated
@Slf4j
public class MaterialDeliveryEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(@Valid MaterialDeliveredEvent event) {  // ← validation runs automatically
        log.info("Sending event to Kafka → topic=material-events key={} payload={}",
                event.plantName(), event);
        kafkaTemplate.send("material-events", event.plantName(), event);
    }
}
