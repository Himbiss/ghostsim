package ghostsimulator.model;

import ghostsimulator.GhostSimulator;
import ghostsimulator.util.Resources;

public class NoFireballAtTileException extends RuntimeException {

	public NoFireballAtTileException(Tile tile) {
		super(Resources.getValue("err.nofireballattile")+((GhostSimulator.DEBUG_MODE ? tile : "")));
	}
}
