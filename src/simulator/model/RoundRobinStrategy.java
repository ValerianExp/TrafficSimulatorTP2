package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qa, int currGreen, int lastSwitchingTime,
            int currTime) {
        // TODO completar
    	if (roads.size() == 0) {
			return -1;
		}else if (currGreen == -1) {
			return 0;
		}else if (currTime - lastSwitchingTime < timeSlot) {
			
		}
        return 0;
    }

}
