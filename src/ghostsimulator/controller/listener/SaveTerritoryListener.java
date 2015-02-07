package ghostsimulator.controller.listener;


import ghostsimulator.controller.EntityManager;
import ghostsimulator.controller.XMLSerializationController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class SaveTerritoryListener implements ActionListener {
	
	private final JFileChooser fc = new JFileChooser();
	
	/**
	 * Shows a Save Dialog and returns the selected file. Returns null if
	 * canceled
	 * 
	 * @return file
	 */
	private File getFileSaveDialog() {
		int returnVal = fc.showSaveDialog(EntityManager.getInstance().getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManager manager = EntityManager.getInstance();
		XMLSerializationController controller = manager.getXmlSerializationController();
		if (e.getSource() == manager.getMenubar().saveTerritoryItem) {
			controller.saveWithStAX(getFileSaveDialog());
		}
	}
	
}
