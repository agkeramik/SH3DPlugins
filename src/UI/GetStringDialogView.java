package UI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


import com.eteks.sweethome3d.model.Home;

import exporter.XML.RoomExporter;



public class GetStringDialogView extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Home home;
	JTextField field;
	JButton saveButton, cancelButton;

	public GetStringDialogView(Home home) {
		setSize(300, 100);
		setLocation(500, 500);
		this.home = home;
		setLayout(new GridLayout(2, 2));
		add(new JLabel("FileName"));

		field = new JTextField();
		add(field);
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		add(saveButton);
		add(cancelButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == saveButton) {
			RoomExporter exporter = new RoomExporter();
			exporter.export(field.getText(),home);
			dispose();
		} else if (e.getSource() == cancelButton) {
			dispose();
		}
	}

}
