package ghostsimulator.view;

import ghostsimulator.GhostManager;
import ghostsimulator.model.BooHoo;
import ghostsimulator.util.Invisible;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.JPopupMenu;

public class MethodPopupMenu extends JPopupMenu {
	
	public MethodPopupMenu(GhostManager manager) {
		super("Methoden");
		BooHoo boohoo = manager.getTerritory().getBoohoo();
		for(Method m :boohoo.getClass().getDeclaredMethods()) {
			int modifiers = m.getModifiers();
			if(!m.isAnnotationPresent(Invisible.class) && Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers) && !Modifier.isPrivate(modifiers))
				add(new MethodMenuItem(m, boohoo, manager));
		}
		// add the superclass methods if the boohoo inherits from the class BooHoo
		if(boohoo.getClass().getSuperclass().equals(BooHoo.class)) {
			for(Method m :boohoo.getClass().getSuperclass().getDeclaredMethods()) {
				int modifiers = m.getModifiers();
				if(!m.isAnnotationPresent(Invisible.class) && Modifier.isPublic(modifiers) && !Modifier.isAbstract(modifiers) && !Modifier.isPrivate(modifiers))
					add(new MethodMenuItem(m, manager.getTerritory().getBoohoo(), manager));
			}
		}
	}
}
