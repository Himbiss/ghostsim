package ghostsimulator.model;

import ghostsimulator.GhostSimulator;
import ghostsimulator.util.Resources;

public class WallInFrontException extends RuntimeException {

	private static final long serialVersionUID = -7357675112214698961L;

	public WallInFrontException(Tile tile) {
		super(Resources.getValue("err.cannotmovetotile")+((GhostSimulator.DEBUG_MODE ? tile : "")));
	}

}
