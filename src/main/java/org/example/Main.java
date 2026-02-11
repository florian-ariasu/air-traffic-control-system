package org.example;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final Map<String, Runway<?>> runways = new LinkedHashMap<>();
    private static final Map<String, Airplane> allFlights = new HashMap<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        runways.clear();
        allFlights.clear();

        String testFolder = "src/main/resources/" + args[0];
        String inputFile = testFolder + "/input.in";

        try (Scanner sc = new Scanner(new File(inputFile))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" - ");
                String timestampStr = parts[0];
                LocalTime currentTime = LocalTime.parse(timestampStr, TIME_FORMAT);
                String command = parts[1];

                processCommand(command, parts, currentTime, testFolder, timestampStr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void processCommand(String command, String[] parts, LocalTime time, String folder, String tsStr) {
        switch (command) {
            case "add_runway_in_use":
                String id = parts[2];
                String usage = parts[3];
                String planeType = parts[4];

                if (planeType.equals("wide body")) {
                    runways.put(id, new Runway<WideBodyAirplane>(id, usage));
                } else {
                    runways.put(id, new Runway<NarrowBodyAirplane>(id, usage));
                }
                break;

            case "allocate_plane":
                handleAllocation(parts, time, tsStr, folder);
                break;

            case "permission_for_maneuver":
                handleManeuver(parts[2], time, tsStr, folder);
                break;

            case "runway_info":
                writeRunwayInfo(parts[2], time, folder);
                break;

            case "flight_info":
                writeFlightInfo(parts[2], time, folder, tsStr);
                break;

            case "exit":
                return;
        }
    }

    private static void handleAllocation(String[] parts, LocalTime time, String tsStr, String folder) {
        String bodyType = parts[2];
        String model = parts[3];
        String flightID = parts[4];
        String source = parts[5];
        String destination = parts[6];
        LocalTime desiredTime = LocalTime.parse(parts[7], TIME_FORMAT);
        String runwayId = parts[8];
        boolean isUrgent = parts.length > 9 && parts[9].equals("urgent");

        Runway runway = runways.get(runwayId);
        Airplane plane;

        if (bodyType.equals("wide body")) {
            plane = new WideBodyAirplane(model, flightID, source, destination, desiredTime);
        } else {
            plane = new NarrowBodyAirplane(model, flightID, source, destination, desiredTime);
        }

        plane.setUrgent(isUrgent);
        allFlights.put(flightID, plane);

        try {
            boolean wantsLanding = destination.equals("Bucharest");
            if (wantsLanding && !runway.getUsage().equals("landing")) {
                throw new IncorrectRunwayException(tsStr);
            }

            if (!wantsLanding && !runway.getUsage().equals("takeoff")) {
                throw new IncorrectRunwayException(tsStr);
            }

            runway.addAirplane(plane);
        } catch (IncorrectRunwayException e) {
            writeToExceptionFile(e.getMessage(), folder);
        }
    }

    public static void handleManeuver(String runwayId, LocalTime time, String tsStr, String folder) {
        Runway runway = runways.get(runwayId);

        try {
            if (runway.isOccupied(time)) {
                throw new UnavailableRunwayException(tsStr);
            }

            Airplane plane = runway.pollAirplane();
            if (plane != null) {
                if (runway.getUsage().equals("landing")) {
                    plane.setStatus(PlaneStatus.LANDED);
                    runway.setOccupiedUntil(time.plusMinutes(10));
                } else {
                    plane.setStatus(PlaneStatus.DEPARTED);
                    runway.setOccupiedUntil(time.plusMinutes(5));
                }

                plane.setActualTime(time);
            }
        } catch (UnavailableRunwayException e) {
            writeToExceptionFile(e.getMessage(), folder);
        }
    }

    public static void writeRunwayInfo(String runwayId, LocalTime time, String folder) {
        Runway runway = runways.get(runwayId);
        String tsFilename = time.format(TIME_FORMAT).replace(":", "-");
        String fileName = folder + "/runway_info_" + runwayId + "_" + tsFilename + ".out";

        try (PrintWriter pw = new PrintWriter(new File(fileName))) {
            pw.println(runway.getRunwayInfo(time));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFlightInfo(String flightID, LocalTime time, String folder, String tsStr) {
        Airplane plane = allFlights.get(flightID);
        if (plane == null) {
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(folder + "/flight_info.out", true))) {
            pw.println(tsStr + " | " + plane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToExceptionFile(String message, String folder) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(folder + "/board_exceptions.out", true))) {
            pw.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
