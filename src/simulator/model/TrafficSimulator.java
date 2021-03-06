package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	private RoadMap roadMap;
	private List<TrafficSimObserver> observables;
	private List<Event> eventList;
	private int time;


	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>();
		time = 0;
		observables = new ArrayList<TrafficSimObserver>();
	}

	public void addEvent(Event e) {
		eventList.add(e);
		for (TrafficSimObserver t : observables) {
			t.onEventAdded(roadMap, eventList, e, time);
		}
	}

	public void advance() {

		try {
			time++;

			for (TrafficSimObserver t : observables) {
				t.onAdvanceStart(roadMap, eventList, time);
			}

			while (eventList.size() > 0 && eventList.get(0).getTime() == time) {
				Event e = eventList.get(0);
				eventList.remove(e);
				e.execute(roadMap);
			}

			for (Junction j : roadMap.getJunctions()) {
				j.advance(time);
			}

			for (Road r : roadMap.getRoads()) {
				r.advance(time);
			}

		} catch (Exception e) {
			for (TrafficSimObserver t : observables) {
				t.onError(e.getMessage());
			}
		}
		for (TrafficSimObserver t : observables) {
			t.onAdvanceEnd(roadMap, eventList, time);
		}
	}

	public void reset() {
		roadMap.reset();
		eventList.clear();
		time = 0;

		for (TrafficSimObserver t : observables) {
			t.onReset(roadMap, eventList, time);
		}
	}

	public JSONObject report() {
		JSONObject j = new JSONObject();

		j.put("time", time);
		j.put("state", roadMap.report());

		return j;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		if(!observables.contains(o)) observables.add(o);
		for (TrafficSimObserver t : observables) {
			t.onRegister(roadMap, eventList, time);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		if(observables.contains(o))	observables.remove(o);
	}

}
