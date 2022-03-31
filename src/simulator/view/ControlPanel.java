package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver	{
	private Controller ctrl;
	public ControlPanel(Controller _ctrl) {
		super();
		ctrl = _ctrl;
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JToolBar panel = new JToolBar(); 
//		BorderLayout panel = new BorderLayout();
		
		
		JButton openButton= new JButton();
		openButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\open.png")));
		panel.add(openButton);

		JButton CO2Button= new JButton();
		CO2Button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\co2class.png")));
		panel.add(CO2Button);
		
		JButton weatherButton= new JButton();
		weatherButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\weather.png")));
		panel.add(weatherButton);
		
		panel.addSeparator(); 
		
		JButton runButton= new JButton();
		runButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\run.png")));
		panel.add(runButton);
		
		JButton stopButton= new JButton();
		stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\stop.png")));
		panel.add(stopButton);
		
		JLabel ticksLabel = new JLabel("Ticks: ");
		panel.add(ticksLabel);
		
		JSpinner ticks = new JSpinner();
		ticks.setMaximumSize(new Dimension(500,200));
//		ticks.setMinimumSize(new Dimension(100,200));
		panel.add(ticks);
		
//		panel.addSeparator(); 
		panel.add(Box.createGlue());
		
		
		JButton exitButton= new JButton();
		exitButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\exit.png")));
		panel.add(exitButton);
		
		this.add(panel);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
