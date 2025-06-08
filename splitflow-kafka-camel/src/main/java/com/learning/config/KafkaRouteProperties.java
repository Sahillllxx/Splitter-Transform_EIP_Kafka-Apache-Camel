package com.learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaRouteProperties {
    private String inputTopic;
    private String outputTopic;
    private String dlqTopic;

}
