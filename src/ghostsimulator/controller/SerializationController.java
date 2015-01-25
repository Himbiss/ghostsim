package ghostsimulator.controller;

import ghostsimulator.GhostManager;
import ghostsimulator.model.Territory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

public class SerializationController implements ActionListener {

	private GhostManager manager;
	private final JFileChooser fc = new JFileChooser();

	public SerializationController(GhostManager manager) {
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == manager.getMenubar().serializeTerritoryItem) {
			int returnVal = fc.showSaveDialog(manager.getFrame());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// serialize the territory
				try (FileOutputStream fileOut = new FileOutputStream(file);
						ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
					out.writeObject(manager.getTerritory());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == manager.getMenubar().deserializeTerritoryItem) {
			int returnVal = fc.showOpenDialog(manager.getFrame());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// deserialize the territory
				try (FileInputStream fileIn = new FileInputStream(file);
						ObjectInputStream in = new ObjectInputStream(fileIn)) {
						manager.setTerritory((Territory) in.readObject());
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
