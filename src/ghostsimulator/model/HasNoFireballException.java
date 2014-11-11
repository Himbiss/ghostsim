package ghostsimulator.model;

public class HasNoFireballException extends RuntimeException {

	public HasNoFireballException() {
		super("Cannot shoot fireball because I have none!");
	}

}