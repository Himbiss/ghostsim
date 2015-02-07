package ghostsimulator.controller.listener;


import ghostsimulator.controller.EntityManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

public class SerializeTerritoryListener implements ActionListener {

	private final JFileChooser fc = new JFileChooser();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManager manager = EntityManager.getInstance();
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
	}

}
