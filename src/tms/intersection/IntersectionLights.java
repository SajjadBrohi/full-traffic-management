package tms.intersection;

import tms.route.Route;
import tms.util.TimedItem;

import java.util.List;

public class IntersectionLights implements TimedItem {
    public IntersectionLights(List<Route> connections,
                               int yellowTime,
                               int duration) {
        //stub
    }

    public int getYellowTime() {
        return 0;
    }

    public void setDuration(int duration) {
        //stub
    }

    public void oneSecond() {
        //stub
    }

    public String toString() {
        return null;
    }

}
