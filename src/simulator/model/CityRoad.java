package simulator.model;

public class CityRoad extends Road {

    CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    public void reduceTotalContamination() {
        int x;
        switch (weather) {
            case WINDY:
            case STORM:
                x = 10;
                break;
            default:
                x = 2;
                break;
        }
        totalCO2 -= x;
    }

    @Override
    public void updateSpeedLimit() {
        //TODO completar
        //Si la velocidad no cambia, siempre es la la velocidad maxima;
        if (true) {
            speedLimit = maxSpeed;
        }
    }

    @Override
    public int calculateVehicleSpeed(Vehicle v) {
        //TODO Velocidad limite de la carretera es speedLimit, no?
        int s = speedLimit;
        int f = v.getContClass();
        s = ((11 - f) * s) / 11;
        return s;
    }

}
