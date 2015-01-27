package ghostsimulator.model;

import ghostsimulator.GhostSimulator;
import ghostsimulator.util.Resources;

public class NoSpaceOnTileException extends RuntimeException {
	
	private static final long serialVersionUID = -3926042841280897170L;

	public NoSpaceOnTileException(Tile tile) {
		super(Resources.getValue("err.nospace")+(GhostSimulator.DEBUG_MODE ? tile : ""));
	}

}
