package ghostsimulator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ghostsimulator.GhostManager;

public class XMLSerializationController implements ActionListener {

	private GhostManager manager;
	
	public XMLSerializationController(GhostManager manager) {
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == manager.getMenubar().saveTerritoryItem) {
			
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritoryDOM) {
			
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritorySAX) {
			
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritoryStAXCursor) {
			
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritoryStAXIterator) {
			
		}
	}

}
