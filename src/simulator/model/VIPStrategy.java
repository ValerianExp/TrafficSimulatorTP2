package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class VIPStrategy implements DequeuingStrategy{
	
	private int limit;
	private String suffix;
	public VIPStrategy(String suffix, int limit) {
		// TODO Examen
		this.suffix = suffix;
		this.limit = limit;
	}
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Examen
		List<Vehicle> solucion = new ArrayList<>();
		//Los coches se eliminaban en el advance
		int cont = 0;
		

		for(Vehicle v: q) {
			if (v.getId().endsWith(suffix) && cont < limit) {
				solucion.add(v);
				cont++;
			}
		}
		
		if (cont < limit) {
			for(Vehicle v: q) {
				if (!v.getId().endsWith(suffix) && cont < limit) {
					solucion.add(v);
					cont++;
				}
			}
		}
		
		return solucion;
	}
	
	//Version 2-Esta incompleta/mal
	public List<Vehicle> dequeue_2(List<Vehicle> q) {
		// TODO Examen
		List<Vehicle> solucion = new ArrayList<>();
		List<Vehicle> aux = new ArrayList<>(q);
		//Los coches se eliminaban en el advance
		int cont = 0;
		
		for(Vehicle v: q) {
			if (v.getId().endsWith(suffix) && cont < limit) {
				solucion.add(v);
				cont++;
			}
		}
		aux.removeAll(solucion);
		if (cont < limit) {
			for(Vehicle v: q) {
				if (!v.getId().endsWith(suffix) && cont < limit) {
					solucion.add(v);
					cont++;
				}
			}
		}
		
		return solucion;
	}
}
