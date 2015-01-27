package ghostsimulator;

import ghostsimulator.controller.SerializationController;
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
		GhostManager manager = GhostManager.getInstance();
		manager.setTerritory(territory);

		SerializationController serializationController = new SerializationController(manager);
		manager.setSerializationController(serializationController);
		
		XMLSerializationController xmlSerializationController = new XMLSerializationController(manager);
		manager.setXmlSerializationController(xmlSerializationController);
		
		GhostSimulatorFrame frame = new GhostSimulatorFrame(manager);
		
		// instantiate tutor manager
		TutorManager.getInstance();
		
		frame.setVisible(true);
	}

}
