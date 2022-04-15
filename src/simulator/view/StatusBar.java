package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private JLabel timeLabel = new JLabel();
	private JLabel eventLabel = new JLabel();
	private JLabel time = new JLabel("Time: ");
	
	public StatusBar(Controller _ctrl) {
		initGUI();
		_ctrl.addObserver(this);
	}

	
	private void initGUI() {
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		time = new JLabel("Time: ", JLabel.LEFT);
		timeLabel = new JLabel("");
		this.add(time);
		this.add(timeLabel);
		
		this.add(new JSeparator(SwingConstants.VERTICAL));
		
		eventLabel = new JLabel("");
		this.add(eventLabel);
	}
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("" + time);
		eventLabel.setText("");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("" + time);
		eventLabel.setText("");	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		timeLabel.setText("" + time);
		eventLabel.setText("" + events);		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		timeLabel.setText("" + time);
		eventLabel.setText("");	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	

}
