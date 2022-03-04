package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;


public class SetContClassEventBuilder extends Builder<Event> {
	private NewSetContClassEvent s;
	
	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		int time = data.getInt("time");
		
		List<Pair<String,Integer>> pairList = new ArrayList<>();
		
		JSONArray array = data.getJSONArray("info"); 
		for(int i = 0; i<array.length(); i ++) {
			String id = array.getJSONObject(i).getString("road");
			int contamination = array.getJSONObject(i).getInt("class");
			pairList.add(new Pair<String, Integer>(id,contamination));
		}
			
		s = new NewSetContClassEvent(time, pairList);
		
		return s;
	}

}
