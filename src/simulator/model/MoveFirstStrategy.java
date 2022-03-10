package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> firstList = new ArrayList<>();
		if (q.size() !=0) {
			Vehicle first = q.get(0);
			firstList.add(first);}
		
		return firstList;
	}

}
