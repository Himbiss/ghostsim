package ghostsimulator.model;

import ghostsimulator.util.Resources;

public class HasNoFireballException extends RuntimeException {

	private static final long serialVersionUID = 420363500886908777L;

	public HasNoFireballException() {
		super(Resources.getValue("err.nofireballs"));
	}

}
