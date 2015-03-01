package ghostsimulator;

import ghostsimulator.controller.EntityManager;
import ghostsimulator.controller.SimulationController;
import ghostsimulator.controller.XMLSerializationController;
import ghostsimulator.controller.tutor.TutorManager;
import ghostsimulator.model.Territory;
import ghostsimulator.util.Resources;
import ghostsimulator.view.GhostSimulatorFrame;

import javax.swing.SwingUtilities;

public class GhostSimulator {

	public static boolean DEBUG_MODE = false;
	
	private static Territory territory;

	public static void main(String[] args) {
		
		// create the model
		territory = new Territory();
		
		DEBUG_MODE = Boolean.parseBoolean(Resources.getSystemProperty("debug_mode"));
		EntityManager manager = EntityManager.getInstance();
		manager.setTerritory(territory);
		
		XMLSerializationController xmlSerializationController = new XMLSerializationController();
		manager.setXmlSerializationController(xmlSerializationController);
		
		SimulationController simulationController = new SimulationController();
		manager.setSimulationController(simulationController);
		
		// start the gui
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				GhostSimulatorFrame frame = new GhostSimulatorFrame(EntityManager.getInstance());
				
				// instantiate tutor manager
				TutorManager.getInstance();
				
				// show main frame
				frame.setVisible(true);
		    }
		  });
		
	}

}
