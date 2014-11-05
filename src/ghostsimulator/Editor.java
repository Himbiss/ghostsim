package ghostsimulator;

import java.awt.Dimension;

import javax.swing.JEditorPane;

/**
 * An editor in which the user can write code that can be executed
 * by the program
 * 
 * @author Vincent Ortland
 */
public class Editor extends JEditorPane {
	
	public Editor() {
		setPreferredSize(new Dimension(300, 0));
	}
	
}
