package ghostsimulator;

import ghostsimulator.controller.EntityManager;
import ghostsimulator.controller.SimulationController;
import ghostsimulator.controller.XMLSerializationController;
import ghostsimulator.controller.tutor.TutorManager;
import ghostsimulator.model.Territory;
import ghostsimulator.util.Resources;
import ghostsimulator.view.GhostSimulatorFrame;

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
		
		GhostSimulatorFrame frame = new GhostSimulatorFrame(manager);
		
		// instantiate tutor manager
		TutorManager.getInstance();
		
		frame.setVisible(true);
	}

}
