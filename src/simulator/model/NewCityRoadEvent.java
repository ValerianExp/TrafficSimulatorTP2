package simulator.model;

public class NewCityRoadEvent extends Event {
	private String id;
	private String srcJunc;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	
	
	public NewCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
		super(time);
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}


	@Override
	void execute(RoadMap map) {
		CityRoad cr = new CityRoad(id,map.getJunction(srcJunc), map.getJunction(destJunc), length, co2Limit, maxSpeed, weather);
		map.addRoad(cr);
	}

	
}
