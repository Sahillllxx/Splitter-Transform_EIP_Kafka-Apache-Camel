package com.learning.processor;

import com.learning.model.Flight;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class StatefulFlightProcessor implements Processor {

    private final Map<String, Integer> flightCounter = new ConcurrentHashMap<>();

    @Override
    public void process(Exchange exchange) {
        Flight flight = exchange.getIn().getBody(Flight.class);

        String departure = flight.getDeparture();
        int count = flightCounter.merge(departure, 1, Integer::sum);
        exchange.getMessage().setHeader("FlightCount", count);
    }
}
