package ghostsimulator.controller.listener;

import ghostsimulator.controller.DBManager;
import ghostsimulator.util.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * This listener shows an option pane to save the current editor content under a specific tag in the database.
 * It is invoked if the user clicks "save example" in the menu.
 * @author Vincent
 *
 */
public class DBStoreListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String[] tags = new String[0];
		
		do{
			String tagsString = JOptionPane.showInputDialog(Resources.getValue("menu.examples.saveexample"));
		
			if(tagsString != null && !tagsString.isEmpty()) {
				tags = tagsString.trim().split(" ");
		
				if(tags.length > 0) {
					DBManager.getInstance().storeCurrentExample(tags);
				}
			}
		} while(tags.length == 0);
	}

}
