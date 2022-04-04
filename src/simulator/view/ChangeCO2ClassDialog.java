package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog{
	
	public ChangeCO2ClassDialog() {
		setTitle("Change CO2 Class");
		initGUI();
	}

	private void initGUI() {
		//TODO Esta mal, pero funciona
		//Creo que hay que poner toptext, selectores, y botones
		//En su propio JPanel de cada uno
		this.setLayout(new BorderLayout());
		//top text(?)
		JLabel toptext = new JLabel();
		toptext.setText("Schedule an event to change the CO2 Class of a vehicle after a given number of simultation ticks from now");
		this.add(toptext, BorderLayout.NORTH);
		
		//selectores
		JPanel selectors = new JPanel();
		this.add(selectors, BorderLayout.CENTER);
		JLabel v = new JLabel();
		v.setText("Vehicle: ");
		//TODO Necesita la informacion de los vehicles
		JComboBox<Vehicle> vehicleCB = new JComboBox<Vehicle>();
		vehicleCB.setPreferredSize(new Dimension(100, 20));
		
		JComboBox<Integer> co2Class = new JComboBox<Integer>();
		co2Class.setPreferredSize(new Dimension(100, 20));
		JLabel c = new JLabel();
		c.setText("CO2 Class: ");
		selectors.add(v);
		selectors.add(vehicleCB);
		selectors.add(c);
		selectors.add(co2Class);
		this.pack();
	}
	
}
