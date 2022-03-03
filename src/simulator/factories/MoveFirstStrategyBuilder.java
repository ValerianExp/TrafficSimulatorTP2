package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy>{
	private MoveFirstStrategy m;
	MoveFirstStrategyBuilder() {
		super("move_first_dqs");
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		m = new MoveFirstStrategy();
		return m;
	}

}
