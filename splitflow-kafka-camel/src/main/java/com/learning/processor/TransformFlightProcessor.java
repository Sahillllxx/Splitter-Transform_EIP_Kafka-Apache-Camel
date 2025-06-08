package com.learning.processor;

import com.learning.model.Flight;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransformFlightProcessor implements Processor {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void process(Exchange exchange) {
        Flight flight = exchange.getIn().getBody(Flight.class);

        LocalDateTime departureTime = LocalDateTime.parse(flight.getDepartureTime(), FORMATTER);
        LocalDateTime arrivalTime = LocalDateTime.parse(flight.getArrivalTime(), FORMATTER);

        Duration duration = Duration.between(departureTime, arrivalTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        String formattedDuration = hours + "h " + minutes + "m";
        flight.setDuration(formattedDuration);

        exchange.getIn().setBody(flight);
    }
}
