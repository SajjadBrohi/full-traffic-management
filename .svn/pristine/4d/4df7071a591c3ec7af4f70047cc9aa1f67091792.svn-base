package tms.intersection;

import tms.route.Route;
import tms.util.TimedItem;
import tms.util.TimedItemManager;

import java.util.List;

public class IntersectionLights implements TimedItem {
    private List<Route> connections;
    private int yellowTime;
    private int duration;

    public IntersectionLights(List<Route> connections,
                               int yellowTime,
                               int duration) {
        this.connections = connections;
        this.yellowTime = yellowTime;
        this.duration = duration;

        TimedItemManager.getTimedItemManager().registerTimedItem(this);
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
