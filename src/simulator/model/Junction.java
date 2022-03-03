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
		lastSwitchStep = 0;
		if (lsStrategy == null)
			throw new IllegalArgumentException("lsStrategy no puede ser null");
		if (dqStrategy == null)
			throw new IllegalArgumentException("dqStrategy no puede ser null");
		if (xCoor < 0 || yCoor < 0)
			throw new IllegalArgumentException("no puede haber coordenadas negativas");
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
		Junction j = r.getDest();
		outgoingRoads.put(j, r);
		if (!r.getSrcJunc().equals(this) || outgoingRoads.get(this).getDest().equals(j))
			throw new IllegalArgumentException("Error: no es una carretera saliente");
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
		JSONObject j = new JSONObject();
		JSONArray a = new JSONArray();
		j.put("id", _id);
		if (greenLightIndex == -1) {
			j.put("green", "none");
		} else {
			j.put("green", incomingRoads.get(greenLightIndex)._id);
		}

		//TODO completar JSON del Junction
		for (Road r : incomingRoads) {
			JSONObject jaux = new JSONObject();
			JSONArray arraux = new JSONArray();
			jaux.put("road", r.getId());
			for (Vehicle v : queueMap.get(r)) {
				arraux.put(v.getId());
			}
			jaux.put("vehicles",jaux);
			a.put(jaux);
			
		}
		j.put("queues", a);
		return j;
	}

	public void enter(Vehicle v) {
		Road r = v.getRoad();
		int i = incomingRoads.indexOf(r);
		queueList.get(i).add(v);
	}

	Road roadTo(Junction j) {
		Road r = outgoingRoads.get(this);
		if (r.getDest().equals(j))
			return r;
		else
			return null;
	}

}