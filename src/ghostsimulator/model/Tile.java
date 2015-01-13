package ghostsimulator.model;

import ghostsimulator.util.Resources;

import java.io.Serializable;


/**
 * Represents a Tile with a size and a number of attributes. 
 * E.g. the number of fireballs on it or if this tile represents a red or a white wall.
 * @author Vincent Ortland
 *
 */
public class Tile implements Serializable {

	private static final long serialVersionUID = 4767556145604905332L;

	public enum Wall { NO_WALL, RED_WALL, WHITE_WALL };
	private int space;
	private int columnIndex, rowIndex;
	private int numFireballs;
	private Wall wall;
	private transient BooHoo boohoo;
	
	/**
	 * Creates this tile with a x-index and a y-index
	 * @param column
	 * @param row
	 */
	public Tile(int column, int row) {
		this.columnIndex = column;
		this.rowIndex = row;
		this.wall = Wall.NO_WALL;
		this.space = 9;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}
	
	/**
	 * Returns true if the tile has some space left for the boohoo to put coins or fireballs
	 * @return hasSpace
	 */
	public boolean hasSpaceLeft() {
		return numFireballs < space;
	}
	
	/**
	 * Adds a fireball to this tile
	 * @throws NoSpaceOnTileException
	 */
	public void addFireball() throws NoSpaceOnTileException {
		if(!hasSpaceLeft())
			throw new NoSpaceOnTileException(this);
		numFireballs++;
	}
	
	/**
	 * Removes a fireball
	 */
	public void removeFireball() {
		numFireballs--;
	}
	
	/**
	 * Sets the number of fireballs
	 * @param fireballs
	 */
	public void setFireballs(int fireballs) {
		this.numFireballs = fireballs;
	}
	
	/**
	 * Returns true if there are fireballs on this tile
	 * @return hasFireballs
	 */
	public boolean hasFireballs() {
		return numFireballs > 0;
	}
	
	/**
	 * Sets the wall-state of this tile ( NONE, RED_WALL, WHITE_WALL )
	 * @param wall
	 */
	public void setWall(Wall wall) {
		this.wall = wall;
	}
	
	/**
	 * Returns the wall state of the current tile
	 * @return wall
	 */
	public Wall getWall() {
		return wall;
	}
	
	/**
	 * Returns true if this tile is a red or a white wall
	 * @return isWall
	 */
	public boolean isWall() {
		return (wall == Wall.RED_WALL) || (wall == Wall.WHITE_WALL);
	}

	/**
	 * The boohoo is currently on this tile
	 * @param booHoo
	 */
	public void moveTo(BooHoo booHoo) {
		this.boohoo = booHoo;
	}

	/**
	 * The boohoo leaves this tile
	 */
	public void leave() {
		this.boohoo = null;
	}
	
	public boolean hasBooHoo() {
		return boohoo != null;
	}
	
	public BooHoo getBooHoo() {
		return boohoo;
	}
	
	public int numFireballs() {
		return numFireballs;
	}
	
	@Override
	public String toString() {
		return Resources.getValue("tile")+"("+columnIndex+"|"+rowIndex+"): "+Resources.getValue("wall")+":"+wall+" "+Resources.getValue("fireballs")+":"+numFireballs+" BooHoo:"+(boohoo!=null);
	}
}
