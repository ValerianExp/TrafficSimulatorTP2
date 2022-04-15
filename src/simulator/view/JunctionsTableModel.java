package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private List<Junction> jl;

	private String[] columns = {"Id","Green", "Queues"};

	JunctionsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		jl = new ArrayList<Junction>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return jl.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Junction junction = jl.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return junction.getId();
		case 1:
			if(junction.getGreenLightIndex()==-1) return "none";	
			return junction.getGreenLightIndex();
		case 2: 
			return junction.getInRoads();
		}
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		jl=map.getJunctions();
		fireTableDataChanged();

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		jl=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		jl=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		jl=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		jl=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO onError
	}

}
