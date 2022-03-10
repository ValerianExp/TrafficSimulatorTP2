package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int maxSpeed;
	private int speed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int totalCO2;
	private int totalDistance;
	private int lastJunctionIndex;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);

		if (maxSpeed < 0) {
			throw new IllegalArgumentException("maxSpeed en la clase constructora Vehiculo es negativa");
		} else if (contClass < 0 || contClass > 10) {
			throw new IllegalArgumentException("contClass en la clase constructora Vehiculo no esta entre 0 y 10");
		} else if (itinerary.size() < 2) {
			// Al menos 2
			throw new IllegalArgumentException(
					"el tamaño de itineray.size() en la clase constructora Vehiculo no es al menos 2");
		} else if (maxSpeed <= 0) {
			throw new IllegalArgumentException("maxSpeed debe de ser postiva");
		}
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		lastJunctionIndex = 0;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		
		status = VehicleStatus.PENDING;
	}

	@Override
	void advance(int time) {
		if (speed < 0) {
			throw new IllegalArgumentException("Velocidad es negativa en el metodo advance() de Vehiculo");
		}
		else if (status == VehicleStatus.TRAVELING) {
			int oldLocation = location;
			location = Math.min(location + speed, road.getLength());
			int c = contClass * (location - oldLocation);
			totalCO2 += c;
			road.addContamination(c);
			totalDistance += location - oldLocation;
			if (location >= road.getLength()) {
				Junction j;
				j = road.getDest();
				j.enter(this);
				setSpeed(0);
				status = VehicleStatus.WAITING;
			}
		} 
	}

	void moveToNextRoad() {
		if (status == VehicleStatus.PENDING) {
			road = itinerary.get(lastJunctionIndex).roadTo(itinerary.get(lastJunctionIndex + 1));

			if(road != null) {
				road.enter(this);
			}
			//road.updateSpeedLimit();
			//itinerary.get(lastJunctionIndex).roadTo(itinerary.get(lastJunctionIndex + 1)).enter(this);
			//setLocation(0);
			this.status = VehicleStatus.TRAVELING;
			lastJunctionIndex++;
			setSpeed(0);
			setLocation(0);
			
		} else if (status == VehicleStatus.WAITING) {
			if (lastJunctionIndex + 1 == itinerary.size()) {
				road.exit(this);
				status = VehicleStatus.ARRIVED;
				
			} else {
				road.exit(this);
				Road r = road.getDest().roadTo(itinerary.get(lastJunctionIndex + 1));
				this.road = r;
				this.status = VehicleStatus.TRAVELING;
				setSpeed(0);
				setLocation(0);
				road.enter(this);
				lastJunctionIndex++;

				//setLocation(0);
			}
			//road.updateSpeedLimit();
		
		} else {
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
		if (status == VehicleStatus.TRAVELING) {
			this.speed = Math.min(speed, maxSpeed);
		}

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
		return contClass;
	}

	void setContClass(int contClass) {
		if (contClass < 0 || contClass > 10) {
			throw new IllegalArgumentException("contClass negativo");
		}
		this.contClass = contClass;
	}

	public int getTotalCO2() {
		return totalCO2;
	}

	private void setTotalCO2(int totalCO2) {
		this.totalCO2 = totalCO2;
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		j.put("id", _id.toString());
		j.put("speed", (int)speed);
		j.put("distance", (int)totalDistance);
		j.put("co2", (int)totalCO2);
		j.put("class", (int)contClass);
		j.put("status", status.toString());
		if (!(status == VehicleStatus.PENDING || status == VehicleStatus.ARRIVED)) {
			j.put("road", road.toString());
			j.put("location", (int)location);
		}
		return j;
	}

}
