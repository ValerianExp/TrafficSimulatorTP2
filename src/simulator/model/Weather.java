package simulator.model;

public enum Weather {
	SUNNY(2), CLOUDY(3), RAINY(10), WINDY(15), STORM(20);

	int value;

	//TODO Preguntar si se puede hacer 
	//Creo que no se puede hacer mira la clase CityRoad, pone otro tipo de valores para cada enumerado
	Weather(int value) {
		this.value = value;

	}

	public int getValue() {
		return value;
	}
}
