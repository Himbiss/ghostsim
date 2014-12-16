package ghostsimulator.view;

import ghostsimulator.GhostManager;
import ghostsimulator.controller.EditorManager;
import ghostsimulator.controller.TerritoryManager;
import ghostsimulator.model.Territory;
import ghostsimulator.util.Resources;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;


/**
 * A {@link JFrame} that is the main frame of this program
 * It holds the {@link Editor} and the {@link Territory} as well
 * as the controls
 * 
 * @author Vincent Ortland
 */
public class GhostSimulatorFrame extends JFrame {

	private JLabel infoLabel;

	public GhostSimulatorFrame(GhostManager manager) {
		// setup frame
		setSize(1000, 800);
		setLayout(new BorderLayout()); 
		setTitle(Resources.getValue("title"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		Editor editor = new Editor(manager);
		EditorManager editorManager = new EditorManager(manager);
		
		// add menu bar
		MenuBar menuBar = new MenuBar(manager);
		setJMenuBar(menuBar);
		
		// add toolbar
		ToolBar toolBar = new ToolBar(manager);
		add(toolBar, BorderLayout.NORTH);
		
		// create the territory panel
		TerritoryPanel territoryPanel = new TerritoryPanel(manager);
		TerritoryManager terrManager = new TerritoryManager(manager);
		
		// create split pane and add editor and territory in scroll panes
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JScrollPane editorScrollPane = new JScrollPane(editor);
		JScrollPane territoryScrollPane = new JScrollPane(territoryPanel);
		splitPane.add(editorScrollPane);
		splitPane.add(territoryScrollPane);
		add(splitPane, BorderLayout.CENTER);
		
		// create and add info panel
		JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		infoLabel = new JLabel(Resources.getValue("welcome.message"));
		infoPanel.add(infoLabel);
		add(infoPanel, BorderLayout.SOUTH);
		
		// add view widgets to the manager
		manager.setEditor(editor);
		manager.setEditorManager(editorManager);
		manager.setMenubar(menuBar);
		manager.setToolbar(toolBar);
		manager.setInfoLabel(infoLabel);
		manager.setTerritoryPanel(territoryPanel);
		manager.setTerritoryManager(terrManager);
		
		// load default file into editor
		manager.getEditorManager().loadDefaultFile();
	}
	
	/**
	 * Shows a message in the info panel
	 * @param message
	 */
	public void showInfoMessage(String message) {
		infoLabel.setText(message);
	}

}
