package simulator.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	private List<Road> incomingRoads;
	private Map<Junction, Road> outgoingRoads;
	private List<List<Vehicle>> queueList;
	//TODO Se recomienda guardar un Map<Road,List<Vehicles> para hacer la búsqueda
	private int greenLightIndex;
	private int lastSwitchStep;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	//TODO Completar atributos del Junction
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		lastSwitchStep = 0;
		if (lsStrategy == null) throw new IllegalArgumentException("lsStrategy no puede ser null");
		if (dqStrategy == null) throw new IllegalArgumentException("dqStrategy no puede ser null");
		if(xCoor < 0 || yCoor < 0) throw new IllegalArgumentException("no puede haber coordenadas negativas");
	}

	void addIncomingRoad(Road r) {
		if (!r.getDestJunc().equals(this)) throw new IllegalArgumentException("Error: no es una carretera entrante");
		incomingRoads.add(r);
		LinkedList list;
		//TODO Pregunta sobre addIncomingRoad
	}
	
	void addOutGoingRoad(Road r) {
		if (r.getSrcJunc().equals(this)) throw new IllegalArgumentException("Error: no es una carretera saliente");
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
		Road r = null;
		return r;
	}

}