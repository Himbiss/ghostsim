package ghostsimulator.view;
import ghostsimulator.GhostManager;
import ghostsimulator.util.ImageLoader;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar {
	
	private GhostManager manager;

	public MenuBar(GhostManager manager) {
		this.manager = manager;
		// create and add menus
		JMenu editorMenu = new JMenu("Editor");
		JMenu territoryMenu = new JMenu("Territorium");
		JMenu simulationMenu = new JMenu("Simulation");
		add(editorMenu);
		add(territoryMenu);
		add(simulationMenu);
		
		// set mnemonics for menus
		editorMenu.setMnemonic(KeyEvent.VK_E);
		territoryMenu.setMnemonic(KeyEvent.VK_T);
		simulationMenu.setMnemonic(KeyEvent.VK_S);
		
		// create menu items for editor menu
		JMenuItem saveItem = new JMenuItem("Speichern");
		JMenuItem compileItem = new JMenuItem("Compilieren");
		JMenuItem exitItem = new JMenuItem("Beenden");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		editorMenu.add(saveItem);
		editorMenu.add(compileItem);
		editorMenu.addSeparator();
		editorMenu.add(exitItem);
		
		// set mnemonics and accelerators for editor menu items
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		compileItem.setMnemonic(KeyEvent.VK_C);
		compileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		exitItem.setMnemonic(KeyEvent.VK_B);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		// create and add menu items for territory menu
		JMenuItem saveTerritoryItem = new JMenuItem("Speichern");
		JMenuItem loadTerritoryItem = new JMenuItem("Laden");
		JMenuItem serializeTerritoryItem = new JMenuItem("Serialisieren");
		JMenuItem deserializeTerritoryItem = new JMenuItem("Deserialisieren");
		territoryMenu.add(saveTerritoryItem);
		territoryMenu.add(loadTerritoryItem);
		territoryMenu.add(serializeTerritoryItem);
		territoryMenu.add(deserializeTerritoryItem);
		
		// set mnemonics and accelerators for territory menu items
		saveTerritoryItem.setMnemonic(KeyEvent.VK_S);
		saveTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		loadTerritoryItem.setMnemonic(KeyEvent.VK_L);
		loadTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		serializeTerritoryItem.setMnemonic(KeyEvent.VK_E);
		serializeTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
		deserializeTerritoryItem.setMnemonic(KeyEvent.VK_D);
		deserializeTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
		
		
		// create and add menu items for simulation menu
		JMenuItem startSimulationItem = new JMenuItem("Start");
		JMenuItem pauseSimulationItem = new JMenuItem("Pausieren");
		JMenuItem stopSimulationItem = new JMenuItem("Stopp");
		simulationMenu.add(startSimulationItem);
		simulationMenu.add(pauseSimulationItem);
		simulationMenu.add(stopSimulationItem);
		
		// set mnemonics and accelerators for simulation menu items
		startSimulationItem.setMnemonic(KeyEvent.VK_S);
		startSimulationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		pauseSimulationItem.setMnemonic(KeyEvent.VK_P);
		pauseSimulationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		stopSimulationItem.setMnemonic(KeyEvent.VK_T);
		stopSimulationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		
		Cursor c = ImageLoader.getCursor("cursor.png");
		editorMenu.setCursor(c);
		simulationMenu.setCursor(c);
		territoryMenu.setCursor(c);
		setCursor(c);
	}

}
