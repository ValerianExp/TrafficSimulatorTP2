package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	private NewVehicleEvent n;
	
	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxspeed = data.getInt("maxspeed");		
		int contclass = data.getInt("class");
		
		List<String> itinerary = new ArrayList<>();
		
		JSONArray itineraryJSON = data.getJSONArray("itinerary");
		for(Object o: itineraryJSON) {
			itinerary.add((String) o);
		}
				
		n = new NewVehicleEvent(time, id, maxspeed, contclass, itinerary);
		
		return n;
	}

}
