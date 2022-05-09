package simulator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.platform.commons.util.StringUtils;

import java.util.TreeMap;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class TicksExamen implements TrafficSimObserver {

	String idCoche;
	String idCarretera;
	private Map<String, Road> roadMap;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Controller controller;
	// @formatter:off
	// Primer valor, el numero de tick
	// Segundo valor, -el primer valor es el id del road
	// 				  -el segundo valor es el contador de vehiculos
	// @formatter:on
	private HashMap<Integer, TreeMap<String, Integer>> resultado;

	public TicksExamen(Controller controller) {
		roadMap = new HashMap<String, Road>();
		roadList = new ArrayList<Road>();
		vehicleList = new ArrayList<Vehicle>();
		resultado = new HashMap<Integer, TreeMap<String, Integer>>();

		this.controller = controller;
		controller.addObserver(this);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		roadList = map.getRoads();
		TreeMap<String, Integer> val = new TreeMap<String, Integer>();
		for (Road r : roadList) {
			if (r.getVehicles().size() != 0) {
				val.put(r.getId(), r.getVehicles().size());
				resultado.put(time, val);
			}
		}
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
	
	@Override
	public String toString() {
		//return resultado.toString() 
		//tambien muestra la informacion
		// {1={r1=1, r2=1}, 2={r1=1, r2=1}, 3={r1=1, r2=1}}
		
		//solo que esta tiene el formato que pone en el examen
		String salida = "Done!" + '\n';
	    for (Entry<Integer, TreeMap<String, Integer>> entry : resultado.entrySet()) {
	        //fatherKey es el tick
	    	TreeMap<String, Integer> childMap = entry.getValue();
	        Integer Fatherkey = entry.getKey();
	        salida += Fatherkey.toString() + ":";
	        salida += "[";
	        for (Entry<String, Integer> entry2 : childMap.entrySet()) {
	        	//childKey es el id de la carretera
	        	//childValue es el contador de vehiculos de la carretera
	            String childKey = entry2.getKey();
	            Integer childValue = entry2.getValue();
	            salida += "(" + childKey + "," + childValue + ")";
				salida += ", ";
				
	        }
	        //Borrar el ultimo ", " de cada iteracion del tick
	        //---
	        int pos = salida.lastIndexOf(", ");
	        salida = salida.substring(0, pos);
	        //---
	        salida += "]";
	        salida += '\n';
	    }
	   	   
	    return salida;
	    
	}

}
