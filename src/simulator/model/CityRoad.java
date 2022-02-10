package simulator.model;

public class CityRoad extends Road {

    CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    public void reduceTotalContamination() {
    	if (totalCO2 - getWeather().getInterContam() > 0) {
    		totalCO2 -= getWeather().getInterContam();
		}
    }

    @Override
    public void updateSpeedLimit() {
        //La velocidad no cambia, siempre es la la velocidad maxima;
            speedLimit = maxSpeed;
    }

    @Override
    public int calculateVehicleSpeed(Vehicle v) {
        
        int s = speedLimit;
        int f = v.getContClass();
        s = ((11 - f) * s) / 11;
        return s;
    }

}
