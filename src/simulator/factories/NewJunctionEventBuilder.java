package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
	private NewJunctionEvent n;
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int coorX = data.getJSONArray("coor").getInt(0);		
		int coorY = data.getJSONArray("coor").getInt(1);
		
		LightSwitchingStrategy lsStrategy = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqStrategy = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		n = new NewJunctionEvent(time, id, lsStrategy, dqStrategy, coorX, coorY);
		
		return n;
	}

}
