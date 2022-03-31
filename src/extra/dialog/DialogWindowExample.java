package extra.dialog;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DialogWindowExample extends JFrame {


	public DialogWindowExample() {
		super("Custom Dialog Example");
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);//this.add(mainPanel);

		mainPanel.add(new JLabel("Click "));
		JButton here = new JButton("HERE");
		here.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				select_food();
			}
		});
		mainPanel.add(here);
		mainPanel.add(new JLabel(" to select your food"));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	protected void select_food() {

		// if you're in a JPanel class, you can use the following
		//
		// (Frame) SwingUtilities.getWindowAncestor(this)
		//
		// in order to get the parent JFrame. Then pass it to the constructor
		// of MyDialogWindow instead of 'this'
		//
		
		//En nuestro caso sera el panel de Control, ventanita del co2 y el weather
		MyDialogWindow dialog = new MyDialogWindow(this);
		//Otra manera:En el cosntructor en otro caso se tendría que pasar la lista de coches y carreteras
		//Como traemos la lista, panel de control es el observer, y casi todas las funciones tienen roadMap como parametro

		List<Dish> dishes = new ArrayList<Dish>();
		for (int i = 0; i < 10; i++) {
			dishes.add(new Dish("Yum Yum " + i));
		}
		//Otra manera: un getter en el estado
		int status = dialog.open(dishes);

		if (status == 0) {
			System.out.println("Canceled");
		} else {
			System.out.println("Your favorite dish is: " + dialog.getDish());
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DialogWindowExample();
			}
		});
	}
}