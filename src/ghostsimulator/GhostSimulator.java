package ghostsimulator;

import ghostsimulator.controller.SerializationController;
import ghostsimulator.model.Territory;
import ghostsimulator.view.Editor;
import ghostsimulator.view.GhostSimulatorFrame;

public class GhostSimulator {

	public static boolean DEBUG_MODE = true;
	
	private static Editor editor;
	private static Territory territory;

	public static void main(String[] args) {
		// create the model
		territory = new Territory();
		
		GhostManager manager = new GhostManager();
		manager.setTerritory(territory);

		SerializationController serializationController = new SerializationController(manager);
		manager.setSerializationController(serializationController);
		
		GhostSimulatorFrame frame = new GhostSimulatorFrame(manager);
		frame.setVisible(true);
	}

}
