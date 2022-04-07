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
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;



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
	JButton openButton= new JButton();
	JButton CO2Button= new JButton();
	JButton weatherButton= new JButton();
	JButton runButton= new JButton();
	JButton stopButton= new JButton();
	JSpinner ticks = new JSpinner(new SpinnerNumberModel(10, 0, 10000, 10));
	JButton exitButton= new JButton();
	//TODO Cambiar los news al constructor
	JToolBar panel;
	
	public ControlPanel(Controller _ctrl) {
		super();
		ctrl = _ctrl;
		initGUI();
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
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JSON", "json");
				fc.setFileFilter(filtro);
				fc.setCurrentDirectory(new File("resources/examples/"));
				int seleccion=fc.showOpenDialog(new JPanel());
				if(seleccion==JFileChooser.APPROVE_OPTION){
					File fichero=fc.getSelectedFile();
					ctrl.reset();
					try {
						ctrl.loadEvents(new FileInputStream(fichero.toString()));
					} catch(FileNotFoundException e1){
						System.err.println("Error loading files");
					}
				}
			}
		});
		
		//BOTON CO2
		CO2Button.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\co2class.png")));
		CO2Button.setToolTipText(CO2Text);
		
		CO2Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectCO2();
			}
		});
		
		
		//BOTON WEATHER
		weatherButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\weather.png")));
		weatherButton.setToolTipText(weatherText);
		
		//BOTON RUN
		runButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\run.png")));
		runButton.setToolTipText(runText);
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater( new Runnable() {
					@Override
					public void run() {
						int i = Integer.parseInt(ticks.getValue().toString());
						run_sim(i);
					}
				});
			}
		});

		
		//BOTON STOP
		stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources\\icons\\stop.png")));
		stopButton.setToolTipText(stopText);
		
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
		ChangeCO2ClassDialog g = new ChangeCO2ClassDialog((Frame) SwingUtilities.getWindowAncestor(this));	
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
		ticks.setEnabled(b);
		//TODO Poner todos los botones
	}


}
