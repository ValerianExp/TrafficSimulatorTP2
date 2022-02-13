package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int timeSlot;
	
	
	MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}
	
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if (roads.size() == 0) {
			return -1;
		}else if (currGreen == -1) {
			List<Vehicle> max = null;
			for (List<Vehicle> x: qs) {
				if(x.size() > max.size()) max = x;
			}
			return qs.indexOf(max);
		}else if (currTime - lastSwitchingTime < timeSlot) {
			return currGreen;
		}
		//TODO Esto es una busqueda circular ??
		List<Vehicle> max = qs.get(currGreen+1);
		for (int i = currGreen +1; i < qs.size(); i++) {
			if(qs.get(i).size() > max.size()) max = qs.get(i);
		}
		for (int i =0; i< currGreen +1;i++) {
			if(qs.get(i).size() > max.size()) max = qs.get(i);
		}
        return qs.indexOf(max);
	}

}
