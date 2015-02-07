package ghostsimulator.controller.listener;

import ghostsimulator.model.Simulation;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This listener sets the speed of the simulation according to the position of the speed slider
 * @author vincent
 *
 */
public class SliderListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            Simulation.SPEED = (int)source.getValue();
        }   
	}

}
