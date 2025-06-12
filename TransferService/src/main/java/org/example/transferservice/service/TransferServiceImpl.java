package org.example.transferservice.service;

import org.example.core.events.DepositRequestedEvent;
import org.example.core.events.WithdrawalRequestedEvent;
import org.example.transferservice.error.TransferServiceException;
import org.example.transferservice.model.TransferRestModel;
import org.example.transferservice.persistance.TransferEntity;
import org.example.transferservice.persistance.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class TransferServiceImpl implements TransferService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestTemplate restTemplate;
    private final Environment environment;
    private final TransferRepository transferRepository;

    public TransferServiceImpl(KafkaTemplate<String, Object> kafkaTemplate,
                               RestTemplate restTemplate,
                               Environment environment,
                               TransferRepository transferRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
        this.environment = environment;
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional("transactionManager")
    public boolean transfer(TransferRestModel transferRestModel) {

        WithdrawalRequestedEvent withdrawalRequestedEvent = new WithdrawalRequestedEvent(
                transferRestModel.getSenderId(),
                transferRestModel.getRecipientId(),
                transferRestModel.getAmount()
        );
        DepositRequestedEvent depositRequestedEvent = new DepositRequestedEvent(
                transferRestModel.getSenderId(),
                transferRestModel.getRecipientId(),
                transferRestModel.getAmount()
        );

        try {
            TransferEntity transferEntity = new TransferEntity();
            BeanUtils.copyProperties(transferRestModel, transferEntity);
            transferEntity.setTransferId(UUID.randomUUID().toString());
            transferRepository.save(transferEntity);

            kafkaTemplate.send(environment.getProperty(
                    "withdraw-money-topic", "withdraw-money-topic"
            ), withdrawalRequestedEvent);
            LOGGER.info("Send event to withdrawal topic");

            //Business logic that causes and error
            callRemoteService();

            kafkaTemplate.send(environment.getProperty(
                    "deposit-money-topic", "deposit-money-topic"
            ), depositRequestedEvent);
            LOGGER.info("Send event to deposit topic");

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new TransferServiceException(e.getMessage());
        }

        return true;
    }

    private ResponseEntity<String> callRemoteService() throws Exception {
        String requestURL = "http://localhost:8082/response/200";
        ResponseEntity<String> response = restTemplate.exchange(
            requestURL, HttpMethod.GET, null, String.class
        );

        if (response.getStatusCode().value() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            throw new Exception("Destination Microservice not available");
        }

        if (response.getStatusCode().value() == HttpStatus.OK.value()) {
            LOGGER.info("Received response from mock service:" + response.getBody());
        }

        return response;
    }

}
