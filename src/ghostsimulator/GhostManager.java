package ghostsimulator;

import ghostsimulator.controller.EditorManager;
import ghostsimulator.controller.SerializationController;
import ghostsimulator.controller.TerritoryManager;
import ghostsimulator.controller.XMLSerializationController;
import ghostsimulator.model.Territory;
import ghostsimulator.util.AudioLoader;
import ghostsimulator.view.Editor;
import ghostsimulator.view.GhostSimulatorFrame;
import ghostsimulator.view.MenuBar;
import ghostsimulator.view.TerritoryPanel;
import ghostsimulator.view.ToolBar;

import javax.swing.JLabel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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
	private EditorManager editorManager;
	private TerritoryManager terrManager ;
	private SerializationController serializationController;
	private XMLSerializationController xmlSerializationController;
	private static GhostManager instance;
	
	private GhostManager() {
	}
	
	public static GhostManager getInstance() {
		if(instance == null)
			return (instance = new GhostManager());
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

	public static void playErrorSound() {
		try {
			AudioStream audioStream = AudioLoader.getSound("error.wav");
			AudioPlayer.player.start(audioStream);
		} catch (Exception exc) {
		}
	}
	public SerializationController getSerializationController() {
		return serializationController;
	}
	public void setSerializationController(SerializationController serializationController) {
		this.serializationController = serializationController;
	}
	public XMLSerializationController getXmlSerializationController() {
		return xmlSerializationController;
	}
	public void setXmlSerializationController(XMLSerializationController xmlSerializationController) {
		this.xmlSerializationController = xmlSerializationController;
	}
	
}
