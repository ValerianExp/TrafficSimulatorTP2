package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	private Junction srcJunc;
	private Junction destJunc;
	protected int length;
	protected int maxSpeed;
	protected int speedLimit;
	protected int contLimit;
	protected Weather weather;
	protected int totalCO2;
	private List<Vehicle> vehicles;
	private Comparator<? super Vehicle> SortVehiclesByLocation;

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		vehicles = new ArrayList<Vehicle>();
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
		if (maxSpeed < 0) {
			throw new IllegalArgumentException("maxSpeed en la clase constructora Road es negativa");
		} else if (contLimit < 0) {
			throw new IllegalArgumentException("contLimit en la clase constructora Road es negativa");
		} else if (length < 0) {
			throw new IllegalArgumentException("length en la clase constructora Road es negativa");
		} else if (srcJunc == null) {
			throw new IllegalArgumentException("srcJunc en la clase constructora Road es null");
		} else if (destJunc == null) {
			throw new IllegalArgumentException("destJunc en la clase constructora Road es null");
		} else if (weather == null) {
			throw new IllegalArgumentException("weather en la clase constructora Road es null");
		}
		
	}

	void enter(Vehicle v) {
		if (v.getSpeed() > 0 || v.getLocation() > 0)
			throw new IllegalArgumentException("Error");
		vehicles.add(v);
	}

	void exit(Vehicle v) {
		vehicles.remove(v);
	}

	void setWeather(Weather w) {
		if (w == null) {
			throw new IllegalArgumentException("Error en setWeather");
		}
		weather = w;
	}

	public abstract void reduceTotalContamination();

	public abstract void updateSpeedLimit();

	public abstract int calculateVehicleSpeed(Vehicle v);

	public int getLength() {
		return length;
	}

	public void addContamination(int c) {
		if (c < 0) {
			throw new IllegalArgumentException("parametro c en el metodo negativa");
		}
		totalCO2 += c;
	}


	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for (Vehicle v : vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		vehicles.sort(new SortVehiclesByLocation());

	}

	public class SortVehiclesByLocation implements Comparator<Vehicle> {
		@Override
		public int compare(Vehicle v1, Vehicle v2) {
			if (v1.getLocation() == v2.getLocation())
				return 0;
			else if (v1.getLocation() < v2.getLocation())
				return -1;
			else
				return 1;
		}
	}

	public Junction getSrcJunc() {
		return srcJunc;
	}

	public Junction getDestJunc() {
		return destJunc;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public int getContLimit() {
		return contLimit;
	}

	public int getTotalCO2() {
		return totalCO2;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}

	public Weather getWeather() {
		return weather;
	}

	@Override
	public JSONObject report() {
		JSONObject j = new JSONObject();
		JSONArray vehiclesArray = new JSONArray();

		j.put("id", _id);
		j.put("speedlimit", speedLimit);
		j.put("weather", weather);
		j.put("co2", totalCO2);
		for (Vehicle v : vehicles) {
			vehiclesArray.put(v);
		}
		j.put("vehicles", vehiclesArray);

		return j;
	}

}
