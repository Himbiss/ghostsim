package ghostsimulator.controller;

import ghostsimulator.GhostManager;
import ghostsimulator.GhostSimulator;
import ghostsimulator.model.BooHoo;
import ghostsimulator.model.NoSpaceOnTileException;
import ghostsimulator.model.Tile;
import ghostsimulator.model.Tile.Wall;
import ghostsimulator.util.Invisible;
import ghostsimulator.view.MethodMenuItem;
import ghostsimulator.view.MethodPopupMenu;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 * Handels Mouse and Keboard Input for the TerritoryPanel
 * @author Vincent Ortland
 */
public class TerritoryInputListener implements MouseListener, MouseMotionListener, KeyListener {
	
	private GhostManager manager;
	private int startX, startY, curX, curY;
	private boolean isDragging;
	
	public TerritoryInputListener(GhostManager manager) {
		this.manager = manager;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Invoked if the user wants to delete something from a tile.
	 * First the wall is deleted and after that the fire balls, one at a time.
	 * @param tile
	 */
	private void doDelete(Tile tile) {
		if(tile.isWall())
			tile.setWall(Wall.NO_WALL);
		else if(tile.hasFireballs())
			tile.removeFireball();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		try {
			BooHoo boohoo = manager.getTerritory().getBoohoo();
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
		manager.getTerritoryPanel().repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Tile tile = manager.getTerritoryPanel().getTileByPosition(e.getPoint());
		if(tile != null) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				// check if the tile has the boohoo, then go into dragging mode
				if(tile.hasBooHoo()) {
					isDragging = true;
					startX = e.getX();
					startY = e.getY();
					return;
				}
				// otherwide apply territory action
				switch(manager.getToolbar().getSelectedTerritoryAction()) {
				case DELETE:
					doDelete(tile);
					break;
				case ADD_FIREBALL:
					try{
						tile.addFireball();
					} catch (NoSpaceOnTileException ex) {
						manager.getInfoLabel().setText(ex.getMessage());
					}
					break;
				case ADD_REDWALL:
					if(!tile.hasBooHoo())
						tile.setWall(Wall.RED_WALL);
					break;
				case ADD_WHITEWALL:
					if(!tile.hasBooHoo())
						tile.setWall(Wall.WHITE_WALL);
					break;
				}
			} else {
				if(tile.hasBooHoo()) {
					// show popup menu
					MethodPopupMenu menu = new MethodPopupMenu(manager.getTerritory().getBoohoo());
					menu.setLocation(e.getLocationOnScreen());
					menu.show(manager.getTerritoryPanel(), e.getX(), e.getY());
					return;
				} else {
					doDelete(tile);
				}
			}
		}
		manager.getTerritoryPanel().repaint();
		manager.getTerritoryPanel().requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Tile tile = manager.getTerritoryPanel().getTileByPosition(e.getPoint());
		Tile source = manager.getTerritoryPanel().getTileByPosition(new Point(startX, startY));
		if(tile != null && source != null) {
			if (SwingUtilities.isLeftMouseButton(e) && isDragging) {
				// check if the tile has a wall, otherwise drop the boohoo at this position
				if(!tile.isWall()) {
					source.leave();
					tile.moveTo(manager.getTerritory().getBoohoo());
					manager.getTerritory().setBooHooPosition(new Point(tile.getColumnIndex(),tile.getRowIndex()));
					isDragging = false;
				}
				
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	
}
