package ghostsimulator.model;

import ghostsimulator.GhostSimulator;
import ghostsimulator.util.Resources;

public class WallInFrontException extends RuntimeException {

	public WallInFrontException(Tile tile) {
		super(Resources.getValue("err.cannotmovetotile")+((GhostSimulator.DEBUG_MODE ? tile : "")));
	}

}
