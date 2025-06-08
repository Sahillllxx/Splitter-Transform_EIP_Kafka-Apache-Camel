package com.learning.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    private String flightNumber;
    private String departure;
    private String arrival;
    private String departureTime;
    private String arrivalTime;
    private String duration;

}
