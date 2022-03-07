package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String, Junction> junctionMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;
	
	RoadMap(){
		junctionList = new ArrayList<Junction>();
		roadList = new ArrayList<Road>();
		vehicleList = new ArrayList<Vehicle>();
		junctionMap = new HashMap<String,Junction>();
		roadMap = new HashMap<String,Road>();
		vehicleMap = new HashMap<String, Vehicle>();
	}
	
	void addJunction(Junction j) {
		if(junctionMap.containsKey(j.getId())) throw new IllegalArgumentException("Junction ya existente");
		junctionList.add(j);
		junctionMap.put(j.getId(), j);		
	}
	void addRoad(Road r) {
		if(roadMap.containsKey(r.getId())) throw new IllegalArgumentException("Road ya existente");
		if(!junctionMap.containsKey(r.getDest().getId()) || !junctionMap.containsKey(r.getSrc().getId()))
				throw new IllegalArgumentException("Road sin junctions existentes");
		roadList.add(r);
		roadMap.put(r.getId(), r);		
	}
	void addVehicle(Vehicle v) {
		if(vehicleMap.containsKey(v.getId())) throw new IllegalArgumentException("Road ya existente");
		List<Junction> lista = v.getItinerary();
		for (int i = 0; i < lista.size() - 1; i++) { 
			if(lista.get(i).roadTo(lista.get(i + 1)) == null) throw new IllegalArgumentException("La carretera " + i + " no pertenece al itinerario del vehiculo");
		}
		vehicleList.add(v);
		vehicleMap.put(v.getId(), v);	
	}
	
	public Junction getJunction(String id) {
		return junctionMap.get(id);
	}
	public Road getRoad(String id) {
		return roadMap.get(id);
	}
	public Vehicle getVehicle(String id) {
		return vehicleMap.get(id);		
	}
	
	public List<Junction>getJunctions(){
		return Collections.unmodifiableList(junctionList);
	}
	public List<Road>getRoads(){
		return Collections.unmodifiableList(roadList);
	}
	public List<Vehicle>getVehicles(){
		return Collections.unmodifiableList(vehicleList);
	}
	
	void reset() {
		junctionList.clear();
		roadList.clear();
		vehicleList.clear();
		junctionMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}
	
	public JSONObject report(){
		JSONObject j = new JSONObject();
		JSONArray junctionJSON = new JSONArray();
		JSONArray roadJSON = new JSONArray();
		JSONArray vehicleJSON = new JSONArray();
		
		for(Junction x: junctionList) {
			junctionJSON.put(x.report());
		}
		j.put("junctions", junctionJSON);
		
		
		for(Road x: roadList) {
			roadJSON.put(x.report());
		}
		j.put("road", roadJSON);
		
		for(Vehicle x: vehicleList) {
			vehicleJSON.put(x.report());
		}
		j.put("vehicles", vehicleJSON);
		
		return j;
	}
	
}
