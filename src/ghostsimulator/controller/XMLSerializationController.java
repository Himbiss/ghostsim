package ghostsimulator.controller;

import ghostsimulator.GhostManager;
import ghostsimulator.model.Territory;
import ghostsimulator.model.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLSerializationController implements ActionListener {

	private GhostManager manager;
	private final JFileChooser fc = new JFileChooser();
	
	// constants for the xml
	private static final String TERRITORY = "Territory";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String TILE = "tile";
	private static final String BOOHOO_STATE = "boohoo_state";
	private static final String COLUMN = "col";
	private static final String ROW = "row";
	private static final String DIRECTION = "direction";
	private static final String WALL = "wall";
	private static final String WALL_TYPE = "wall_type";
	private static final String FIREBALLS = "fireballs";
	
	
	public XMLSerializationController(GhostManager manager) {
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == manager.getMenubar().saveTerritoryItem) {
			saveWithStAX(getFileSaveDialog());
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritoryDOM) {
			
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritorySAX) {
			
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritoryStAXCursor) {
			
		}  else if(e.getSource() == manager.getMenubar().subLoadTerritoryStAXIterator) {
			
		}
	}
	
	/**
	 * Shows a Save Dialog and returns the selected file.
	 * Returns null if canceled
	 * @return file
	 */
	private File getFileSaveDialog()	 {
		int returnVal = fc.showSaveDialog(manager.getFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		return null;
	}
	
	/**
	 * Shows a Open Dialog and returns the selected file.
	 * Returns null if canceled
	 * @return file
	 */
	private File getFileOpenDialog() {
		int returnVal = fc.showOpenDialog(manager.getFrame());
		if(returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		return null;
	}
	
	/**
	 * Saves the territory to 'file' using StAX
	 * @param file
	 */
	private void saveWithStAX(File file) {
		if(file == null)
			return;
		
		// create a new output factory
		XMLOutputFactory factory      = XMLOutputFactory.newInstance();

		 try {
		     XMLStreamWriter writer = factory.createXMLStreamWriter(
		             new FileWriter(file));

		     Territory territory = manager.getTerritory();
		     
		     // write start document and root node
		     writer.writeStartDocument();
		     writer.writeStartElement(TERRITORY);
		     
		     // write attributes (width and height)
		     writer.writeAttribute(WIDTH, String.valueOf(territory.getColumnCount()));
		     writer.writeAttribute(HEIGHT, String.valueOf(territory.getRowCount()));
		     
		     // write the boohoo position as a element
		     writer.writeStartElement(BOOHOO_STATE);
		     writer.writeAttribute(COLUMN, String.valueOf(territory.getBoohooPosition().x));
		     writer.writeAttribute(ROW, String.valueOf(territory.getBoohooPosition().y));
		     writer.writeAttribute(DIRECTION, territory.getBoohooDirection().name());
		     writer.writeEndElement();
		     
		     // write the territory
		     for(int col=0; col<territory.getColumnCount(); col++) {
		    	 for(int row=0; row<territory.getRowCount(); row++) {
		    		 // write an tile element
		    		 Tile tile = territory.getTile(col,row);
		    		 writer.writeStartElement(TILE);
		    		 writer.writeAttribute(COLUMN, String.valueOf(col));
		    		 writer.writeAttribute(ROW, String.valueOf(row));
		    		 writer.writeAttribute(FIREBALLS, String.valueOf(tile.numFireballs()));
		    		 // check if this tile has a wall, if yes write an wall element
		    		 if(tile.isWall()) {
		    			 writer.writeStartElement(WALL);
		    			 writer.writeAttribute(WALL_TYPE, tile.getWall().name());
		    			 writer.writeEndElement();
		    		 }
		    		 writer.writeEndElement();
		    	 }
		     }
		     
		     // end root node and end document
		     writer.writeEndElement();
		     writer.writeEndDocument();

		     writer.flush();
		     writer.close();

		 } catch (XMLStreamException e) {
		     e.printStackTrace();
		 } catch (IOException e) {
		     e.printStackTrace();
		 }
	}
	
	/**
	 * Loads the territory from 'file' using SAX
	 * @param file
	 */
	private void loadWithSAX(File file) {
		
	}
	
	/**
	 * Loads the territory from 'file' using SAX
	 * @param file
	 */
	private void loadWithDOM(File file) {
		
	}
	
	/**
	 * Loads the territory from 'file' using SAX
	 * @param file
	 */
	private void loadWithStAXCursor(File file) {
		
	}
	
	/**
	 * Loads the territory from 'file' using SAX
	 * @param file
	 */
	private void loadWithStAXIterator(File file) {
		
	}

}
