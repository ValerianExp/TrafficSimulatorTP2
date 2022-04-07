package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private List<Junction> lj;

	private String[] columns = {"Id","Green", "Queues"};

	JunctionsTableModel(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return lj.size();
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
		//switch(columnIndex())
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		lj=map.getJunctions();
		fireTableDataChanged();

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		lj=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		lj=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		lj=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		lj=map.getJunctions();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
