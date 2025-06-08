package com.learning.utils;

import com.learning.config.KafkaRouteProperties;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEndpointBuilder {

    private final KafkaRouteProperties properties;

    @Value("${camel.component.kafka.brokers}")
    private String kafkaBrokers;

    public String kafkaInputUri() {
        return StaticEndpointBuilders
                .kafka(properties.getInputTopic())
                .brokers(kafkaBrokers)
                .getUri();
    }

    public String kafkaTransformedUri() {
        return StaticEndpointBuilders
                .kafka(properties.getOutputTopic())
                .brokers(kafkaBrokers)
                .getUri();
    }

    public String directDeadLetterQueue() {
        return StaticEndpointBuilders.direct("deadLetterQueue").getUri();
    }

    public String kafkaDlqUri() {
        return StaticEndpointBuilders
                .kafka(properties.getOutputTopic())
                .brokers(kafkaBrokers)
                .getUri();
    }
}
