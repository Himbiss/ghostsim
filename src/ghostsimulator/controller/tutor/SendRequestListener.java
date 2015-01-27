package ghostsimulator.controller.tutor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendRequestListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		TutorManager.getInstance().sendRequest();
	}

}
