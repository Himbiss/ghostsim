package ghostsimulator.controller;


import ghostsimulator.util.ImageLoader;
import ghostsimulator.view.Editor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.BadLocationException;



public class AutocompleteAction implements Action {
	private Editor editor;
	private boolean isEnabled = true;
	// an array of function strings for autocompletion
	private String[] functions = {"takeFireball()",
								  "putDownFireball()",
								  "shootFireball()",
								  "hasFireballs()",
								  "fireballOnTile()",
								  "wallInFront()",
								  "turnLeft()",
								  "moveForward()"};
	private ActionListener menuListener;
	private JPopupMenu popup;
	
	public AutocompleteAction(final Editor editor) {
		this.editor = editor;
		this.popup = new JPopupMenu();
		this.menuListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				// append text to document
				String append = ev.getActionCommand();
				int caretPosition = editor.getCaretPosition();
				try {
					editor.getDocument().insertString(caretPosition, append, null);
				} catch (BadLocationException e) {
					System.err.println("Could not append autocompletion!");
					e.printStackTrace();
				}
			}
		};
		// add items to popup
		for(int i=0; i<functions.length; i++) {
			JMenuItem item = new JMenuItem(functions[i]);
			item.addActionListener(this);
			item.addActionListener(menuListener);
			popup.add(item);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("Action performed!");
		Point caretPosition = editor.getCaret().getMagicCaretPosition();
		popup.show(editor, caretPosition.x, caretPosition.y);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	}

	@Override
	public Object getValue(String key) {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void putValue(String key, Object value) {
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	}

	@Override
	public void setEnabled(boolean b) {
		isEnabled = b;
	}
}