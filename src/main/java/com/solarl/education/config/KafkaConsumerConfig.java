package com.solarl.education.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.solarl.education.response.AdvertisementResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.topic.advertisement}")
    private String advertisementTopicName;

    @Value("${spring.kafka.bootstrap-servers}")
    private List<String> bootstrapServers;

    public ConsumerFactory<String, AdvertisementResponse> emailNotificationConsumerFactory() {
        Map<String, Object> props = setDefaultConfig(advertisementTopicName);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                advertisementKafkaDeserializer()
        );
    }

    public ConsumerFactory<String, AdvertisementResponse> telegramNotificationConsumerFactory() {
        Map<String, Object> props = setDefaultConfig(advertisementTopicName);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                advertisementKafkaDeserializer()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AdvertisementResponse> emailNotificationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AdvertisementResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailNotificationConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AdvertisementResponse> telegramNotificationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AdvertisementResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(telegramNotificationConsumerFactory());
        return factory;
    }

    private Deserializer<AdvertisementResponse> advertisementKafkaDeserializer() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return new JsonDeserializer<>(AdvertisementResponse.class, mapper);
    }

    private Map<String, Object> setDefaultConfig(String topicName) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, topicName);

        return props;
    }
}
