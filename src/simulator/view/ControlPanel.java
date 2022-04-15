package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;



public class ControlPanel extends JPanel implements TrafficSimObserver	{
	private final String openText = "Change CO2 Class of a Vehicle";
	private final String CO2Text = "Change CO2 Class of a Vehicle";
	private final String weatherText = "Change Weather of a Road";
	private final String runText = "Run the simulator";
	private final String stopText = "Stop the simulator";
	private final String ticksText = "Simulation tick to run: 1-10000";
	private final String exitText = "Exits the simulator";

	
	
	private Controller ctrl;
	private JLabel ticksLabel;
	private boolean _stopped = false;
	JButton openButton;
	JButton CO2Button;
	JButton weatherButton;
	JButton runButton;
	JButton stopButton;
	JSpinner ticks;
	JButton exitButton;
	JToolBar panel;
	private RoadMap map;
	private int tiempo;
	private ChangeCO2ClassDialog changeCO2;
	private ChangeWeatherDialog changeWeather;
	public ControlPanel(Controller _ctrl) {
		super();
		ctrl = _ctrl;
		_stopped = true;
		openButton= new JButton();
		CO2Button= new JButton();
		weatherButton= new JButton();
		runButton= new JButton();
		stopButton= new JButton();
		ticks = new JSpinner(new SpinnerNumberModel(10, 0, 10000, 10));
		exitButton= new JButton();
		
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		panel = new JToolBar(); 
		
		//BOTON OPEN
		openButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\open.png")));
		openButton.setToolTipText(openText);
		
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc=new JFileChooser();
				updateUI();
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JSON", "json");
				fc.setFileFilter(filtro);
				fc.setCurrentDirectory(new File("resources/examples/"));
				int seleccion=fc.showOpenDialog(new JPanel());
				if(seleccion==JFileChooser.APPROVE_OPTION){
					try {
						File fichero=fc.getSelectedFile();
						ctrl.reset();
						ctrl.loadEvents(new FileInputStream(fichero.toString()));
					} catch(FileNotFoundException e1){
						System.err.println("Error loading files");
					}
				}
				else {
					JOptionPane.showMessageDialog((Frame) SwingUtilities.getWindowAncestor(ControlPanel.this), "Process cancelled");
				}
			}
		});
		
		//BOTON CO2
		CO2Button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\co2class.png")));
		CO2Button.setToolTipText(CO2Text);
		
		CO2Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectCO2();
			}
		});
		
		//BOTON WEATHER
		weatherButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\weather.png")));
		weatherButton.setToolTipText(weatherText);
		
		weatherButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectWeather();
			}
		});
		
		//BOTON RUN
		runButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\run.png")));
		runButton.setToolTipText(runText);
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater( new Runnable() {
					@Override
					public void run() {
						enableToolBar(false);
						_stopped = false;
						int i = Integer.parseInt(ticks.getValue().toString());
						run_sim(i);
					}
				});
			}
		});

		
		//BOTON STOP
		stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\stop.png")));
		stopButton.setToolTipText(stopText);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = true;		
			}
		});
		
		//SPINNER TICKS
		ticksLabel = new JLabel("Ticks: ");
			
		ticks.setMinimumSize(new Dimension(100,200));
		ticks.setMaximumSize(new Dimension(500,200));
		ticks.setToolTipText(ticksText);		

		
		//BOTON EXIT
		exitButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\exit.png")));
		exitButton.setToolTipText(exitText);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(i==0) System.exit(0);
			}
		});
		

		
		panel.add(openButton);
		panel.add(CO2Button);
		panel.add(weatherButton);
		panel.addSeparator(); 
		panel.add(runButton);
		panel.add(stopButton);
		panel.add(ticksLabel);
		panel.add(ticks);
		panel.add(Box.createGlue());
		panel.add(exitButton);
		
		this.add(panel);
	}

	protected void selectCO2() {
		changeCO2 = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this));	
		int estado = changeCO2.open(map);
		
		if(estado != 0) {
			List<Pair<String,Integer>> cs = new ArrayList<>();
			
			cs.add(new Pair<String,Integer>(changeCO2.getVehicle().getId(),changeCO2.getCO2Class()));
			
			int time = tiempo + changeCO2.getTicks();
			ctrl.addEvent(new NewSetContClassEvent(time, cs));
		}
	}
	protected void selectWeather() {
		changeWeather = new ChangeWeatherDialog((Frame) SwingUtilities.getWindowAncestor(this));	
		int estado = changeWeather.open(map);
		
		if(estado != 0) {
			List<Pair<String,Weather>> ws = new ArrayList<>();
			
			ws.add(new Pair<String,Weather>(changeWeather.getRoad().getId(),changeWeather.getWeather()));
			
			int time = tiempo + changeWeather.getTicks();
			ctrl.addEvent(new SetWeatherEvent(time, ws));
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.map = map;
		tiempo = time;
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.map = map;
		tiempo = time;
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.map = map;
		tiempo = time;
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.map = map;
		tiempo = time;
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.map = map;
		tiempo = time;
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1,System.out);
			} catch (Exception e) {
				// TODO show error message
				
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			enableToolBar(true);
			_stopped = true;
		}
	}
	
	private void stop() {
		_stopped = true;
	}
		
	private void enableToolBar(boolean b) {
		openButton.setEnabled(b);
		CO2Button.setEnabled(b);
		weatherButton.setEnabled(b);
		runButton.setEnabled(b);
//		stopButton.setEnabled(b);
		ticks.setEnabled(b);
		exitButton.setEnabled(b); 
	}


}
