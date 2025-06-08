package com.learning.utils;

import com.learning.config.KafkaRouteProperties;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteEndpointConstants {

    private final KafkaRouteProperties properties;

    public static final String KAFKA_FLIGHT_ROUTE_ID = "kafka-flight-route";
    public static final String TRANSFORM_FLIGHT_ROUTE_ID = "transform-flight-route";
    public static final String STATEFUL_FLIGHT_ROUTE_ID = "stateful-flight-route";

    public static final String DIRECT_STATEFUL_FLIGHT = "stateful-flight";
    public static final String DIRECT_TRANSFORM_FLIGHT = "transform-flight";

}
