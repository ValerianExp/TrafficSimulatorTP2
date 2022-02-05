package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int speed;
	private int distance;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contaminationClass;
	private int totalCO2;
	private int totalDistance;
	
	
	public Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		// TODO Auto-generated constructor stub
		super(id);
		if (maxSpeed < 0 || (contClass < 0 || contClass > 10) || itinerary.size() < 2) {
			throw new IllegalArgumentException("Error en los parametros del constructor");
		}
		this.itinerary = Collections.unmodifiableList(new ArrayList<> (itinerary));
	}
	
	
	
	@Override
	void advance(int time) {
		//TODO comprobar que este bie
		if (status == VehicleStatus.TRAVELING) {
			int oldLocation = location;
			location = Math.min(location + speed, road.getLength());
			int c = contaminationClass * (location - oldLocation);
			totalCO2 += c;
			road.addContamination(c);
			if(location >= road.getLength()) {
				Junction j;
				j = road.getDest();
				j.enter(this);
				status = VehicleStatus.WAITING;
			}
		}
		else {
			if(speed != 0) { //TODO Comprobar esto
				throw new IllegalArgumentException("Error en la velocidad");
			}
		}
		
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", _id);
		j.put("speed", speed);
		j.put("distance", distance);
		j.put("co2", totalCO2);
		j.put("class", contaminationClass);
		j.put("status", status);
		if (!(status == VehicleStatus.PENDING || status == VehicleStatus.ARRIVED)) {
			j.put("road", road);
			j.put("location", location);			
		}
		
		return j;
	}

	 void moveToNextRoad() {
//		 this.getRoad().exit(this);
		 
		 if(status == VehicleStatus.PENDING) {
			 
		 }
		 else if(status == VehicleStatus.WAITING) {
			 if(itinerary.size() == 1) {
				 
			 }
			 else {
				 
			 }
		 }
		 else {
			 throw new IllegalArgumentException("Estado del coche erroneo");
		 }
		 
	 }
	
	public List<Junction> getItinerary() {
		return itinerary;
	}

	private void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	private void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getSpeed() {
		return speed;
	}

	void setSpeed(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("Speed es negativa");
		}
		this.speed = Math.min(speed, maxSpeed);
	}

	public VehicleStatus getStatus() {
		return status;
	}

	private void setStatus(VehicleStatus status) {
		this.status = status;
	}

	public Road getRoad() {
		return road;
	}

	private void setRoad(Road road) {
		this.road = road;
	}

	public int getLocation() {
		return location;
	}

	private void setLocation(int location) {
		this.location = location;
	}

	public int getContClass() {
		return contaminationClass;
	}

	void setContaminationClass(int contClass) {
		if (contClass < 0 || contClass > 10) {
			throw new IllegalArgumentException("contClass negativo");
		}
		this.contaminationClass = contClass;
	}

	public int getTotalCO2() {
		return totalCO2;
	}

	private void setTotalCO2(int totalCO2) {
		this.totalCO2 = totalCO2;
	}
	

}
