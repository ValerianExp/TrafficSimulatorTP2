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
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {
	
	private DefaultComboBoxModel<Vehicle> vehicleModel;
	private DefaultComboBoxModel<Integer> numberModel;
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
	private JComboBox<Vehicle> vehicleCB;
	private JComboBox<Integer> co2CB;

	
	public ChangeCO2ClassDialog(Frame frame) {
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
		toptext = new JLabel("Schedule an event to change the CO2 Class of a vehicle after a given number of simultation ticks from now.");
		toptextPanel.add(toptext);
	
		emergent.add(toptextPanel);

		// SELECTORES
		selectors = new JPanel();

		vehicle = new JLabel("Vehicle: ");
		vehicleModel = new DefaultComboBoxModel<Vehicle>();
		
		vehicleCB = new JComboBox<Vehicle>(vehicleModel);
		vehicleCB.setPreferredSize(new Dimension(100, 20));
		vehicleCB.setVisible(true);
		
		co2 = new JLabel("CO2 Class: ");
		numberModel = new DefaultComboBoxModel<Integer>();
		
		co2CB = new JComboBox<Integer>(numberModel);
		co2CB.setPreferredSize(new Dimension(100, 20));
		co2CB.setVisible(true);
		
		ticks = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 1));
		t = new JLabel("Ticks: ");

		selectors.add(vehicle);
		selectors.add(vehicleCB);
		selectors.add(co2);
		selectors.add(co2CB);
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
				ChangeCO2ClassDialog.this.setVisible(false);
			}
			
		});
		buttonsPanel.add(accept);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				estado = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
			
		});
		buttonsPanel.add(cancel);
		
		emergent.add(buttonsPanel);
		
		this.pack();
		setResizable(false);
		setVisible(false);
	}
	
	
	public int open(RoadMap map) {
		numberModel.removeAllElements();
		for (int i = 0; i < 11; i++) {
			numberModel.addElement(i);
		}
		
		vehicleModel.removeAllElements();
		for(Vehicle v: map.getVehicles()) {
			vehicleModel.addElement(v);
		}
		this.setLocationRelativeTo(getParent());
		setVisible(true);
		return estado;
	}
	
	public Vehicle getVehicle() {
		
		return (Vehicle) vehicleModel.getSelectedItem();
	}

	public Integer getCO2Class() {
		
		return (Integer) numberModel.getSelectedItem();
	}
	
	public Integer getTicks() {
		
		return (Integer) ticks.getValue();
	}
}
