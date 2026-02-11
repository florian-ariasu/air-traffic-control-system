package org.example;

public class UnavailableRunwayException extends RuntimeException {
    public UnavailableRunwayException(String ts) {
        super(ts + " | The chosen runway for maneuver is currently occupied");
    }
}

