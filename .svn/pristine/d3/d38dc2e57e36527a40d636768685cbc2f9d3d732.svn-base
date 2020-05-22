package tms.network;

import tms.intersection.Intersection;
import tms.sensors.DemoPressurePad;
import tms.sensors.DemoSpeedCamera;
import tms.sensors.DemoVehicleCount;
import tms.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkInitialiser {
    public static final String LINE_INFO_SEPARATOR = ":";
    public static final String LINE_LIST_SEPARATOR = ";";

    public NetworkInitialiser() {}

    public static Network loadNetwork(String filename)
            throws IOException,
            InvalidNetworkException {
        Network network = new Network();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String currentLine = bufferedReader.readLine();
        List<String> intersectionsWithTrafficLights = new ArrayList<>();
        int currentLineNumber = 1;
        int numIntersections = 0;
        int numRoutes = 0;

        while (currentLine != null) {
            if (currentLine.startsWith(";")) {
                currentLine = bufferedReader.readLine();
                continue;
            }

            switch(currentLineNumber) {
                case 1:
                    numIntersections = Integer.parseInt(currentLine);
                    break;
                case 2:
                    numRoutes = Integer.parseInt(currentLine);
                    break;
                case 3:
                    network.setYellowTime(Integer.parseInt(currentLine));
                    break;
                case 4:
                    // Parse intersections without traffic lights
                    for (int i = 0; i < numIntersections; i++) {
                        if (currentLine.contains(":")) {
                            intersectionsWithTrafficLights.add(currentLine);
                            String[] intersectionValues = currentLine.split(":");
                            network.createIntersection(intersectionValues[0]);
                        } else {
                            network.createIntersection(currentLine);
                        }

                        if (i + 1 < numIntersections) {
                            currentLine = bufferedReader.readLine();
                        }
                    }
                    break;
                case 5:
                    // Parse routes and sensors
                    for (int i = 0; i < numRoutes; i++) {
                        String[] routeValues = currentLine.split(":");

                        // add Route
                        try {
                            network.connectIntersections(
                                    routeValues[0],
                                    routeValues[1],
                                    Integer.parseInt(routeValues[2])
                            );
                        } catch (IntersectionNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Adding Sensors
                        for (int j = 0; j < Integer.parseInt(routeValues[3]); j++) {
                            currentLine = bufferedReader.readLine();
                            String[] sensorValues = currentLine.split(":");

                            // Converting sensor values to int
                            String[] dataString = sensorValues[2].split(",");
                            int[] dataInt = new int[dataString.length];
                            for(int k = 0; k < dataString.length; k++) {
                                dataInt[k] = Integer.parseInt(dataString[k]);
                            }

                            try {
                                switch (sensorValues[0]) {
                                    case "PP":
                                        network.addSensor(
                                                routeValues[0],
                                                routeValues[1],
                                                new DemoPressurePad(dataInt, Integer.parseInt(sensorValues[1])));
                                        break;
                                    case "SC":
                                        network.addSensor(
                                                routeValues[0],
                                                routeValues[1],
                                                new DemoSpeedCamera(dataInt, Integer.parseInt(sensorValues[1])));
                                        break;
                                    case "VC":
                                        network.addSensor(
                                                routeValues[0],
                                                routeValues[1],
                                                new DemoVehicleCount(dataInt, Integer.parseInt(sensorValues[1])));
                                        break;
                                }
                            } catch (DuplicateSensorException | IntersectionNotFoundException | RouteNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                        if (i + 1 < numRoutes) {
                            currentLine = bufferedReader.readLine();
                        }

                        // add speedSign
                        if (routeValues.length > 4) {
                            try {
                                network.addSpeedSign(routeValues[0], routeValues[1], Integer.parseInt(routeValues[4]));
                            } catch (IntersectionNotFoundException | RouteNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    break;
                case 6:
                    // Create traffic lights
                    for (int i = 0; i < intersectionsWithTrafficLights.size(); i++) {
                        String[] intersectionValues = intersectionsWithTrafficLights.get(i).split(":");
                        List<String> intersectionOrder = new ArrayList<>();
                        for (String originIntersection: intersectionValues[2].split(",")) {
                            intersectionOrder.add(originIntersection);
                        }
                        try {
                            network.addLights(intersectionValues[0],
                                    Integer.parseInt(intersectionValues[1]),
                                    intersectionOrder);
                        } catch (IntersectionNotFoundException | InvalidOrderException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            currentLineNumber++;
            currentLine = bufferedReader.readLine();
        }

        bufferedReader.close();
        return network;
    }
}
