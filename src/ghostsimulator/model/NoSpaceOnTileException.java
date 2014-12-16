package ghostsimulator.model;

import java.util.ResourceBundle;

import ghostsimulator.GhostSimulator;
import ghostsimulator.util.Resources;

public class NoSpaceOnTileException extends RuntimeException {
	
	public NoSpaceOnTileException(Tile tile) {
		super(Resources.getValue("err.nospace")+(GhostSimulator.DEBUG_MODE ? tile : ""));
	}

}
