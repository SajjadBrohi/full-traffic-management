package tms.sensors;

public class DemoVehicleCount
        extends DemoSensor
        implements VehicleCount {

    public DemoVehicleCount(int[] data,
                             int threshold) {
        super(data, threshold);
    }

    @Override
    public int countTraffic() {
        return this.getCurrentValue();
    }

    @Override
    public int getCongestion() {
        float congestion = (float) this.countTraffic() / this.getThreshold();
        int congestionPct = Math.round(100 - 100 * congestion);
        return Math.min(Math.max(congestionPct, 0), 100);
    }

    @Override
    public String toString() {
        return "VC" + ":" + super.toString();
    }
}