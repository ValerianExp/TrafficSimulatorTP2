package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {
	private SetWeatherEvent s;
	
	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		int time = data.getInt("time");
		
		List<Pair<String,Weather>> pairList = new ArrayList<>();
		
		JSONArray array = data.getJSONArray("info"); 
		for(int i = 0; i<array.length(); i ++) {
			String id = array.getJSONObject(i).getString("road");
			Weather weather = Weather.valueOf(array.getJSONObject(i).getString("weather"));
			pairList.add(new Pair<String, Weather>(id,weather));
		}
			
		s = new SetWeatherEvent(time, pairList);
		
		return s;
	}

}
