package ghostsimulator.controller.listener;

import ghostsimulator.controller.EntityManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public class PrintEditorListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// JTextComponent already has a print method
			EntityManager.getInstance().getEditor().print();
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}
	}

}
