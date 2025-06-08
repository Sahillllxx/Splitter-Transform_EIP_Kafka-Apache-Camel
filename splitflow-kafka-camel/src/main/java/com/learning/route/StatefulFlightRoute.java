package com.learning.route;


import com.learning.processor.StatefulFlightProcessor;
import com.learning.utils.RouteEndpointConstants;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
@RequiredArgsConstructor
public class StatefulFlightRoute extends RouteBuilder {

    private final StatefulFlightProcessor statefulFlightProcessor;

    @Override
    public void configure() {
        from(direct(RouteEndpointConstants.DIRECT_STATEFUL_FLIGHT))
            .routeId(RouteEndpointConstants.STATEFUL_FLIGHT_ROUTE_ID)
            .process(statefulFlightProcessor)
            .log("Stateful count for ${body.departure}: ${headers.FlightCount}");
    }
}
