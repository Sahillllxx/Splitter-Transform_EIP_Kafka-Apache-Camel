package com.learning.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.utils.KafkaEndpointBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteExceptionHandler extends RouteBuilder {

    private final KafkaEndpointBuilder endpointBuilder;

    @Override
    public void configure() throws Exception {

        errorHandler(defaultErrorHandler()
            .maximumRedeliveries(3)
            .redeliveryDelay(2000)
            .retryAttemptedLogLevel(org.apache.camel.LoggingLevel.WARN)
            .logStackTrace(true)
        );

        onException(JsonProcessingException.class)
            .handled(true)
            .log("JSON processing error: ${exception.message}")
            .to(StaticEndpointBuilders.log("json-exception"))
            .to(endpointBuilder.directDeadLetterQueue());

        onException(NullPointerException.class)
            .handled(true)
            .log("Null pointer exception: ${exception.message}")
            .to(StaticEndpointBuilders.log("null-pointer-exception"))
            .to(endpointBuilder.directDeadLetterQueue());

        onException(IllegalArgumentException.class)
            .handled(true)
            .log("Illegal argument: ${exception.message}")
            .to(StaticEndpointBuilders.log("illegal-argument-exception"))
            .to(endpointBuilder.directDeadLetterQueue());

        onException(Exception.class)
            .handled(true)
            .log("General exception caught: ${exception.message}")
            .to(StaticEndpointBuilders.log("general-exception"))
            .to(endpointBuilder.directDeadLetterQueue());

        from(endpointBuilder.directDeadLetterQueue())
            .log("Sending message to Dead Letter Queue Kafka topic: ${body}")
            .to(endpointBuilder.kafkaDlqUri());
    }

}
