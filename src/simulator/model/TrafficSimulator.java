package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class TrafficSimulator {
	private RoadMap roadMap;
	private List<Event> eventList;
	private int time;
	
	public TrafficSimulator(){
		roadMap = new RoadMap();
		eventList = new ArrayList<Event>();
		time = 0;
	}
	
	public void addEvent(Event e) {
		eventList.add(e);
	}
	
	public void advance(){
		time++;
		
		for(Event e: eventList) {
			if(e.getTime() == time) {
				e.execute(roadMap);
				eventList.remove(e);
			}
		}
		
		for(Junction j: roadMap.getJunctions()) {
			j.advance(time);
		}
		
		for(Road r: roadMap.getRoads()) {
			r.advance(time);
		}
	}
	
	public void reset() {
		roadMap.reset();
		eventList.clear();
		time = 0;
	}
	
	public JSONObject report(){
		JSONObject j = new JSONObject();
		
		j.put("time", time);
		j.put("state", roadMap.report());
		
		return j;
	}
	
}
