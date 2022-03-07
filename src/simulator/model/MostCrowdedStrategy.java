package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int timeSlot;

	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.size() == 0) {
			return -1;
		} else if (currGreen == -1) {
			List<Vehicle> max = qs.get(0);
			for (List<Vehicle> x : qs) {
				if (x.size() > max.size())
					max = x;
			}
			return qs.indexOf(max);
		} else if (currTime - lastSwitchingTime < timeSlot) {
			return currGreen;
		}
		List<Vehicle> max = qs.get((currGreen + 1)%roads.size());


		for (int i = currGreen + 1; i < currGreen + 1 + qs.size(); i++) {
			if (qs.get(i % qs.size()).size() > max.size())
				max = qs.get(i % qs.size());
		}
		return qs.indexOf(max);
	}

}