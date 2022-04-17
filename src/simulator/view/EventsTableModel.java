package simulator.view;

import java.util.ArrayList;
import java.util.List;


import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver{
	private Controller _ctrl;
	private List<Event> el;
	private String[] columns = {"Time", "Desc."};
	EventsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		el = new ArrayList<Event>();
		_ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return el.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Event event = el.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return event.getTime();
		case 1:
			return event.toString();
		}
		return "";
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		el = events;
		this.fireTableDataChanged();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		el = events;
		this.fireTableDataChanged();		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		el = events;
		this.fireTableDataChanged();		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		el = events;
		this.fireTableDataChanged();		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		el = new ArrayList<Event>();
		this.fireTableDataChanged();		
	}

	@Override
	public void onError(String err) {
		// TODO onError
		
	}
}
