package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy>{
	private MoveAllStrategy m;
	MoveAllStrategyBuilder() {
		super("move_all_dqs");
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		m = new MoveAllStrategy();
		return m;
	}

}