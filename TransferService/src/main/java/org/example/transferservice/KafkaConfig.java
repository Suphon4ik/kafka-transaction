package org.example.transferservice;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Autowired
    private Environment environment;

    Map<String, Object> config() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty(
                "spring.kafka.producer.bootstrap-servers"
        ));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, environment.getProperty(
                "spring.kafka.producer.acks"
                ));
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getProperty(
                "spring.kafka.producer.properties.delivery.timeout.ms")
        );
        props.put(ProducerConfig.LINGER_MS_CONFIG, environment.getProperty(
                "spring.kafka.producer.properties.linger.ms"
        ));
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, environment.getProperty(
                "spring.kafka.producer.properties.request.timeout.ms"
        ));
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, environment.getProperty(
                "spring.kafka.producer.properties.max.in.flight.requests.per.connection"
        ));
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, environment.getProperty(
                "spring.kafka.producer.properties.enable.idempotence"
        ));
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, environment.getProperty(
                "spring.kafka.producer.transaction-id-prefix"
        ));

        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(config());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(
            ProducerFactory<String, Object> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTransactionManager<String, Object> kafkaTransactionManager(
            ProducerFactory<String, Object> producerFactory
    ) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    NewTopic createWithdrawTopic() {
        return TopicBuilder.name(environment.getProperty("withdraw-money-topic"))
                .partitions(3).replicas(3).build();
    }

    @Bean
    NewTopic createDepositTopic() {
        return TopicBuilder.name(environment.getProperty("deposit-money-topic"))
                .partitions(3).replicas(3).build();
    }
}
