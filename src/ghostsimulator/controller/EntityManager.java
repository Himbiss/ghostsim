package ghostsimulator.controller;

import ghostsimulator.model.Territory;
import ghostsimulator.view.Editor;
import ghostsimulator.view.GhostSimulatorFrame;
import ghostsimulator.view.MenuBar;
import ghostsimulator.view.TerritoryPanel;
import ghostsimulator.view.ToolBar;

import javax.swing.JLabel;

/**
 * Manages all instances
 * @author Vincent Ortland
 */
public class EntityManager {

	private Territory territory;
	private Editor editor;
	private MenuBar menubar;
	private ToolBar toolbar;
	private GhostSimulatorFrame frame;
	private TerritoryPanel territoryPanel;
	private JLabel infoLabel;
	private EditorManager editorManager;
	private TerritoryManager terrManager ;
	private XMLSerializationController xmlSerializationController;
	private SimulationController simulationController;
	private static EntityManager instance;
	
	private EntityManager() {
	}
	
	public static EntityManager getInstance() {
		if(instance == null)
			return (instance = new EntityManager());
		return instance;
	}
	
	public EditorManager getEditorManager() {
		return editorManager;
	}
	public void setEditorManager(EditorManager editorManager) {
		this.editorManager = editorManager;
	}
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
	public void setTerritoryManager(TerritoryManager terrManager) {
		this.terrManager = terrManager;
	}
	
	public TerritoryManager getTerritoryManager() {
		return this.terrManager;
	}
	
	public XMLSerializationController getXmlSerializationController() {
		return xmlSerializationController;
	}
	public void setXmlSerializationController(XMLSerializationController xmlSerializationController) {
		this.xmlSerializationController = xmlSerializationController;
	}

	public SimulationController getSimulationController() {
		return simulationController;
	}

	public void setSimulationController(SimulationController simulationController) {
		this.simulationController = simulationController;
	}
	
}
