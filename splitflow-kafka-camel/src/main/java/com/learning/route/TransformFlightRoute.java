package com.learning.route;


import com.learning.model.Flight;
import com.learning.processor.TransformFlightProcessor;
import com.learning.utils.KafkaEndpointBuilder;
import com.learning.utils.RouteEndpointConstants;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
@RequiredArgsConstructor
public class TransformFlightRoute extends RouteBuilder {

    private final TransformFlightProcessor processor;
    private final KafkaEndpointBuilder endpointBuilder;

    @Override
    public void configure() {

        from(direct(RouteEndpointConstants.DIRECT_TRANSFORM_FLIGHT))
            .routeId(RouteEndpointConstants.TRANSFORM_FLIGHT_ROUTE_ID)
            .process(processor)
                .log("Transformed flight Duration: ${body.duration}")

                .marshal()
                .json(JsonLibrary.Jackson, Flight.class)

                .to(endpointBuilder.kafkaTransformedUri());
    }
}
