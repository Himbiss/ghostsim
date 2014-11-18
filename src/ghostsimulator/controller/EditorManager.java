package ghostsimulator.controller;

import ghostsimulator.GhostManager;
import ghostsimulator.model.BooHoo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class EditorManager {

	private final static String DIRECTORY = "programs";
	private final static String PROGRAM_NAME = "GhostProgram";
	private final static String PROGRAM_PREFIX = "public class " + PROGRAM_NAME
			+ " extends " + BooHoo.class.getSimpleName() + " {\n public ";
	private final static String PROGRAM_POSTFIX = "\n}";

	private GhostManager manager;
	private File file;

	public EditorManager(GhostManager manager) {
		this.manager = manager;
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
				String line = "";
				while ((line = reader.readLine()) != null) {
					Document doc = manager.getEditor().getDocument();
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

	/**
	 * Saves the Editor with content
	 */
	public void saveEditor() {
		if (file != null) {
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

	public void loadDefaultFile() {
		File file = new File(DIRECTORY + "/" + PROGRAM_NAME + ".java");
		// if the file does not exist, fill it with default content
		if (!file.exists()) {
			try (BufferedWriter writer = new BufferedWriter(
					new FileWriter(file))) {
				writer.write("void main() {\n");
				writer.newLine();
				writer.newLine();
				writer.write("}");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// load the editor with the content
		try {
			loadEditor(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
