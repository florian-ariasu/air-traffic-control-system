package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Airplane {
    protected static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    protected String model;
    protected String flightID;
    protected String source;
    protected String destination;
    protected LocalTime desiredTime;
    protected LocalTime actualTime;
    protected PlaneStatus status;
    protected boolean isUrgent = false;

    public Airplane(String model, String flightID, String source, String destination, LocalTime desiredTime) {
        this.model = model;
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;
        this.desiredTime = desiredTime;
        this.status = source.equals("Bucharest") ? PlaneStatus.WAITING_FOR_TAKEOFF : PlaneStatus.WAITING_FOR_LANDING;
    }

    public String getFlightID() {
        return flightID;
    }

    public LocalTime getDesiredTime() {
        return desiredTime;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        this.isUrgent = urgent;
    }

    public void setActualTime(LocalTime actualTime) {
        this.actualTime = actualTime;
    }

    public void setStatus(PlaneStatus status) {
        this.status = status;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(model).append(" - ").append(flightID).append(" - ")
                .append(source).append(" - ").append(destination).append(" - ")
                .append(status).append(" - ").append(desiredTime.format(TIME_FORMAT));

        if (actualTime != null) {
            sb.append(" - ").append(actualTime.format(TIME_FORMAT));
        }

        return sb.toString();
    }
}
