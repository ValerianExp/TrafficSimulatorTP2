package simulator.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	private List<Road> incomingRoads;
	private Map<Junction, Road> outgoingRoads;
	private List<List<Vehicle>> queueList;
	private Map<Road,List<Vehicle>> queueMap;
	private int greenLightIndex;
	private int lastSwitchStep;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		lastSwitchStep = 0;
		if (lsStrategy == null) throw new IllegalArgumentException("lsStrategy no puede ser null");
		if (dqStrategy == null) throw new IllegalArgumentException("dqStrategy no puede ser null");
		if(xCoor < 0 || yCoor < 0) throw new IllegalArgumentException("no puede haber coordenadas negativas");
	}

	void addIncomingRoad(Road r) {
		if (!r.getDestJunc().equals(this))
			throw new IllegalArgumentException("Error: no es una carretera entrante");
		incomingRoads.add(r);
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		queueList.add(list);
		queueMap.put(r,list);
	}
	
	void addOutGoingRoad(Road r) {
		Junction j = r.getDest();
		outgoingRoads.put(j, r);
		if (!r.getSrcJunc().equals(this) || outgoingRoads.get(this).getDest().equals(j)) throw new IllegalArgumentException("Error: no es una carretera saliente");
	}
	
	@Override
	void advance(int time) {
		for(List<Vehicle> x: queueList) {
			dqStrategy.dequeue(x);
			
		}
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		JSONArray queueArray = new JSONArray();
		JSONArray vehicleArray = new JSONArray();

		
		j.put("id", _id);
		// j.put("green", ??);
		if (greenLightIndex == -1) {
			j.put("green", incomingRoads.get(greenLightIndex).getId());
		}
		else {
			//TODO modificar el green del JSON
			j.put("green" , 0 );
		}
		for (List<Vehicle> q : queueList) {
			//TODO implementar mapa de carreteras y vehiculos
			
			for (Vehicle v : q) {
				vehicleArray.put(v);
			}
			j.put("vehicles", vehicleArray);
		}
		j.put("queues", queueArray);
		return null;
	}

	public void enter(Vehicle v) {
		Road r = v.getRoad();
		int i = incomingRoads.indexOf(r);
		queueList.get(i).add(v);
	}
	
	Road roadTo(Junction j) {
		Road r = outgoingRoads.get(this);
		if ( r.getDest().equals(j)) return r;
		else return null;
	}

}