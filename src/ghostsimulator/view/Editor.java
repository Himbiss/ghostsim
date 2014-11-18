package ghostsimulator.view;

import ghostsimulator.GhostManager;
import ghostsimulator.controller.AutocompleteAction;
import ghostsimulator.controller.EditorManager;
import ghostsimulator.model.BooHoo;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.KeyStroke;


/**
 * An editor in which the user can write code that can be executed
 * by the program
 * 
 * @author Vincent Ortland
 */
public class Editor extends JEditorPane {
	
	private GhostManager manager;
	
	public Editor(GhostManager manager) {
		this.manager = manager;
		// add autocompletion to the editor
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK), "autocomplete");
		getActionMap().put("autocomplete", new AutocompleteAction(this));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 0);
	}

	
}