package ghostsimulator.view;
import ghostsimulator.GhostManager;
import ghostsimulator.util.ImageLoader;
import ghostsimulator.util.Resources;

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
	public JMenuItem serializeTerritoryItem;
	public JMenuItem deserializeTerritoryItem;

	public MenuBar(GhostManager manager) {
		this.manager = manager;
		// create and add menus
		JMenu editorMenu = new JMenu(Resources.getValue("menu.editor.txt"));
		JMenu territoryMenu = new JMenu(Resources.getValue("menu.territory.txt"));
		JMenu simulationMenu = new JMenu(Resources.getValue("menu.simulation.txt"));
		add(editorMenu);
		add(territoryMenu);
		add(simulationMenu);
		
		// set mnemonics for menus
		editorMenu.setMnemonic(Resources.getMnemonic("menu.editor.txt"));
		territoryMenu.setMnemonic(Resources.getMnemonic("menu.territory.txt"));
		simulationMenu.setMnemonic(Resources.getMnemonic("menu.simulation.txt"));
		
		// create menu items for editor menu
		JMenuItem saveItem = new JMenuItem(Resources.getValue("menu.editor.item.save"));
		JMenuItem compileItem = new JMenuItem(Resources.getValue("menu.editor.item.compile"));
		JMenuItem exitItem = new JMenuItem(Resources.getValue("menu.editor.item.exit"));
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
		saveItem.setMnemonic(Resources.getMnemonic("menu.editor.item.save.mnemonic"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		compileItem.setMnemonic(Resources.getMnemonic("menu.editor.item.compile.mnemonic"));
		compileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		exitItem.setMnemonic(Resources.getMnemonic("menu.editor.item.exit.mnemonic"));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		// create and add menu items for territory menu
		JMenuItem saveTerritoryItem = new JMenuItem(Resources.getValue("menu.territory.item.save"));
		JMenuItem loadTerritoryItem = new JMenuItem(Resources.getValue("menu.territory.item.load"));
		serializeTerritoryItem = new JMenuItem(Resources.getValue("menu.territory.item.serialize"));
		deserializeTerritoryItem = new JMenuItem(Resources.getValue("menu.territory.item.deserialize"));
		territoryMenu.add(saveTerritoryItem);
		territoryMenu.add(loadTerritoryItem);
		territoryMenu.add(serializeTerritoryItem);
		territoryMenu.add(deserializeTerritoryItem);
		
		// set mnemonics and accelerators for territory menu items
		saveTerritoryItem.setMnemonic(Resources.getMnemonic("menu.territory.item.save.mnemonic"));
		saveTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		loadTerritoryItem.setMnemonic(Resources.getMnemonic("menu.territory.item.load.mnemonic"));
		loadTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		serializeTerritoryItem.setMnemonic(Resources.getMnemonic("menu.territory.item.serialize.mnemonic"));
		serializeTerritoryItem.addActionListener(manager.getSerializationController());
		serializeTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
		deserializeTerritoryItem.setMnemonic(Resources.getMnemonic("menu.territory.item.deserialize.mnemonic"));
		deserializeTerritoryItem.addActionListener(manager.getSerializationController());
		deserializeTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK + ActionEvent.SHIFT_MASK));
		
		
		// create and add menu items for simulation menu
		JMenuItem startSimulationItem = new JMenuItem(Resources.getValue("menu.simulation.item.start"));
		JMenuItem pauseSimulationItem = new JMenuItem(Resources.getValue("menu.simulation.item.pause"));
		JMenuItem stopSimulationItem = new JMenuItem(Resources.getValue("menu.simulation.item.stop"));
		simulationMenu.add(startSimulationItem);
		simulationMenu.add(pauseSimulationItem);
		simulationMenu.add(stopSimulationItem);
		
		// set mnemonics and accelerators for simulation menu items
		startSimulationItem.setMnemonic(Resources.getMnemonic("menu.simulation.item.start.mnemonic"));
		startSimulationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		pauseSimulationItem.setMnemonic(Resources.getMnemonic("menu.simulation.item.pause.mnemonic"));
		pauseSimulationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		stopSimulationItem.setMnemonic(Resources.getMnemonic("menu.simulation.item.stop.mnemonic"));
		stopSimulationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		
		Cursor c = ImageLoader.getCursor("cursor.png");
		editorMenu.setCursor(c);
		simulationMenu.setCursor(c);
		territoryMenu.setCursor(c);
		setCursor(c);
	}

}
