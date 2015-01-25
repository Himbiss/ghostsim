package ghostsimulator.controller;

import ghostsimulator.util.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class DBLoadListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String tag = JOptionPane.showInputDialog(Resources.getValue("menu.examples.loadexample"));
		if(tag != null) {
			String[] examples = DBManager.getInstance().getExamples(tag);
			if(examples == null || examples.length == 0) {
				JOptionPane.showMessageDialog(null, Resources.getValue("menu.examples.nothingfound"));
			} else {
				int choice = JOptionPane.showOptionDialog(null, Resources.getValue("menu.examples.chooseexample"), Resources.getValue("menu.examples.chooseexample"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, examples, examples[0]);
				if(choice != JOptionPane.CLOSED_OPTION) {
					DBManager.getInstance().loadExample(examples[choice]);
				}
			}
		}
	}

}
