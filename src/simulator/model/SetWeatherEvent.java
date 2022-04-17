package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private int time;
	private List<Pair<String, Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws == null) throw new IllegalArgumentException("La lista ws no puede ser nula");
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		for(Pair<String, Weather> p: ws) {
			Road r = map.getRoad(p.getFirst());
			if (r == null) throw new IllegalArgumentException("No existe ese road en el par");
			r.setWeather(p.getSecond());
		}
		
	}
	
	@Override
	public String toString() {
		String s = "Change Weather [";
		
		for(int i = 0; i< ws.size(); i++) {
			s += "(" + ws.get(i).getFirst() + ", " + ws.get(i).getSecond() + ")";
			if (i!=ws.size()-1) s+= ", ";
		}
		
		s+="]";
		
		return s;
	}
}
