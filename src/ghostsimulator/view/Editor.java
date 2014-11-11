package ghostsimulator.view;

import ghostsimulator.controller.AutocompleteAction;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;


/**
 * An editor in which the user can write code that can be executed
 * by the program
 * 
 * @author Vincent Ortland
 */
public class Editor extends JEditorPane {
	
	public Editor() {
		setPreferredSize(new Dimension(300, 0));
		// add autocompletion to the editor
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK), "autocomplete");
		getActionMap().put("autocomplete", new AutocompleteAction(this));
	}

	
}