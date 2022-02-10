package simulator.model;

public enum Weather {
	SUNNY(2,2), CLOUDY(3,2), RAINY(10,2), WINDY(15,10), STORM(20,10);

	int interContam;
	int urbanContam;


	Weather(int interContam, int urbanContam) {
		this.interContam = interContam;
		this.urbanContam = urbanContam;

	}

	public int getUrbanContam() {
		return urbanContam;
	}
	
	public int getInterContam() {
		return interContam;
	}
}
