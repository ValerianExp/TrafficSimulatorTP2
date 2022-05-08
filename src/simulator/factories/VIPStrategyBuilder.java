package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.VIPStrategy;

public class VIPStrategyBuilder extends Builder<DequeuingStrategy>{
	//TODO examen
	//El constructor no recibe el string
	public VIPStrategyBuilder() {
		super("vip_dqs"); //Leer de JSON coincide con la constante type:"vip_dqs"
	}

	@Override
	//Mira antes de entrar mira que data != null
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		String tag;
		int limit = 0;
		if (data.has("viptag")) {
			tag = data.getString("viptag");
		}
		else {
			throw new IllegalArgumentException("A tag is needed");
		}
		if (data.has("limit")) {
			limit = data.getInt("limit");
		}
		return new VIPStrategy(tag, limit);
	}

}
