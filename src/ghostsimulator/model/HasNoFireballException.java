package ghostsimulator.model;

import ghostsimulator.util.Resources;

public class HasNoFireballException extends RuntimeException {

	public HasNoFireballException() {
		super(Resources.getValue("err.nofireballs"));
	}

}
