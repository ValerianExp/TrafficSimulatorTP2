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
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Junction> getItinerary() {
		return itinerary;
	}

	public void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("Speed es negativa");
		}
		this.speed = Math.min(speed, maxSpeed);
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getContClass() {
		return contaminationClass;
	}

	public void setContaminationClass(int contClass) {
		if (contClass < 0 || contClass > 10) {
			throw new IllegalArgumentException("contClass negativo");
		}
		this.contaminationClass = contClass;
	}

	public int getTotalCO2() {
		return totalCO2;
	}

	public void setTotalCO2(int totalCO2) {
		this.totalCO2 = totalCO2;
	}
	

}
