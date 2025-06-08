package com.learning.route;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.model.Flight;
import com.learning.utils.KafkaEndpointBuilder;
import com.learning.utils.RouteEndpointConstants;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import java.util.List;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
@RequiredArgsConstructor
public class KafkaFlightRoute extends RouteBuilder {

    private final ObjectMapper objectMapper;
    private final KafkaEndpointBuilder endpointBuilder;

    @Override
    public void configure() {
        from(endpointBuilder.kafkaInputUri())
                .routeId(RouteEndpointConstants.KAFKA_FLIGHT_ROUTE_ID)

                .log("Received batch of flights: ${body}")

                .process(exchange -> {
                    List<Flight> flights = objectMapper.readValue(exchange.getIn().getBody(String.class),
                            new TypeReference<>() {});
                    exchange.getIn().setBody(flights);
                })
                .split(body())

                .to(direct(RouteEndpointConstants.DIRECT_STATEFUL_FLIGHT),
                        direct(RouteEndpointConstants.DIRECT_TRANSFORM_FLIGHT));
    }
}
