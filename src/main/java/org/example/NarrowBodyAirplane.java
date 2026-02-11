package org.example;

import java.time.LocalTime;

public class NarrowBodyAirplane extends Airplane {
    public NarrowBodyAirplane(String model, String flightID, String source, String destination, LocalTime desiredTime) {
        super(model, flightID, source, destination, desiredTime);
    }

    public String toString() {
        return "Narrow Body - " + super.toString();
    }
}
