package ghostsimulator.model;

import ghostsimulator.GhostSimulator;
import ghostsimulator.util.Resources;

public class NoFireballAtTileException extends RuntimeException {

	private static final long serialVersionUID = 2343796611011925289L;

	public NoFireballAtTileException(Tile tile) {
		super(Resources.getValue("err.nofireballattile")+((GhostSimulator.DEBUG_MODE ? tile : "")));
	}
}
