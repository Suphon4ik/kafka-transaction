package org.example.depisiteservice.handler;

import org.example.core.events.DepositRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "deposit-money-topic", containerFactory = "kafkaListenerContainerFactory")
public class DepositRequestedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(DepositRequestedEvent requestedEvent) {
        LOGGER.info("Received a new deposit event: {}", requestedEvent.getAmount());
    }
}
