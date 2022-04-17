package simulator.view;

import java.util.ArrayList;
import java.util.List;


import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{
	private Controller _ctrl;
	private List<Vehicle> vl;
	private String[] columns = {"Id", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
	VehiclesTableModel(Controller ctrl) {
		_ctrl = ctrl;
		vl = new ArrayList<Vehicle>();
		_ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return vl.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
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
			switch(vehicle.getStatus()) {
			case ARRIVED:
				return "Arrived";
			case TRAVELING:
				String s= vehicle.getRoad().getId() + ":" +  vehicle.getLocation();
				return s;
			case PENDING:
				return "Pending";
			case WAITING:
				return "Waiting:" + vehicle.getItinerary().get(vehicle.getlastJunctionIndex());
			}
		case 2:
			return vehicle.getItinerary();
		case 3:
			return vehicle.getContClass();
		case 4:
			return vehicle.getMaxSpeed();
		case 5: 
			return vehicle.getSpeed();
		case 6: 
			return vehicle.getTotalCO2();
		case 7:
			return vehicle.getDistance();
		}
		
		return null;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		vl = map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		vl = map.getVehicles();
		this.fireTableDataChanged();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		vl = map.getVehicles();
		this.fireTableDataChanged();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		vl = map.getVehicles();
		this.fireTableDataChanged();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		vl = map.getVehicles();
		this.fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO onError
		
	}
}
