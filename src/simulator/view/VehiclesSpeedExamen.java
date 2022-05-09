package simulator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

//TODO examen
public class VehiclesSpeedExamen extends AbstractTableModel implements TrafficSimObserver {
	private Controller _ctrl;
	private List<Vehicle> vl;
	private String[] columns = { "Id vehicles", "Medium speed" };
	private int tick;
	//id del vehiculo, (numero tick, valor de la de velocidad en ese momento)
	private Map<String, Map<Integer, Integer>> velocidades2;

	public VehiclesSpeedExamen(Controller ctrl) {
		_ctrl = ctrl;
		vl = new ArrayList<Vehicle>();
		
		velocidades2 = new HashMap<String, Map<Integer, Integer>>();
		_ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {

		return vl.size();
	}

	@Override
	public int getColumnCount() {

		return columns.length;
		// return 2;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vehicle vehicle = vl.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return vehicle.getId();
		case 1:
			//new HashMap<String, Map<Integer, Integer>>();
			//Map<Integer, Integer> t = new TreeMap<Integer, Integer>();
			
			if (velocidades2.get(vehicle.getId()) == null ) {
				velocidades2.put(vehicle.getId(), new TreeMap<Integer, Integer>());
				velocidades2.get(vehicle.getId()).put(tick, vehicle.getSpeed());
				
			} else {
				//Integer velocidadAnterior = velocidades2.get(vehicle.getId()).get(tick);
				velocidades2.get(vehicle.getId()).put(tick, vehicle.getSpeed());
			}
			
			//El sumatorio con la media
			//---
			double resultado = 0;
			for (Entry<Integer, Integer> i: velocidades2.get(vehicle.getId()).entrySet()) {
				if (vehicle.getSpeed() != 0) {
					resultado += i.getValue();
				}
			}
			resultado /= tick;
			//---
			
			return resultado;
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
