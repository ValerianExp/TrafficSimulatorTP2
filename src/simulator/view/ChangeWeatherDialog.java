package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import extra.dialog.Dish;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	
	private DefaultComboBoxModel<Road> roadModel;
	private DefaultComboBoxModel<Weather> weatherModel;
	private JSpinner ticks;
	private int estado = 0;
	private JButton accept;
	private JButton cancel;
	private JPanel toptextPanel;
	private JPanel selectors;
	private JPanel buttonsPanel;
	private JPanel emergent;
	private JLabel vehicle;
	private JLabel toptext;
	private JLabel co2;
	private JLabel t;
	private JComboBox<Road> roadCB;
	private JComboBox<Weather> weatherCB;
	
	public ChangeWeatherDialog(Frame frame) {
		super(frame, true);
		setTitle("Change CO2 Class");
		initGUI();
	}

	private void initGUI() {
		// EMERGENT
		emergent = new JPanel();
		emergent.setLayout(new BoxLayout(emergent, BoxLayout.Y_AXIS));
		setContentPane(emergent);
		
		// TOPTEXT
		toptextPanel = new JPanel();
		toptext = new JLabel("Schedule an event to change the weather of a road after a given number of simultation ticks from now.");
		toptextPanel.add(toptext);
	
		emergent.add(toptextPanel);

		// SELECTORES
		selectors = new JPanel();

		vehicle = new JLabel("Road: ");
		roadModel = new DefaultComboBoxModel<Road>();
		
		roadCB = new JComboBox<Road>(roadModel);
		roadCB.setPreferredSize(new Dimension(100, 20));
		roadCB.setVisible(true);
		
		co2 = new JLabel("Weather: ");
		weatherModel = new DefaultComboBoxModel<Weather>();
		
		weatherCB = new JComboBox<Weather>(weatherModel);
		weatherCB.setPreferredSize(new Dimension(100, 20));
		weatherCB.setVisible(true);
		
		ticks = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 1));
		t = new JLabel("Ticks: ");

		selectors.add(vehicle);
		selectors.add(roadCB);
		selectors.add(co2);
		selectors.add(weatherCB);
		selectors.add(t);
		selectors.add(ticks);

		emergent.add(selectors);
		
		// BOTONES
		buttonsPanel = new JPanel();
		
		accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				estado = 1;
				ChangeWeatherDialog.this.setVisible(false);
			}
			
		});
		buttonsPanel.add(accept);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				estado = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
			
		});
		buttonsPanel.add(cancel);
		
		emergent.add(buttonsPanel);
		
		this.pack();
		setResizable(false);
		setVisible(false);
	}
	
	
	public int open(RoadMap map) {
		roadModel.removeAllElements();
		for (Road r: map.getRoads()) {
			roadModel.addElement(r);
		}
		
		weatherModel.removeAllElements();
		for(Weather w: Weather.values()) {
			weatherModel.addElement(w);
		}
		this.setLocationRelativeTo(getParent());
		setVisible(true);
		return estado;
	}
	
	public Integer getTicks() {
		
		return (Integer) ticks.getValue();
	}

	public Weather getWeather() {
		
		return (Weather) weatherModel.getSelectedItem();
	}
	
	public Road getRoad() {
		
		return (Road) roadModel.getSelectedItem();
	}
}
