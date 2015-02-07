package ghostsimulator.view;

import ghostsimulator.controller.AudioController;
import ghostsimulator.controller.EntityManager;
import ghostsimulator.model.BooHoo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JMenuItem;

public class MethodMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = -492195097487666226L;
	Method m;
	BooHoo boohoo;
	EntityManager manager;
	
	public MethodMenuItem(Method m, BooHoo boohoo, EntityManager manager) {
		super(m.getName());
		addActionListener(this);
		this.m = m;
		this.boohoo = boohoo;
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			m.invoke(boohoo);
		} catch (IllegalAccessException e1) {
			System.err.println("Got IllegalAccessException: "+e1.getCause().getMessage());
			manager.getInfoLabel().setText(e1.getCause().getMessage());
			AudioController.playErrorSound();
		} catch (IllegalArgumentException e1) {
			System.err.println("Got IllegalArgumentException: "+e1.getCause().getMessage());
			manager.getInfoLabel().setText(e1.getCause().getMessage());
			AudioController.playErrorSound();
		} catch (InvocationTargetException e1) {
			System.err.println("Got RuntimeException: "+e1.getCause().getMessage());
			manager.getInfoLabel().setText(e1.getCause().getMessage());
			AudioController.playErrorSound();
		}
	}
}