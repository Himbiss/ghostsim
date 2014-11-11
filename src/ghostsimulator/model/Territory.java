package ghostsimulator.model;

import ghostsimulator.model.BooHoo.Direction;
import ghostsimulator.model.Tile.Wall;

import java.awt.Point;


/**
 * This class represents a two dimensional territory of fixed size. It has one
 * {@link BooHoo} that can be controlled.
 * 
 * @author Vincent Ortland
 * 
 */
public class Territory{

	public static final int DEFAULT_COLUMN_CNT = 13;
	public static final int DEFAULT_ROW_CNT = 13;

	private Tile[][] territory;
	private BooHoo boohoo;

	private int columnCount, rowCount;

	/**
	 * This constructor creates the territory with a user-specified size
	 * 
	 * @param columnCount
	 * @param rowCount
	 */
	public Territory(int columnCount, int rowCount) {
		this.columnCount = columnCount;
		this.rowCount = rowCount;
		initialize();
	}

	/**
	 * This constructor creates the territory with a fixed size
	 */
	public Territory() {
		this.columnCount = DEFAULT_COLUMN_CNT;
		this.rowCount = DEFAULT_ROW_CNT;
		initialize();
	}
	
	/**
	 * Initializes the territory array
	 */
	private void initialize() {
		Point boohooPosition = new Point(3,2);
		boohoo = new BooHoo(this, boohooPosition);
		// create a new array of tiles and pad it with white walls as borders
		territory = new Tile[columnCount][rowCount];
		for(int row=0; row<rowCount; row++) {
			for(int column=0; column<columnCount; column++) {
				territory[column][row] = new Tile(column, row);
				if(column==0 || column==(columnCount-1) || row==0 || row==(rowCount-1))
					territory[column][row].setWall(Wall.WHITE_WALL);
			}
		}
		Tile tile = getTile(boohooPosition);
		tile.moveTo(boohoo);
	}
	
	/**
	 * Returns the tile at 'position'
	 * @param position
	 * @return tile
	 */
	protected Tile getTile(Point position) {
		return territory[position.x][position.y];
	}

	/**
	 * Animates the shot of a fireball from position 'position' towards 'direction'
	 * @param position
	 * @param direction
	 */
	public void shootFireball(Point position, Direction direction) {
		// advance the fireball until it hits something
		Point fireballPosition = new Point(position);
		Tile currentTile = getTile(position);
		while(currentTile.getWall() == Wall.NO_WALL) {
			fireballPosition = advancePosition(fireballPosition, direction);
			currentTile = getTile(fireballPosition);
		}
		// if the fireball hit a red wall, remove it otherwise do nothing
		if(currentTile.getWall() == Wall.RED_WALL)
			currentTile.setWall(Wall.NO_WALL);
	}
	
	/**
	 * Returns a new {@link Point} object advanced in the Direction 'direction'
	 * @param position
	 * @param direction
	 * @return advanced
	 */
	public static Point advancePosition(Point position, Direction direction) {
		int tmpX = position.x, tmpY = position.y;
		switch(direction) {
		case NORTH:
			tmpY--;
			break;
		case SOUTH:
			tmpY++;
			break;
		case EAST:
			tmpX++;
			break;
		case WEST:
			tmpX--;
			break;
		}
		return new Point(tmpX, tmpY);
	}
	
	public Tile[][] getTerritory() {
		return territory;
	}

	public BooHoo getBoohoo() {
		return boohoo;
	}
	
	public Point getBoohooPosition() {
		return boohoo.getPosition();
	}
	
	public Direction getBoohooDirection() {
		return boohoo.getDirection();
	}
	
	public int getBoohooNumFireballs() {
		return boohoo.getNumFireballs();
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}
}
