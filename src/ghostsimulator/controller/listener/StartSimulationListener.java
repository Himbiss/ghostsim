package ghostsimulator.controller.listener;

import ghostsimulator.controller.EntityManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartSimulationListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		EntityManager.getInstance().getSimulationController().startSimulation();
	}

}
