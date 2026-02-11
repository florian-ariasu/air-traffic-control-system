package org.example;

import java.time.LocalTime;
import java.util.*;

public class Runway<T extends Airplane> {
    private String id;
    private String usage;
    private PriorityQueue<T> queue;
    private Comparator<T> airplaneComparator;
    private LocalTime occupiedUntil = LocalTime.MIN;

    public Runway(String id, String usage) {
        this.id = id;
        this.usage = usage;

        this.airplaneComparator = (a1, a2) -> {
            if (usage.equals("landing")) {
                if (a1.isUrgent() && !a2.isUrgent()) return -1;
                if (!a1.isUrgent() && a2.isUrgent()) return 1;
            }
            return a1.getDesiredTime().compareTo(a2.getDesiredTime());
        };

        this.queue = new PriorityQueue<>(airplaneComparator);
    }

    public void addAirplane(T airplane) {
        queue.add(airplane);
    }

    public T pollAirplane() {
        if (queue.isEmpty()) return null;
        return this.queue.poll();
    }

    public boolean isOccupied(LocalTime currentTime) {
        return !currentTime.isAfter(occupiedUntil);
    }

    public void setOccupiedUntil(LocalTime time) {
        this.occupiedUntil = time;
    }

    public String getId() {
        return id;
    }

    public String getUsage() {
        return usage;
    }

    public String getRunwayInfo(LocalTime currentTime) {
        PriorityQueue<T> copy = new PriorityQueue<>(this.airplaneComparator);
        copy.addAll(this.queue);
        StringBuilder sb = new StringBuilder(id + " - " + (isOccupied(currentTime) ? "OCCUPIED" : "FREE"));

        while (!copy.isEmpty()) {
            sb.append("\n").append(copy.poll().toString());
        }

        return sb.toString();
    }
}
