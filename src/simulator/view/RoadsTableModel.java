package simulator.view;

import java.util.ArrayList;
import java.util.List;


import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private Controller _ctrl;
	private List<Road> rl;
	private String[] columns = {"Id", "Length", "Weather", "Max. Speed", "SpeedLimit", "Total CO2", "CO2 Limit"};
	
	RoadsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		rl = new ArrayList<Road>();
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return rl.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Road road = rl.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return road.getId();
		case 1:
			return road.getLength();
		case 2:
			return road.getWeather();
		case 3:
			return road.getMaxSpeed();
		case 4:
			return road.getSpeedLimit();
		case 5:
			return road.getTotalCO2();
		case 6:
			return road.getContLimit();
		}
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		rl = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		rl = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		rl = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		rl = map.getRoads();
		this.fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		rl = map.getRoads();
		this.fireTableDataChanged();		
	}

	@Override
	public void onError(String err) {
		// TODO onError
		
	}
}
