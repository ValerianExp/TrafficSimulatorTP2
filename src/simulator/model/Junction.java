package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> incomingRoads;
	private Map<Junction, Road> outgoingRoads;
	private List<List<Vehicle>> queueList;
	private Map<Road, List<Vehicle>> queueMap;
	private int greenLightIndex;
	private int lastSwitchStep;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor,
			int yCoor) {
		super(id);

		if (lsStrategy == null)
			throw new IllegalArgumentException("lsStrategy no puede ser null");
		if (dqStrategy == null)
			throw new IllegalArgumentException("dqStrategy no puede ser null");
		if (xCoor < 0 || yCoor < 0)
			throw new IllegalArgumentException("no puede haber coordenadas negativas");
		
		lastSwitchStep = 0;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		incomingRoads = new ArrayList<Road>();
		outgoingRoads = new HashMap<Junction, Road>();
		queueList = new ArrayList<>();
		queueMap = new HashMap<Road, List<Vehicle>>();
		greenLightIndex = -1;
	}

	void addIncomingRoad(Road r) {
		if (!r.getDestJunc().equals(this))
			throw new IllegalArgumentException("Error: no es una carretera entrante");
		incomingRoads.add(r);
		LinkedList<Vehicle> list = new LinkedList<Vehicle>();
		queueList.add(list);
		queueMap.put(r, list);
	}

	void addOutGoingRoad(Road r) {
		//Junction j = r.getDestJunc();
		outgoingRoads.put(this, r);
		
		//TODO cambiar la exception
		/*
		if (!r.getSrcJunc().equals(this) || outgoingRoads.get(this).getDestJunc().equals(j))
			throw new IllegalArgumentException("Error: no es una carretera saliente");
		*/
	}

	@Override
	void advance(int time) {
		List<Vehicle> vList = dqStrategy.dequeue(queueList.get(greenLightIndex));

		Vehicle v;
		for (int i = 0; i < vList.size(); i++) {
			v = vList.get(i);
			v.moveToNextRoad();
		}

		queueList.get(greenLightIndex).removeAll(vList);

		//  Si es distinto del índice
		// actual, entonces cambia el valor del índice al nuevo valor y pone el último paso de cambio de semáforo al paso actual (es decir, el valor del parámetro time).
		int aux = lsStrategy.chooseNextGreen(incomingRoads, queueList, greenLightIndex, lastSwitchStep, time);
		if (aux != greenLightIndex) {
			greenLightIndex = aux;
			lastSwitchStep = time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject junction = new JSONObject();
		JSONArray arrayQueues = new JSONArray();
		
		junction.put("id", _id);
		if (greenLightIndex == -1) junction.put("green", "none");
		else junction.put("green", incomingRoads.get(greenLightIndex)._id);
		
		
		for (Road r : incomingRoads) {
			JSONObject jaux = new JSONObject();
			JSONArray vehicles = new JSONArray();
			jaux.put("road", r.getId());
			for (Vehicle v : queueMap.get(r)) {
				vehicles.put(v.getId());
			}
			jaux.put("vehicles", vehicles);
			arrayQueues.put(jaux);
			
		}
		junction.put("queues", arrayQueues);
		return junction;
	}

	public void enter(Vehicle v) {
		Road r = v.getRoad();
		int i = incomingRoads.indexOf(r);
		queueList.get(i).add(v);
	}

	Road roadTo(Junction j) {
		Road r = outgoingRoads.get(j);
		//if (r.getDestJunc().equals(j))
			return r;
		//else
			//return null;
	}

}