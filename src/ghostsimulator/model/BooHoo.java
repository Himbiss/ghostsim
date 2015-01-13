package ghostsimulator.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Observable;


/**
 * This is the actor class. It represents the character BooHoo from SuperMario.
 * @author Vincent Ortland
 *
 */
public class BooHoo extends Observable implements Serializable {

	private static final long serialVersionUID = -2678258386876029301L;

	/**
	 * The direction in which the boohoo is facing
	 */
	public enum Direction {
		NORTH, SOUTH, EAST, WEST;
		/**
		 * Returns the direction counter-clockwise
		 * @param direction
		 * @return leftDirection
		 */
	    public static Direction getTurnLeft(Direction direction) {
	    	switch(direction) {
			case EAST:
				return Direction.NORTH;
			case WEST:
				return Direction.SOUTH;
			case NORTH:
				return Direction.WEST;
			case SOUTH:
				return Direction.EAST;
			default:
				return Direction.NORTH;
			}
	    }
	};
	
	private Direction direction;
	private int numFireballs;
	private Territory territory;
	private Point position;
	
	BooHoo(Territory territory, Point position) {
		this.territory = territory;
		this.position = new Point(position);
		// set the direction automatically to East
		direction = Direction.EAST;
		numFireballs = 3;
	}
	
	public BooHoo() {
		// set the direction automatically to East
		direction = Direction.EAST;
		numFireballs = 3;
	}
	
	/**
	 * Returns the position of the boohooo
	 * @return position
	 */
	Point getPosition() {
		return position;
	}

	void setTerritory(Territory territory) {
		this.territory = territory;
	}
	
	/**
	 * Sets the position of the boohoo
	 * @param position
	 */
	void setPosition(Point position) {
		this.position = position;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Returns the Direction in which the boohoo is facing
	 * @return direction
	 */
	Direction getDirection() {
		return direction;
	}
	
	/**
	 * Returns the number of fire balls of the boohoo
	 * @return
	 */
	int getNumFireballs() {
		return numFireballs;
	}
	
	/**
	 * Returns the position in fron of the BooHoo
	 * @return position
	 */
	private Point getPositionInFront() {
		return Territory.advancePosition(position, direction);
	}
	
	/**
	 * Takes a fireball from the position the boohoo is currently at
	 * @throws NoFireballAtTileException
	 */
	public void takeFireball() throws NoFireballAtTileException {
		Tile tile = territory.getTile(position);
		if(!tile.hasFireballs())
			throw new NoFireballAtTileException(tile);
		tile.removeFireball();
		numFireballs++;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Puts down a fireball on the field below, throws {@link HasNoFireballException} if the boohoo has no fireballs
	 * @throws HasNoFireballException
	 */
	public void putDownFireball() throws HasNoFireballException {
		if(numFireballs <= 0)
			throw new HasNoFireballException();
		Tile tile = territory.getTile(position);
		tile.addFireball();
		numFireballs--;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Returns true if a wall is in front of boo
	 * @return wallInFront
	 */
	public boolean wallInFront() {
		Tile frontTile = territory.getTile(getPositionInFront());
		return frontTile.isWall();
	}
	
	/**
	 * Returns true if one or more fire balls are on this tile
	 * @return fireballsOnTile
	 */
	public boolean fireballOnTile() {
		Tile currentTile = territory.getTile(position);
		return currentTile.hasFireballs();
	}
	
	/**
	 * Returns true if the boo has fireballs
	 * return hasFireballs
	 */
	public boolean hasFireballs() {
		return numFireballs > 0;
	}
	
	/**
	 * Turns the boohoo left
	 */
	public void turnLeft() {
		direction = Direction.getTurnLeft(direction);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Advances the boohoo in the current direction.
	 * Throws a WallInFrontException if the boohoo cannot move because a wall is in the way.
	 * @throws WallInFrontException
	 */
	public void moveForward() throws WallInFrontException {
		Tile currTile = territory.getTile(position);
		Tile frontTile = territory.getTile(getPositionInFront());
		
		if(frontTile != null) {
			if(frontTile.isWall())
				throw new WallInFrontException(frontTile);
			currTile.leave();
			position = getPositionInFront();
			frontTile.moveTo(this);
			setChanged();
			notifyObservers();
		}
	}
	
	public void shootFireball() throws HasNoFireballException {
		if(numFireballs <= 0)
			throw new HasNoFireballException();
		numFireballs--;
		territory.shootFireball(position, direction);
		setChanged();
		notifyObservers();
	}
}
