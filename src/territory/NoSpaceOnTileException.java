package territory;

import ghostsimulator.GhostSimulator;

public class NoSpaceOnTileException extends RuntimeException {
	
	public NoSpaceOnTileException(Tile tile) {
		super("Cannot put object down on this tile because there is no space left! "+(GhostSimulator.DEBUG_MODE ? tile : ""));
	}

}
