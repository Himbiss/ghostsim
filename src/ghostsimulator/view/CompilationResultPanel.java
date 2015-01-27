package ghostsimulator.view;

import ghostsimulator.util.CompilationResultOutputStream;

import java.awt.Color;
import java.io.OutputStream;

import javax.swing.JPanel;
import javax.swing.JTextArea;


public class CompilationResultPanel extends JPanel {

	private static final long serialVersionUID = 6250061227322516651L;
	private JTextArea area;
	private CompilationResultOutputStream stream;

	public CompilationResultPanel() {
		area = new JTextArea();
		area.setEditable(false);
		area.setForeground(Color.RED);
		add(area);
		stream = new CompilationResultOutputStream(area);
	}
	
	public OutputStream getCompilationResultStream() {
		return stream;
	}
}
