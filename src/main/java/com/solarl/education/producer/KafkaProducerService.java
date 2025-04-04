package com.solarl.education.producer;

import com.solarl.education.response.AdvertisementResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {

    @Value("${kafka.producer.topic.advertisement}")
    private String advertisementTopicName;

    public final KafkaTemplate<String, AdvertisementResponse> advertisementKafkaTemplate;

    public KafkaProducerService(
            @Qualifier(value = "advertisementKafkaTemplate") KafkaTemplate<String, AdvertisementResponse> advertisementKafkaTemplate
    ) {
        this.advertisementKafkaTemplate = advertisementKafkaTemplate;
    }

    public void sendAdvertisement(AdvertisementResponse advertisement) {
        advertisementKafkaTemplate.send(advertisementTopicName, advertisement);
        log.info("Kafka: сообщение отправлено в топик: {} {}", advertisementTopicName, advertisement);
    }
}
