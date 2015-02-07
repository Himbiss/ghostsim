package ghostsimulator.model;

import ghostsimulator.controller.EntityManager;
import ghostsimulator.util.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class Simulation extends Thread implements Observer {

	private BooHoo boo;
	private EntityManager manager;
	
	public static volatile int SPEED;
	private volatile boolean needToPause = false;

	public Simulation() {
		this.manager = EntityManager.getInstance();
		this.boo = manager.getTerritory().getBoohoo();
	}
	
	public synchronized boolean isPaused() {
		return needToPause;
	}
	
	private synchronized void pausePoint() throws InterruptedException {
		while (needToPause) {
			manager.getSimulationController().setPauseStartStopEnabled(false, true, true);
			manager.getInfoLabel().setText(Resources.getValue("info.sim.pause"));
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
		manager.getSimulationController().setPauseStartStopEnabled(true, false, true);
		boo.addObserver(this);
		try {
			Class<?>[] noparams = {};
			Method method = boo.getClass().getMethod("main", noparams);
			method.invoke(boo);
		} catch (NoSuchMethodException e) {
			manager.getInfoLabel().setText(Resources.getValue("err.nomainmethod"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			manager.getInfoLabel().setText(Resources.getValue("info.sim.stop"));
		}
		boo.deleteObserver(this);
		manager.getSimulationController().setPauseStartStopEnabled(false, true, false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			try {
				Thread.sleep(SPEED);
				pausePoint();
			} catch (InterruptedException e) {
				throw new RuntimeException();
			}
	}
}
