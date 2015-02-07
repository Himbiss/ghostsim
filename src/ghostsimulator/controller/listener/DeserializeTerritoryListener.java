package ghostsimulator.controller.listener;

import ghostsimulator.controller.EntityManager;
import ghostsimulator.model.Territory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

/**
 * This listener is used to load a territory from a serialized file on the computer.
 * @author vincent
 *
 */
public class DeserializeTerritoryListener implements ActionListener {

	private final JFileChooser fc = new JFileChooser();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManager manager = EntityManager.getInstance();
		int returnVal = fc.showOpenDialog(manager.getFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// deserialize the territory
			try (FileInputStream fileIn = new FileInputStream(file);
					ObjectInputStream in = new ObjectInputStream(fileIn)) {
					manager.getTerritoryManager().changeTerritory((Territory) in.readObject());
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
		}
	}

}
