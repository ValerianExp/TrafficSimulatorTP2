package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator simulator;
	private Factory<Event> events;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory)
	{
		if(sim == null) throw new IllegalArgumentException("La simulacion no puede ser nula");
		if(eventsFactory == null) throw new IllegalArgumentException("La factoria de eventos no puede ser nula");
		simulator = sim;
		events = eventsFactory;
	}

	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		if(!jo.has("events")) { //TODO Comprobar que jo length != 1?
			JSONArray eventsArray = jo.getJSONArray("events");
			
			for(int i = 0; i < eventsArray.length(); i++) {
				simulator.addEvent(events.createInstance(eventsArray.getJSONObject(i)));
			}
			
		}
	}
	
	public void run(int n, OutputStream out) {
		JSONArray j = new JSONArray();
		
		for (int i = 0; i < n; i++) {
			simulator.advance();
			j.put(simulator.report());
		}
		
		JSONObject states = new JSONObject();
		states.put("states", j);
		
		try(PrintStream printer = new PrintStream(out)){
			printer.println(states.toString(3));
		}
//TODO Esto hace falta?
	}
	
	public void reset() {
		simulator.reset();
	}
	
}
