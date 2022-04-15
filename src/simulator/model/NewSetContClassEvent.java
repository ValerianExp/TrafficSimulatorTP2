package simulator.model;
import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {
	private List<Pair<String, Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		if(cs == null) throw new IllegalArgumentException("La lista cs no puede ser nula");
		this.cs = cs;
	}


	@Override
	void execute(RoadMap map) {
		for(Pair<String, Integer> p: cs) {
			Vehicle v = map.getVehicle(p.getFirst());
			if (v == null) throw new IllegalArgumentException("No existe ese vehicle en el par");
			v.setContClass(p.getSecond());
		}
		
	}
	
	@Override
	public String toString() {
		return "Event added (Change CO2 Class [(" + cs.get(0).getFirst() + ", " + cs.get(0).getSecond() + ")])";
	}
	
}
