package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	@Override
	public void reduceTotalContamination() {
		totalCO2 = ((100 - getWeather().getInterContam()) * totalCO2)/100;
		
	}

	@Override
	public void updateSpeedLimit() {
		if(totalCO2 > contLimit) {
			speedLimit = maxSpeed/2;
		}
		else {
			speedLimit = maxSpeed;
		}
		
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		if (weather == Weather.STORM) {
			return((speedLimit*8)/10);
		}
		else {
			return(speedLimit);
		}
	}

}
