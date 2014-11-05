package territory;

import ghostsimulator.GhostSimulator;

public class WallInFrontException extends RuntimeException {

	public WallInFrontException(Tile tile) {
		super("I cannot move to this tile! "+((GhostSimulator.DEBUG_MODE ? tile : "")));
	}

}
