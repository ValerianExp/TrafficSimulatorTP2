package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesSpeedExamen extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private List<Vehicle> vl;
	private String[] columns = { "Id vehicles", "Medium speed" };
	private int tick;
	private List<Integer> vMedia;
	private List<Integer> vAnterior;
	int velocidadAnterior;
	
	public VehiclesSpeedExamen(Controller ctrl) {
		_ctrl = ctrl;
		vl = new ArrayList<Vehicle>();
		vMedia = new ArrayList<Integer>();
		vAnterior = new ArrayList<Integer>();
		velocidadAnterior = 0;
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
	
		return vl.size();
	}

	@Override
	public int getColumnCount() {
		
		return columns.length;
		//return 2;
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Vehicle vehicle = vl.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return vehicle.getId();
		case 1:
			vAnterior.add(rowIndex, vehicle.getSpeed());
			System.out.println("Hello");
			vMedia.add(rowIndex, vAnterior.get(rowIndex) + vehicle.getSpeed());
			//velocidadAnterior = vehicle.getSpeed();
			
			return vMedia.get(rowIndex);
		}
		
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		tick = time;
		vl = map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		tick = time;
		vl = map.getVehicles();
		
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		tick = time;
		vl = map.getVehicles();
		
		this.fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.tick = time;
		vl = map.getVehicles();
		
		this.fireTableDataChanged();

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.tick = time;
		vl = map.getVehicles();
		
		this.fireTableDataChanged();

	}

	@Override
	public void onError(String err) {

	}

}
