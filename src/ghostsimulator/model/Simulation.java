package ghostsimulator.model;

import ghostsimulator.GhostManager;
import ghostsimulator.controller.SliderListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class Simulation extends Thread implements Observer {

	private BooHoo boo;
	private GhostManager manager;
	
	private volatile boolean needToPause = false;

	public Simulation(GhostManager manager) {
		this.boo = manager.getTerritory().getBoohoo();
		this.manager = manager;
	}
	
	public synchronized boolean isPaused() {
		return needToPause;
	}
	
	private synchronized void pausePoint() throws InterruptedException {
		while (needToPause) {
			manager.getToolbar().getBtnSimPause().setEnabled(false);
			manager.getToolbar().getBtnSimStart().setEnabled(true);
			manager.getToolbar().getBtnSimStop().setEnabled(true);
			manager.getInfoLabel().setText("Simulation pausiert");
            wait();
        }
	}
	
	public synchronized void pause() {
        needToPause = true;
    }

    public synchronized void unpause() {
        needToPause = false;
        this.notifyAll();
    }

	@Override
	public void run() {
		manager.getToolbar().getBtnSimPause().setEnabled(true);
		manager.getToolbar().getBtnSimStart().setEnabled(false);
		manager.getToolbar().getBtnSimStop().setEnabled(true);
		boo.addObserver(this);
		try {
			Class<?>[] noparams = {};
			Method method = boo.getClass().getMethod("main", noparams);
			method.invoke(boo);
		} catch (NoSuchMethodException e) {
			manager.getInfoLabel().setText("Keine main-Methode gefunden, vielleicht neu compilieren?");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			manager.getInfoLabel().setText("Simulation gestoppt!");
		}
		boo.deleteObserver(this);
		manager.getToolbar().getBtnSimPause().setEnabled(false);
		manager.getToolbar().getBtnSimStart().setEnabled(true);
		manager.getToolbar().getBtnSimStop().setEnabled(false);
		System.out.println("Simulation Ended");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			try {
				Thread.sleep(SliderListener.SPEED);
				pausePoint();
			} catch (InterruptedException e) {
				throw new RuntimeException();
			}
	}
}
