package ghostsimulator.view;
import ghostsimulator.GhostManager;
import ghostsimulator.controller.DBLoadListener;
import ghostsimulator.controller.DBStoreListener;
import ghostsimulator.controller.tutor.AnswerRequestListener;
import ghostsimulator.controller.tutor.GetAnswerListener;
import ghostsimulator.controller.tutor.GetRequestListener;
import ghostsimulator.controller.tutor.SendRequestListener;
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
	
	private static final long serialVersionUID = -1821774551044395067L;
	public JMenuItem serializeTerritoryItem;
	public JMenuItem deserializeTerritoryItem;
	public JMenuItem saveTerritoryItem;
	public JMenuItem subLoadTerritorySAX;
	public JMenuItem subLoadTerritoryDOM;
	public JMenuItem subLoadTerritoryStAXCursor;
	public JMenuItem subLoadTerritoryStAXIterator;
	public JMenuItem sendAnswerItem;
	public JMenuItem getRequestItem;
	public JMenuItem getAnswerItem;
	public JMenuItem sendRequestItem;

	public MenuBar(GhostManager manager) {
		// create and add menus
		JMenu editorMenu = new JMenu(Resources.getValue("menu.editor.txt"));
		JMenu territoryMenu = new JMenu(Resources.getValue("menu.territory.txt"));
		JMenu simulationMenu = new JMenu(Resources.getValue("menu.simulation.txt"));
		JMenu exampleMenu = new JMenu(Resources.getValue("menu.examples.txt"));
		JMenu tutorMenu = new JMenu(Resources.getValue("menu.tutor.txt"));
		add(editorMenu);
		add(territoryMenu);
		add(simulationMenu);
		add(exampleMenu);
		add(tutorMenu);
		
		// set mnemonics for menus
		editorMenu.setMnemonic(Resources.getMnemonic("menu.editor.txt"));
		territoryMenu.setMnemonic(Resources.getMnemonic("menu.territory.txt"));
		simulationMenu.setMnemonic(Resources.getMnemonic("menu.simulation.txt"));
		exampleMenu.setMnemonic(Resources.getMnemonic("menu.examples.txt"));
		tutorMenu.setMnemonic(Resources.getMnemonic("menu.tutor.txt"));
		
		// create menu items for tutor menu depending on the role (tutor or student)
		if(Resources.getSystemProperty("role").equalsIgnoreCase("tutor")) {
			sendAnswerItem = new JMenuItem(Resources.getValue("menu.tutor.item.sendanswer"));
			sendAnswerItem.addActionListener(new AnswerRequestListener());
			sendAnswerItem.setEnabled(false);
			getRequestItem = new JMenuItem(Resources.getValue("menu.tutor.item.getrequest"));
			getRequestItem.addActionListener(new GetRequestListener());
			getRequestItem.setEnabled(true);
			tutorMenu.add(sendAnswerItem);
			tutorMenu.add(getRequestItem);
		} else {
			getAnswerItem = new JMenuItem(Resources.getValue("menu.tutor.item.getanswer"));
			getAnswerItem.addActionListener(new GetAnswerListener());
			getAnswerItem.setEnabled(false);
			sendRequestItem = new JMenuItem(Resources.getValue("menu.tutor.item.sendrequest"));
			sendRequestItem.addActionListener(new SendRequestListener());
			sendRequestItem.setEnabled(true);
			tutorMenu.add(getAnswerItem);
			tutorMenu.add(sendRequestItem);
		}
		
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
		
		saveTerritoryItem = new JMenuItem(Resources.getValue("menu.territory.item.save"));
		serializeTerritoryItem = new JMenuItem(Resources.getValue("menu.territory.item.serialize"));
		deserializeTerritoryItem = new JMenuItem(Resources.getValue("menu.territory.item.deserialize"));
		
		// add submenu to territory menu item 'loadTerritoryItem
		JMenu subLoadTerritoryMenu = new JMenu(Resources.getValue("menu.territory.item.load"));
		subLoadTerritoryMenu.setMnemonic(Resources.getMnemonic("menu.territory.item.load.mnemonic"));
		subLoadTerritorySAX = new JMenuItem(Resources.getValue("menu.territory.item.loadsax"));
		subLoadTerritoryDOM = new JMenuItem(Resources.getValue("menu.territory.item.loaddom"));
		subLoadTerritoryStAXCursor = new JMenuItem(Resources.getValue("menu.territory.item.loadstaxcur"));
		subLoadTerritoryStAXIterator = new JMenuItem(Resources.getValue("menu.territory.item.loadstaxit"));
		subLoadTerritorySAX.addActionListener(manager.getXmlSerializationController());
		subLoadTerritoryDOM.addActionListener(manager.getXmlSerializationController());
		subLoadTerritoryStAXCursor.addActionListener(manager.getXmlSerializationController());
		subLoadTerritoryStAXIterator.addActionListener(manager.getXmlSerializationController());
		
		subLoadTerritoryMenu.add(subLoadTerritorySAX);
		subLoadTerritoryMenu.add(subLoadTerritoryDOM);
		subLoadTerritoryMenu.add(subLoadTerritoryStAXCursor);
		subLoadTerritoryMenu.add(subLoadTerritoryStAXIterator);

		territoryMenu.add(saveTerritoryItem);
		territoryMenu.add(subLoadTerritoryMenu);
		territoryMenu.add(serializeTerritoryItem);
		territoryMenu.add(deserializeTerritoryItem);
				
		// set mnemonics and accelerators for territory menu items
		saveTerritoryItem.setMnemonic(Resources.getMnemonic("menu.territory.item.save.mnemonic"));
		saveTerritoryItem.addActionListener(manager.getXmlSerializationController());
		saveTerritoryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
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
		
		// create and add menu items for examples menu
		JMenuItem loadExampleItem = new JMenuItem(Resources.getValue("menu.examples.item.load"));
		loadExampleItem.setMnemonic(Resources.getMnemonic("menu.examples.item.load.mnemonic"));
		loadExampleItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		loadExampleItem.addActionListener(new DBLoadListener());
		JMenuItem saveExampleItem = new JMenuItem(Resources.getValue("menu.examples.item.save"));
		saveExampleItem.setMnemonic(Resources.getMnemonic("menu.examples.item.save.mnemonic"));
		saveExampleItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		saveExampleItem.addActionListener(new DBStoreListener());
		exampleMenu.add(loadExampleItem);
		exampleMenu.add(saveExampleItem);
		
		Cursor c = ImageLoader.getCursor("cursor.png");
		editorMenu.setCursor(c);
		simulationMenu.setCursor(c);
		territoryMenu.setCursor(c);
		exampleMenu.setCursor(c);
		setCursor(c);
	}

}
