package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	private MostCrowdedStrategy m = new MostCrowdedStrategy(1);
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data != null && data.has("timeslot")) {
			m = new MostCrowdedStrategy(data.getInt("timeslot"));
		}
		
		return m;
	}

}
