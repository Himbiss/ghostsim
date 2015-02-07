package ghostsimulator.controller;

import ghostsimulator.model.Simulation;
import ghostsimulator.util.Resources;

public class SimulationController {

	private Simulation t;
	private EntityManager manager;
	
	public SimulationController() {
		manager = EntityManager.getInstance();
	}
	
	/**
	 * Starts a new simulation
	 */
	public void startSimulation() {
		if(t != null && t.getState() == Thread.State.WAITING) {
			t.unpause();
			setPauseStartStopEnabled(true, false, true);
			manager.getInfoLabel().setText(Resources.getValue("info.sim.restart"));
		} else {
			EntityManager.getInstance().getInfoLabel().setText(Resources.getValue("info.sim.start"));
			t = new Simulation();
			t.start();
		}
	}
	
	/**
	 * Pauses the current simulation
	 */
	public void pauseSimulation() {
		t.pause();
		setPauseStartStopEnabled(false, true, true);
	}
	
	/**
	 * stops the current simulation
	 */
	public void stopSimulation() {
		t.interrupt();
	}
	
	/**
	 * Used to set the simulation controll buttons/items of the toolbar and the menubar enabled/diabled.
	 * Actually hurts the MVC concept but there is no other way.
	 * @param pause
	 * @param start
	 * @param stop
	 */
	public void setPauseStartStopEnabled(boolean pause, boolean start, boolean stop) {
		manager.getToolbar().setPauseStartStopEnabled(pause, start, stop);
		manager.getMenubar().setPauseStartStopEnables(pause, start, stop);
	}
}
