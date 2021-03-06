package ghostsimulator.controller;

import ghostsimulator.model.BooHoo;
import ghostsimulator.util.Resources;
import ghostsimulator.view.CompilationResultPanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * This class manages the editor pane.
 * @author vincent
 *
 */
public class EditorManager {

	public final static String DIRECTORY = "programs";
	public final static String PROGRAM_NAME = "GhostProgram";
	private final static String PROGRAM_PREFIX = "public class " + PROGRAM_NAME
			+ " extends " + BooHoo.class.getCanonicalName() + " {\n public ";
	private final static String PROGRAM_POSTFIX = "\n}";

	private EntityManager manager;
	private File file;

	public EditorManager() {
		this.manager = EntityManager.getInstance();
	}

	/**
	 * Loads the Editor with content. If fileURL is null, default content is
	 * used.
	 * 
	 * @param toRead
	 */
	public void loadEditor(File toRead) throws IOException {
		file = toRead;
		if (!file.isDirectory() && file.canRead()) {
			try (BufferedReader reader = new BufferedReader(
					new FileReader(file))) {
				Document doc = manager.getEditor().getDocument();
				String line = "";
				while ((line = reader.readLine()) != null) {
					doc.insertString(doc.getLength(), line + "\n", null);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else {
			throw new IOException("File cannot be opened");
		}
	}

	public void loadEditor(String program) {
		Document doc = manager.getEditor().getDocument();
		try {
			doc.remove(0, doc.getLength());
			doc.insertString(0, program + "\n", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void clearEditor() {
		Document doc = manager.getEditor().getDocument();
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the Editor with content
	 */
	public void saveEditor() {
		if (file != null) {
			if (!file.exists())
				try {
					if (file.createNewFile()) {
						System.err.println("Created new file: "
								+ file.getAbsolutePath());
					} else {
						System.err.println("Error creating file: "
								+ file.getAbsolutePath());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter(file))) {
				writer.write(manager.getEditor().getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Cannot save, file is null");
		}
	}

	/**
	 * Called to load the default file into the editor
	 */
	public void loadDefaultFile() {
		File directory = new File(DIRECTORY);
		if (!directory.exists()) {
			System.out.println("Directory '" + DIRECTORY
					+ "' does not exist, creating it..");
			if (!directory.mkdir()) {
				System.err.println("Could not create the '" + DIRECTORY
						+ "' directory!");
				return;
			}
		}

		File file = new File(DIRECTORY + "/" + PROGRAM_NAME + ".java");
		// if the file does not exist, fill it with default content
		if (!file.exists()) {
			System.out.println("File '" + DIRECTORY + "/" + PROGRAM_NAME
					+ "' does not exist, creating it..");
			try {
				if (file.createNewFile()) {
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter(file))) {
						writer.write("void main() {\n");
						writer.newLine();
						writer.newLine();
						writer.write("}");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Could not create the file " + DIRECTORY
							+ "/" + PROGRAM_NAME + "'!");
					return;
				}
			} catch (IOException e) {
				System.out
						.println("An Exception occurred during creation of the file "
								+ DIRECTORY + "/" + PROGRAM_NAME + "'!");
				e.printStackTrace();
				return;
			}
		}
		// load the editor with the content
		try {
			loadEditor(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Compiles the current Code in the editor and shows an error message if
	 * something went wrong
	 */
	public void compile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

			// build the new file with pre- and postfix
			StringBuilder contentBuilder = new StringBuilder(PROGRAM_PREFIX);
			contentBuilder.append(manager.getEditor().getText());
			contentBuilder.append(PROGRAM_POSTFIX);

			// write to file
			writer.write(contentBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create a new result pane for showing the compilation result
		CompilationResultPanel resultPanel = new CompilationResultPanel();

		// compile everything
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null,
				resultPanel.getCompilationResultStream(),
				resultPanel.getCompilationResultStream(),
				file.getAbsolutePath());

		if (result == 1) {
			JOptionPane.showConfirmDialog(null, resultPanel,
					Resources.getValue("info.compilation.error"),
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		} else {
			JOptionPane.showConfirmDialog(null,
					Resources.getValue("info.compilation.success"),
					Resources.getValue("info.compilation.success"),
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		}

		// write old stuff from the editor to file
		saveEditor();
		clearEditor();
		try {
			loadEditor(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result == 0) {
			// load the boohoo with a class loader, instantiate it and add it to
			// the territory
			manager.getTerritoryManager().exchangeBooHoo();
		}
	}

	public String getProgram() {
		Document doc = manager.getEditor().getDocument();
		String text = null;
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return text;
	}
}
