package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;



public class MapByRoadComponent extends JComponent implements TrafficSimObserver{
	private Controller ctrl;
	private RoadMap _map;
	private Color _RED_LIGHT_COLOR= Color.RED;
	private Color _GREEN_LIGHT_COLOR=Color.GREEN;
	private Color _BLUE_LIGHT_COLOR=Color.BLUE;
	private Image _car;
	private static final Color _BG_COLOR = Color.WHITE;

	public MapByRoadComponent(Controller ctrl) {
		super();
		this.ctrl = ctrl;
		setPreferredSize (new Dimension (300, 200));
		initGUI();
		this.ctrl.addObserver(this);
	}
	
	public void initGUI()

	{
		_car = loadImage("car.png");
	}
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		for (int i=0;i<_map.getRoads().size();i++) {

			int x1 = 50;
			int x2 = getWidth()-100;
			int y=(i+1)*50;

			//VEHICLES
			drawVehicles(g,_map.getRoads().get(i),y);
			
			g.setColor(Color.BLACK);
			g.drawLine(x1, y, x2, y);
			g.drawString(_map.getRoads().get(i).getId(), x1-40, y);
		
			Color circleColorOrig=_BLUE_LIGHT_COLOR;
			g.setColor(circleColorOrig);
			g.fillOval(x1 - 10 / 2, y - 10 / 2, 10, 10);
			g.setColor(Color.ORANGE);
			g.drawString(_map.getRoads().get(i).getSrc().getId(), x1-5, y-10);
			
			Color circleColorDest= _RED_LIGHT_COLOR;
			int idx = _map.getRoads().get(i).getDest().getGreenLightIndex();
			if (idx != -1 && _map.getRoads().get(i).equals(_map.getRoads().get(i).getDest().getInRoads().get(idx))) {
				circleColorDest = _GREEN_LIGHT_COLOR;
			}
			g.setColor(circleColorDest);
			g.fillOval(x2 - 10 / 2, y - 10 / 2, 10, 10);
			g.setColor(Color.ORANGE);
			g.drawString(_map.getRoads().get(i).getDest().getId(), x2-5, y-10);
			//Weather
			drawWeather(g,_map.getRoads().get(i), x2+10,y-20);
			
			//Contamination
			drawContam(g,_map.getRoads().get(i), x2+50,y-20);
		}

	}
	
	private void drawVehicles(Graphics g, Road r,int y) {
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				if(r.getVehicles().contains(v)) {
				int x1 = 50;
				int x2 = getWidth()-100-20;
				
				
				double A= (float)v.getLocation();
				double B= r.getLength();
				int x= x1 + (int) ((x2 - x1) * ((double) A / (double) B));

				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.drawImage(_car, x, y - 12 , 16, 16, this);
				g.setColor(new Color(0, vLabelColor, 0));
				g.drawString(v.getId(), x, y - 12);
				}
			}
		}
	}
	private void drawWeather(Graphics g, Road r, int x, int y) {
		
		switch(r.getWeather())
		{
		case SUNNY:
			g.drawImage(loadImage("sun.png"), x, y, 32, 32, null, this);
			break;
		case STORM:
			g.drawImage(loadImage("storm.png"), x, y, 32, 32, null, this);
			break;
		case CLOUDY:
			g.drawImage(loadImage("cloud.png"), x, y, 32, 32, null, this);
			break;
		case RAINY:
			g.drawImage(loadImage("rain.png"), x, y, 32, 32, null, this);
			break;
		case WINDY:
			g.drawImage(loadImage("wind.png"), x, y, 32, 32, null, this);
			break;
		}
		
	}
private void drawContam(Graphics g, Road r, int x, int y) {
		double A=r.getTotalCO2();
		double B= r.getContLimit();
	int C = (int) Math.floor(Math.min((double) A/(1.0 + (double) B),1.0) / 0.19);
	
	String imagen= "cont_"+C+".png";
	g.drawImage(loadImage(imagen), x, y, 32, 32, null, this);
		
	}
	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	
	}

	@Override
	public void onError(String err) {
		// TODO onError
		
	}

}