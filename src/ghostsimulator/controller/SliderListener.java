package ghostsimulator.controller;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {

	public static volatile int SPEED;
	
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            SPEED = (int)source.getValue();
        }   
	}

}
