package ghostsimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Transient;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import territory.BooHoo;
import territory.NoSpaceOnTileException;
import territory.Territory;
import territory.Tile;
import util.ImageLoader;

public class TerritoryPanel extends JPanel implements MouseListener,
		KeyListener {

	public static int TILE_SIZE = 51;

	private Territory territory;
	private Tile[][] tiles;
	private int columnCount;
	private int rowCount;
	private int offsetX, offsetY = TILE_SIZE;
	private Font statusFont;

	private Image boohooNorthImage;
	private Image boohooEastImage;
	private Image boohooSouthImage;
	private Image boohooWestImage;
	private Image fireballImage;
	private Image redWallImage;
	private Image whiteWallImage;
	private Image sandTileImage;
	private Image fireballAnimatedImage;
	

	public TerritoryPanel(Territory territory) {
		addMouseListener(this);
		this.territory = territory;
		tiles = territory.getTerritory();
		columnCount = territory.getColumnCount();
		rowCount = territory.getRowCount();
		statusFont = new Font("Arial", Font.BOLD, 12);
		loadImages();
		addKeyListener(this);
	}

	/**
	 * Loads all Images needed to draw the Territory
	 */
	private void loadImages() {
		sandTileImage = ImageLoader.getImage("Sand_Tile.gif");
		boohooNorthImage = ImageLoader.getImage("boohoo_north_animated.gif");
		boohooEastImage = ImageLoader.getImage("boohoo_east_animated.gif");
		boohooSouthImage = ImageLoader.getImage("boohoo_south_animated.gif");
		boohooWestImage = ImageLoader.getImage("boohoo_west_animated.gif");
		fireballImage = ImageLoader.getImage("fireball.png");
		fireballAnimatedImage = ImageLoader
				.getImage("mario_fireball_animated.gif");
		redWallImage = ImageLoader.getImage("red_wall.png");
		whiteWallImage = ImageLoader.getImage("white_wall.png");
	}

	/**
	 * Translates the position of a point in the JPanel to a Tile. Returns null
	 * if no tile was found.
	 * 
	 * @return tile
	 */
	private Tile getTileByPosition(Point pos) {
		int cntColumn = (pos.x - offsetX) / TILE_SIZE;
		int cntRow = (pos.y - offsetY) / TILE_SIZE;
		if (cntColumn < columnCount && cntRow < rowCount && cntColumn >= 0
				&& cntRow >= 0)
			return tiles[cntColumn][cntRow];
		return null;
	}

	/**
	 * Paints the territory
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		offsetX = (getWidth() - (columnCount * TILE_SIZE)) / 2;
		offsetY = (getHeight() - (rowCount * TILE_SIZE)) / 2;

		Graphics2D g2d = (Graphics2D) g;

		if (g2d != null) {
			
			// draw the stats
			drawStats(g2d);

			// draw the background
			drawBackground(g2d);
			
			// draw boohoo
			int startX = offsetX + territory.getBoohooPosition().x * TILE_SIZE;
			int startY = offsetY + territory.getBoohooPosition().y * TILE_SIZE;
			
			Image boohooImg = boohooEastImage;
			switch (territory.getBoohooDirection()) {
			case NORTH:
				boohooImg = boohooNorthImage;
				break;
			case WEST:
				boohooImg = boohooWestImage;
				break;
			case EAST:
				boohooImg = boohooEastImage;
				break;
			case SOUTH:
				boohooImg = boohooSouthImage;
				break;
			}
			g2d.drawImage(boohooImg, startX, startY, TILE_SIZE, TILE_SIZE, this);

			// iterate through the tiles and draw them
			for (int row = 0; row < rowCount; row++) {
				for (int column = 0; column < columnCount; column++) {
					Tile tile = tiles[column][row];
					drawTile(tile, g2d);
				}
			}

			// draw debug information like the front tile of the boohoo
			if (GhostSimulator.DEBUG_MODE) {
				Point frontPosition = Territory.advancePosition(
						territory.getBoohooPosition(),
						territory.getBoohooDirection());
				Tile frontTile = territory.getTerritory()[frontPosition.x][frontPosition.y];
				int startXTile = offsetX + frontTile.getColumnIndex()
						* TILE_SIZE;
				int startYTile = offsetY + frontTile.getRowIndex() * TILE_SIZE;
				if (frontTile.isWall()) {
					g2d.setColor(new Color(100, 0, 0, 120));
				} else {
					g2d.setColor(new Color(0, 100, 0, 120));
				}

				g2d.fillRect(startXTile, startYTile, TILE_SIZE, TILE_SIZE);
			}

			// draw the grid
			drawGrid(g2d);
			
			g2d.dispose();
		}
	}
	
	/**
	 * Draws the background images for the tiles
	 * @param g2d
	 */
	private void drawBackground(Graphics2D g2d) {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				int startX = offsetX + column * TILE_SIZE;
				int startY = offsetY + row * TILE_SIZE;
				//draw background image
				g2d.drawImage(sandTileImage, startX, startY, TILE_SIZE, TILE_SIZE, null);
			}
		}
	}
	
	/**
	 * Draws the status of the boo, for example how many fireballs it has
	 * @param g2d
	 */
	private void drawStats(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(statusFont);
		String str = "Fireballs: ";
		FontMetrics metrics = g2d.getFontMetrics(statusFont);
		int startX = offsetX;
		int startY = offsetY - metrics.getHeight() - 5;
		int stringWidth = metrics.stringWidth(str);
		int fireballWidth = fireballImage.getWidth(this);
		g2d.drawString("Fireballs:", startX, startY);
		for(int x=startX+stringWidth; x<territory.getBoohooNumFireballs()*fireballWidth+startX+stringWidth; x+=fireballWidth) {
			g2d.drawImage(fireballImage, x, startY-metrics.getHeight(), this);
		}
	}

	/**
	 * Draws the grid
	 * 
	 * @param g2d
	 */
	private void drawGrid(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		int advanceX = columnCount * TILE_SIZE;
		int advanceY = rowCount * TILE_SIZE;

		for (int i = 0; i < columnCount + 1; i++) {
			int startX = offsetX + i * TILE_SIZE;
			int endX = startX;
			int startY = offsetY;
			int endY = offsetY + advanceY;

			g2d.drawLine(startX, startY, endX, endY);
		}

		for (int j = 0; j < rowCount + 1; j++) {
			int startX = offsetX;
			int endX = startX + advanceX;
			int startY = offsetY + j * TILE_SIZE;
			int endY = startY;
			g2d.drawLine(startX, startY, endX, endY);
		}
	}

	/**
	 * This method draws a Tile with a Graphics2D Context
	 * 
	 * @param tile
	 * @param g2d
	 */
	private void drawTile(Tile tile, Graphics2D g2d) {
		int startX = offsetX + tile.getColumnIndex() * TILE_SIZE;
		int startY = offsetY + tile.getRowIndex() * TILE_SIZE;
		
		// draw wall if present
		switch (tile.getWall()) {
		case RED_WALL:
			g2d.drawImage(redWallImage, startX, startY, TILE_SIZE, TILE_SIZE,
					null);
			break;
		case WHITE_WALL:
			g2d.drawImage(whiteWallImage, startX, startY, TILE_SIZE, TILE_SIZE,
					null);
			break;
		default:
			// no wall, draw fireballs
			if (tile.hasFireballs()) {
				int objectsPerRow = 3, fireballRows = 3;
				int objectWidth = TILE_SIZE / objectsPerRow; // draw three
																// objects per
																// row
				int objectHeight = TILE_SIZE / fireballRows; // draw three rows

				int fireballCounter = tile.numFireballs();

				for (int row = fireballRows-1; row >= 0; row--) {
					for (int f = 0; f < objectsPerRow; f++) {
						int fX = startX + f * objectWidth;
						int fY = startY + row * objectHeight;
						if (fireballCounter > 0) {
							g2d.drawImage(fireballImage, fX, fY, objectWidth,
									objectHeight, null);
							fireballCounter--;
						}
					}
				}
			}
			break;
		}

		// draw debug information
		if (GhostSimulator.DEBUG_MODE) {
			g2d.setColor(Color.RED);
			String str = "(" + tile.getColumnIndex() + "|" + tile.getRowIndex()+ ")";
			int posX = startX + (TILE_SIZE / 2);
			int posY = startY + (TILE_SIZE / 2);
			FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
			posX -= metrics.stringWidth(str)/2;
			posY += metrics.getHeight()/2;
			g2d.drawString(str, posX, posY);
		}
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension((columnCount + 2) * TILE_SIZE, (rowCount + 2)
				* TILE_SIZE);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Tile tile = getTileByPosition(e.getPoint());
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (tile != null) {
				try{
					tile.addFireball();
				} catch (NoSpaceOnTileException ex) {
					System.err.println(ex.getMessage());
				}
			}
		} else {
			tile.removeFireball();
		}

		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		try {
			BooHoo boohoo = territory.getBoohoo();
			switch (keyCode) {
			case KeyEvent.VK_T:
				boohoo.takeFireball();
				break;
			case KeyEvent.VK_P:
				boohoo.putDownFireball();
				break;
			case KeyEvent.VK_UP:
				boohoo.moveForward();
				break;
			case KeyEvent.VK_SPACE:
				boohoo.shootFireball();
				break;
			case KeyEvent.VK_LEFT:
				boohoo.turnLeft();
				break;
			case KeyEvent.VK_RIGHT:
				boohoo.turnLeft();
				boohoo.turnLeft();
				boohoo.turnLeft();
				break;
			case KeyEvent.VK_D:
				GhostSimulator.DEBUG_MODE = !GhostSimulator.DEBUG_MODE;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
