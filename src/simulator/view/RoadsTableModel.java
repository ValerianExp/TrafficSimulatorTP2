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
		_ctrl.addObserver(this);
		rl = new ArrayList<>();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

	

}
