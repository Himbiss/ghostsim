package ghostsimulator.view;

import ghostsimulator.model.BooHoo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MethodMenuItem extends JMenuItem implements ActionListener {
	JPopupMenu parent;
	Method m;
	BooHoo boohoo;
	
	public MethodMenuItem(Method m, BooHoo boohoo, JPopupMenu parent) {
		super(m.getName());
		addActionListener(this);
		this.parent = parent;
		this.m = m;
		this.boohoo = boohoo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			m.invoke(boohoo);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}