package ghostsimulator.view;

import ghostsimulator.model.BooHoo;
import ghostsimulator.util.Invisible;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.JPopupMenu;

public class MethodPopupMenu extends JPopupMenu {

	private BooHoo boohoo;
	
	public MethodPopupMenu(BooHoo boohoo) {
		super("Methoden");
		this.boohoo = boohoo;
		for(Method m :boohoo.getClass().getDeclaredMethods()) {
			if(!m.isAnnotationPresent(Invisible.class) && Modifier.isPublic(m.getModifiers()))
			add(new MethodMenuItem(m, boohoo, this));
		}
	}
}
