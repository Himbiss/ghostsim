package ghostsimulator.view;

import ghostsimulator.GhostManager;
import ghostsimulator.controller.AutocompleteAction;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.KeyStroke;


/**
 * An editor in which the user can write code that can be executed
 * by the program
 * 
 * @author Vincent Ortland
 */
public class Editor extends JEditorPane {
	
	private static final long serialVersionUID = 2817101715134266739L;
	
	public Editor(GhostManager manager) {
		// add autocompletion to the editor
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK), "autocomplete");
		getActionMap().put("autocomplete", new AutocompleteAction(this));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}
}