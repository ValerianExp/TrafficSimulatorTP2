package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
<<<<<<< Updated upstream
import javax.swing.SpinnerNumberModel;
=======
import javax.swing.SwingConstants;
>>>>>>> Stashed changes

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
		
		//openButton
		JButton openButton= new JButton();
		openButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\open.png")));
		openButton.setToolTipText("Load files to the simulation");
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./resources/examples/"));
		openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = fc.showOpenDialog(fc);
				InputStream inputStream;
				if (ret == JFileChooser.APPROVE_OPTION) {
					try {
						inputStream = new FileInputStream(fc.getSelectedFile());
						ctrl.reset();
						ctrl.loadEvents(inputStream);
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "File not found");				}
				}
			}
		});
		
		panel.add(openButton);
		panel.addSeparator();
		
		
		//CO2Button
		JButton CO2Button= new JButton();
		CO2Button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\co2class.png")));
		CO2Button.setToolTipText("Change CO2 of a vehicle");
		CO2Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeCO2ClassDialog d = new ChangeCO2ClassDialog();
				d.setVisible(true);
			}
		});
		panel.add(CO2Button);
		
		
		//WeatherButton
		JButton weatherButton= new JButton();
		weatherButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\weather.png")));
		panel.add(weatherButton);
		
		panel.addSeparator();
		
		//RunButton
		JButton runButton= new JButton();
		runButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\run.png")));
		panel.add(runButton);
		
		//StopButton
		JButton stopButton= new JButton();
		stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\stop.png")));
		panel.add(stopButton);
		stopButton.setToolTipText("Open file");
		
		//Ticks
		JLabel ticksLabel = new JLabel("Ticks: ");
		panel.add(ticksLabel);
		
		JSpinner ticks = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 10));
		ticks.setMinimumSize(new Dimension(100,200));
		ticks.setMaximumSize(new Dimension(500,200));
		JSpinner ticks = new JSpinner();
		ticks.setMinimumSize(new Dimension(100 , 40));
		ticks.setMaximumSize(new Dimension (100, 40));
		ticks.setPreferredSize(new Dimension(100, 40));
		panel.add(ticks);

		panel.addSeparator(); 
		
		//ExitButton
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
