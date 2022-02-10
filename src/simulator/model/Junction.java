package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	List<Road> incomingRoads;
	Map<Junction, Road> outgoingRoads;
	//TODO completar
	Junction(String id) {
		
		super(id);
	}

	@Override
	void advance(int time) {
	}

	@Override
	public JSONObject report() {
		return null;
	}

	public void enter(Vehicle vehicle) {
	}
	
	Road roadTo(Junction j) {
		Road r;
		return r;
	}

}