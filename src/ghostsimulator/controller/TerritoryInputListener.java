package ghostsimulator.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import ghostsimulator.GhostManager;
import ghostsimulator.GhostSimulator;
import ghostsimulator.model.BooHoo;
import ghostsimulator.model.NoSpaceOnTileException;
import ghostsimulator.model.Tile;

/**
 * Handels Mouse and Keboard Input for the TerritoryPanel
 * @author Vincent Ortland
 */
public class TerritoryInputListener implements MouseListener, KeyListener {
	
	private GhostManager manager;
	
	public TerritoryInputListener(GhostManager manager) {
		this.manager = manager;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Tile tile = manager.getTerritoryPanel().getTileByPosition(e.getPoint());
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

		manager.getTerritoryPanel().repaint();
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
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}
