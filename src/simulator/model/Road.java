package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class Road extends SimulatedObject{
	
	private Junction srcJunc;
	private Junction destJunc;
	private int length;
	private int maxSpeed;
	private int speedLimit;
	private int contLimit;
	private Weather weather;
	private int totalCO2;
	private List<Vehicle> vehicles;
	
	

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		if (maxSpeed < 0 || contLimit < 0 || length < 0 || srcJunc == null || destJunc == null || weather == null) {
			throw new IllegalArgumentException("Error en los parametros");
		}
	}
	
	void enter(Vehicle v) {
		if (v.getSpeed() > 0 || v.getLocation() >0 )  throw new IllegalArgumentException("Error");
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
		// TODO Auto-generated method stub
		return 0;
	}

	public void addContamination(int c) {
		if (c < 0) {
			throw new IllegalArgumentException("c es negativa");
		}
		totalCO2 += c;
	}
	
	
	public Junction getDest() {
		return destJunc;
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for (Vehicle v : vehicles) {
			//TODO terminar
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
