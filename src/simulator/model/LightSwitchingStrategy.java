package simulator.model;

import java.util.List;

public interface LightSwitchingStrategy {
    int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qa, int currGreen, int lastSwitchingTime, int currTime);
}