package ghostsimulator.controller.tutor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetAnswerListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		TutorManager.getInstance().getAnswer();
	}

}
