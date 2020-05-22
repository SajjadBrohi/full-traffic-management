package tms.network;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tms.util.IntersectionNotFoundException;
import tms.util.InvalidNetworkException;

import java.io.IOException;

public class NetworkTest {
    private Network network;
    private NetworkInitialiser networkInitialiser;
    @Before
    public void setup() throws IOException, InvalidNetworkException {
        networkInitialiser = new NetworkInitialiser();
        network = networkInitialiser.loadNetwork("networks/demo.txt");
    }

    @Test
    public void addIntersection() throws IntersectionNotFoundException {
        network.createIntersection("A");
        Assert.assertEquals("W", network.findIntersection("W").getId());
    }
}
