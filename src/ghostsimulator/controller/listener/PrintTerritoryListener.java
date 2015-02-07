package ghostsimulator.controller.listener;

import ghostsimulator.controller.EntityManager;
import ghostsimulator.util.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;

public class PrintTerritoryListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);
		PrintService prserv = PrintServiceLookup.lookupDefaultPrintService();
		if(prserv != null) {
			DocPrintJob pj = prserv.createPrintJob();
			Doc doc = new SimpleDoc(EntityManager.getInstance().getTerritoryPanel(), flavor, null);
			try {
				pj.print(doc, aset);
			} catch (PrintException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(EntityManager.getInstance().getFrame(), Resources.getValue("err.noprintservicefound"));
		}
	}

}
