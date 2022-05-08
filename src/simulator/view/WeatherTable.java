package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class WeatherTable extends AbstractTableModel implements TrafficSimObserver{

	String[] _name = {"Ticks", "Roads"};
	List<Road> miLista;
	@Override
	public int getRowCount() {
		
		return 0;
	}

	@Override
	public int getColumnCount() {
		
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
	
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	
		
	}

	@Override
	public void onError(String err) {
		
	}

}
