package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

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
			if(junction.getGreenLightIndex()==-1) return "NONE";	
			int indice = jl.get(rowIndex).getGreenLightIndex();
			return jl.get(rowIndex).getInRoads().get(indice);
		case 2: 
			String queue = "";
			for (Road r : jl.get(rowIndex).getInRoads())
			{
				queue += r.getId() + ":[";
				for(Vehicle v: r.getVehicles()) {
					if (v.getStatus() == VehicleStatus.WAITING) queue += v.getId();
				}
				queue += "] ";
			}
			return queue;
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
