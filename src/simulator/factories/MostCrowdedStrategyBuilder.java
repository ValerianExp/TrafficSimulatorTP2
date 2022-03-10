package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{

	private MostCrowdedStrategy m;
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		if(data != null) {
			if(data.has("timeslot")) {
				m = new MostCrowdedStrategy(data.getInt("timeslot"));
			}else {
				m = new MostCrowdedStrategy((int)1);
			}
		} else {
			m = new MostCrowdedStrategy((int)1);
		}
		
		return m;
	}

}
