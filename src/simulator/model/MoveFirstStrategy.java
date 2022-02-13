package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		Vehicle first = q.get(0);
		List<Vehicle> firstList = new ArrayList<>();
		firstList.add(first);
		return firstList;
	}

}
