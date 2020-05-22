package tms.network;

import tms.intersection.Intersection;
import tms.route.Route;
import tms.sensors.Sensor;
import tms.util.DuplicateSensorException;
import tms.util.IntersectionNotFoundException;
import tms.util.InvalidOrderException;
import tms.util.RouteNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Network {
    private List<Intersection> intersections;
    private int yellowTime;

    public Network() {
        intersections = new ArrayList<>();
        yellowTime = 1;
    }

    public int getYellowTime() {
        return yellowTime;
    }

    public void setYellowTime(int yellowTime) {
        if (yellowTime < 1) {
            throw new IllegalArgumentException("yellowTime must be > 0");
        } else {
            this.yellowTime = yellowTime;
        }
    }

    public void createIntersection(String id)
            throws IllegalArgumentException {
        if (id.contains(":") || id.isBlank()) {
            throw new IllegalArgumentException();
        }

        try {
            findIntersection(id);
            throw new IllegalArgumentException();
        } catch (IntersectionNotFoundException e) {
            intersections.add(new Intersection(id));
        }

    }

    public void connectIntersections(String from,
                                      String to,
                                      int defaultSpeed)
            throws IntersectionNotFoundException,
            IllegalStateException,
            IllegalArgumentException {

        if (defaultSpeed < 0) {
            throw new IllegalArgumentException();
        }

        findIntersection(to).addConnection(findIntersection(from), defaultSpeed);
    }

    public void addLights(String intersectionId,
                           int duration,
                           List<String> intersectionOrder)
            throws IntersectionNotFoundException,
            InvalidOrderException,
            IllegalArgumentException {

        if (intersectionOrder.isEmpty()) {
            throw new InvalidOrderException();
        }

        if (duration < yellowTime + 1) {
            throw new IllegalArgumentException();
        }

        List<Route> routeList = new ArrayList<>(intersectionOrder.size());
        for (String currentIntersection: intersectionOrder) {
            try {
                routeList.add(this.getConnection(currentIntersection, intersectionId));
            } catch (RouteNotFoundException r) {
                //
            }
        }

        // Check if given order is not a permutation of incomingRoutes
        boolean permutationCheck = false;
        for (Route routeInIntersection: findIntersection(intersectionId).getConnections()) {
            for (Route routeInList: routeList) {
                if (routeInList.equals(routeInIntersection)) {
                    permutationCheck = true;
                }
            }

            if (permutationCheck) {
                permutationCheck = false;
            } else {
                throw new InvalidOrderException();
            }
        }

        findIntersection(intersectionId).addTrafficLights(routeList, yellowTime, duration);
    }

    public void addSpeedSign(String from,
                              String to,
                              int initialSpeed)
            throws IntersectionNotFoundException,
            RouteNotFoundException {

        if (initialSpeed < 0) {
            throw new IllegalArgumentException();
        }

        getConnection(from, to).addSpeedSign(initialSpeed);
    }

    public void setSpeedLimit(String from,
                               String to,
                               int newLimit)
            throws IntersectionNotFoundException,
            RouteNotFoundException {
        if (newLimit < 0) {
            throw new IllegalArgumentException();
        }

        if (getConnection(from, to).hasSpeedSign()) {
            getConnection(from, to).setSpeedLimit(newLimit);
        } else {
            throw new IllegalStateException();
        }
    }

    public void changeLightDuration(String intersectionId,
                                     int duration)
            throws IntersectionNotFoundException {
        if (!findIntersection(intersectionId).hasTrafficLights()) {
            throw new IllegalStateException();
        }

        if (duration < yellowTime + 1) {
            throw new IllegalArgumentException();
        }

        findIntersection(intersectionId).setLightDuration(duration);
    }

    public Route getConnection(String from,
                               String to)
            throws IntersectionNotFoundException,
            RouteNotFoundException {
        return findIntersection(to).getConnection(findIntersection(from));
    }

    public void addSensor(String from,
                           String to,
                           Sensor sensor)
            throws DuplicateSensorException,
            IntersectionNotFoundException,
            RouteNotFoundException {
        getConnection(from, to).addSensor(sensor);
    }

    public int getCongestion(String from,
                              String to)
            throws IntersectionNotFoundException,
            RouteNotFoundException {
        return getConnection(from, to).getCongestion();
    }

    public Intersection findIntersection(String id)
            throws IntersectionNotFoundException {
        for (Intersection currentIntersection: intersections) {
            if (currentIntersection.getId().equals(id)) {
                return currentIntersection;
            }
        }

        throw new IntersectionNotFoundException();
    }

    public void makeTwoWay(String from,
                            String to)
            throws IntersectionNotFoundException,
            RouteNotFoundException {
        int defaultSpeed = getConnection(from, to).getSpeed();
        connectIntersections(to, from, defaultSpeed);

        if (getConnection(from, to).hasSpeedSign()) {
            getConnection(to, from).addSpeedSign(defaultSpeed);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Network network = (Network) o;
        return Objects.equals(intersections, network.intersections) &&
                intersections.size() == network.intersections.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(intersections);
    }

    private int numRoutes() {
        int numRoutes = 0;
        for (Intersection intersectionFrom: intersections) {
            for (Intersection intersectionTo: intersections) {
                try {
                    getConnection(intersectionFrom.getId(), intersectionTo.getId());
                    numRoutes++;
                } catch (RouteNotFoundException | IntersectionNotFoundException r) {
                    //
                }

                try {
                    getConnection(intersectionTo.getId(), intersectionFrom.getId());
                    numRoutes++;
                } catch (RouteNotFoundException | IntersectionNotFoundException r) {
                    //
                }
            }
        }

        return numRoutes;
    }

    public String toString() {
        StringBuilder toString = new StringBuilder(
                getIntersections().size() + System.lineSeparator() +
                numRoutes() + System.lineSeparator() +
                getYellowTime() + System.lineSeparator());

        for (Intersection intersection: getIntersections()) {
            toString.append(intersection.toString()).append(System.lineSeparator());
        }

        for (Intersection intersectionFrom: intersections) {
            for (Intersection intersectionTo: intersections) {
                Route route = null;
                try {
                    route = getConnection(intersectionFrom.getId(), intersectionTo.getId());
                    toString.append(intersectionFrom.getId() + ":")
                            .append(intersectionTo.getId() + ":")
                            .append(route.getSpeed() + ":")
                            .append(route.getSensors().size()
                                        + (route.hasSpeedSign() ? ":" + route.getSpeed() : ""))

                            .append(System.lineSeparator());
                } catch (RouteNotFoundException | IntersectionNotFoundException e) {
                    // Squash
                }

                try {
                    for (Sensor sensor: route.getSensors()) {
                        toString.append(sensor.toString())
                                .append(System.lineSeparator());
                    }
                } catch (NullPointerException n) {
                    // Squash
                }
            }
        }
        return toString.toString();
    }

    public List<Intersection> getIntersections() {
        return new ArrayList<>(this.intersections);
    }
}
