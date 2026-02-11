package org.example;

import java.time.LocalTime;

public class WideBodyAirplane extends Airplane {
    public WideBodyAirplane(String model, String flightID, String source, String destination, LocalTime desiredTime) {
        super(model, flightID, source, destination, desiredTime);
    }

    public String toString() {
        return "Wide Body - " + super.toString();
    }
}
