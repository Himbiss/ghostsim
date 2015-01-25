package ghostsimulator.controller;

import ghostsimulator.util.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
