package ghostsimulator;

import javax.swing.JLabel;

import ghostsimulator.model.Territory;
import ghostsimulator.view.Editor;
import ghostsimulator.view.GhostSimulatorFrame;
import ghostsimulator.view.MenuBar;
import ghostsimulator.view.TerritoryPanel;
import ghostsimulator.view.ToolBar;

/**
 * Manages all instances
 * @author Vincent Ortland
 */
public class GhostManager {

	private Territory territory;
	private Editor editor;
	private MenuBar menubar;
	private ToolBar toolbar;
	private GhostSimulatorFrame frame;
	private TerritoryPanel territoryPanel;
	private JLabel infoLabel;
	
	public Territory getTerritory() {
		return territory;
	}
	public void setTerritory(Territory territory) {
		this.territory = territory;
	}
	public Editor getEditor() {
		return editor;
	}
	public void setEditor(Editor editor) {
		this.editor = editor;
	}
	public MenuBar getMenubar() {
		return menubar;
	}
	public void setMenubar(MenuBar menubar) {
		this.menubar = menubar;
	}
	public ToolBar getToolbar() {
		return toolbar;
	}
	public void setToolbar(ToolBar toolbar) {
		this.toolbar = toolbar;
	}
	public GhostSimulatorFrame getFrame() {
		return frame;
	}
	public void setFrame(GhostSimulatorFrame frame) {
		this.frame = frame;
	}
	public TerritoryPanel getTerritoryPanel() {
		return territoryPanel;
	}
	public void setTerritoryPanel(TerritoryPanel territoryPanel) {
		this.territoryPanel = territoryPanel;
	}
	public JLabel getInfoLabel() {
		return infoLabel;
	}
	public void setInfoLabel(JLabel infoLabel) {
		this.infoLabel = infoLabel;
	}
	
}