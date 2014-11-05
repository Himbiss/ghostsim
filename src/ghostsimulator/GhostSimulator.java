package ghostsimulator;

import territory.Territory;

public class GhostSimulator {

	public static boolean DEBUG_MODE = true;
	
	private static Editor editor;
	private static Territory territory;

	public static void main(String[] args) {
		editor = new Editor();
		territory = new Territory();
		
		GhostSimulatorFrame frame = new GhostSimulatorFrame(editor, territory);
		frame.setVisible(true);
	}

}
