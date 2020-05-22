package tms.congestion;

import tms.sensors.Sensor;
import java.util.List;

public class AveragingCongestionCalculator implements CongestionCalculator {
    private List<Sensor> sensors;

    public AveragingCongestionCalculator(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public int calculateCongestion() {
        if (sensors.isEmpty()) {
            return 0;
        }

        int congestionSum = 0;
        for (Sensor sensor : sensors) {
            congestionSum += sensor.getCongestion();
        }
        float averageCongestion = (float) congestionSum / sensors.size();
        return Math.round(averageCongestion);
    }
}
