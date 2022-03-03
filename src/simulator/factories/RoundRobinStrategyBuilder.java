package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

	private RoundRobinStrategy r;
	
	RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data != null) {
			if(data.has("timeslot")) {
				r = new RoundRobinStrategy(data.getInt("timeslot"));
			}else {
				r = new RoundRobinStrategy(1);
			}
		}
		
		return r;
	}

}
