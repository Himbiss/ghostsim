package territory;

import ghostsimulator.GhostSimulator;

public class NoFireballAtTileException extends RuntimeException {

	public NoFireballAtTileException(Tile tile) {
		super("There is no fireball at this tile! "+((GhostSimulator.DEBUG_MODE ? tile : "")));
	}
}
