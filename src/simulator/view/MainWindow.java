package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JPanel vehiclesView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JPanel roadsView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		
		//TODO vehicleSpeed examen
		JPanel vehiclesSpeed = createViewPanel(new JTable(new VehiclesSpeedExamen(_ctrl)), "Vehicles Speed");
		vehiclesSpeed.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesSpeed);
		
		
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));	
		mapsPanel.add(mapView);
		
		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapByRoadView.setPreferredSize(new Dimension(500, 400));	
		mapsPanel.add(mapByRoadView); 
		
//		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//		int x = (int) ((d.getWidth() - this.getWidth() / 2));
//		int y = (int) ((d.getHeight() - this.getHeight() / 2));
		setLocation(100,100);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\icons\\co2class.png"));
		this.pack();
		this.setVisible(true);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2,true), title, TitledBorder.LEFT,TitledBorder.TOP));
		p.add(new JScrollPane(c));
		return p;
	}
}
