package ghostsimulator.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class CompilationResultOutputStream extends OutputStream {

	private JTextArea area;
	
	public CompilationResultOutputStream(JTextArea area) {
		this.area = area;
	}
	
	@Override
	public void write(int b) throws IOException {
		// redirects data to the text area
        area.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        area.setCaretPosition(area.getDocument().getLength());
	}

}
