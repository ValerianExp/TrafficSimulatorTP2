package simulator.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String, Junction> junctionMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;
	
	RoadMap(){
		//TODO Initialize the maps to their default values (?)
	}
	
	void addJunction(Junction j) {
		if(junctionMap.containsKey(j.getId())) throw new IllegalArgumentException("Junction ya existente");
		junctionList.add(j);
		junctionMap.put(j.getId(), j);		
	}
	void addRoad(Road r) {
		if(roadMap.containsKey(r.getId())) throw new IllegalArgumentException("Road ya existente");
		if(!junctionMap.containsKey(r.getDestJunc().getId()) || !junctionMap.containsKey(r.getSrcJunc().getId()))
				throw new IllegalArgumentException("Road sin junctions existentes");
		roadList.add(r);
		roadMap.put(r.getId(), r);		
	}
	void addVehicle(Vehicle v) {
		if(vehicleMap.containsKey(v.getId())) throw new IllegalArgumentException("Road ya existente");
		vehicleList.add(v);
		vehicleMap.put(v.getId(), v);
		List<Junction> lista = v.getItinerary();
		for(Junction vs: lista) {
			//TODO Check every Junction in a vehicle itinerary is connected
		}
		
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
		//TODO Create JSON from RoadMap
		return j;
	}
	
}
