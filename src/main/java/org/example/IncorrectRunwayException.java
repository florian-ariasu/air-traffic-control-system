package org.example;

public class IncorrectRunwayException extends RuntimeException {
    public IncorrectRunwayException(String ts) {
        super(ts + " | The chosen runway for allocating the plane is incorrect");
    }
}
