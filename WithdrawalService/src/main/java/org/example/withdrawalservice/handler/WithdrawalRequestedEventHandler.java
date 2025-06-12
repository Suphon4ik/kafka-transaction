package org.example.withdrawalservice.handler;

import org.example.core.events.WithdrawalRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "withdraw-money-topic",
        containerFactory = "kafkaListenerContainerFactory")
public class WithdrawalRequestedEventHandler {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(@Payload WithdrawalRequestedEvent withdrawalRequestedEvent) {
        LOGGER.info("Received a new withdrawal even: {}", withdrawalRequestedEvent.getAmount());

    }
}
