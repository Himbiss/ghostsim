package ghostsimulator.controller;

import ghostsimulator.GhostManager;
import ghostsimulator.model.BooHoo;
import ghostsimulator.model.BooHoo.Direction;
import ghostsimulator.model.Territory;
import ghostsimulator.model.Tile;
import ghostsimulator.model.Tile.Wall;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
		} else if (e.getSource() == manager.getMenubar().subLoadTerritoryDOM) {
			loadWithDOM(getFileOpenDialog());
		} else if (e.getSource() == manager.getMenubar().subLoadTerritorySAX) {
			loadWithSAX(getFileOpenDialog());
		} else if (e.getSource() == manager.getMenubar().subLoadTerritoryStAXCursor) {
			loadWithStAXCursor(getFileOpenDialog());
		} else if (e.getSource() == manager.getMenubar().subLoadTerritoryStAXIterator) {
			loadWithStAXIterator(getFileOpenDialog());
		}
	}

	/**
	 * Shows a Save Dialog and returns the selected file. Returns null if
	 * canceled
	 * 
	 * @return file
	 */
	private File getFileSaveDialog() {
		int returnVal = fc.showSaveDialog(manager.getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		return null;
	}

	/**
	 * Shows a Open Dialog and returns the selected file. Returns null if
	 * canceled
	 * 
	 * @return file
	 */
	private File getFileOpenDialog() {
		int returnVal = fc.showOpenDialog(manager.getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		return null;
	}

	/**
	 * Saves the territory to 'file' using StAX
	 * 
	 * @param file
	 */
	private void saveWithStAX(File file) {
		if (file == null)
			return;

		// create a new output factory
		XMLOutputFactory factory = XMLOutputFactory.newInstance();

		try {
			XMLStreamWriter writer = factory
					.createXMLStreamWriter(new FileWriter(file));

			Territory territory = manager.getTerritory();

			// write start document and root node
			writer.writeStartDocument();
			writer.writeStartElement(TERRITORY);

			// write attributes (width and height)
			writer.writeAttribute(WIDTH,
					String.valueOf(territory.getColumnCount()));
			writer.writeAttribute(HEIGHT,
					String.valueOf(territory.getRowCount()));

			// write the boohoo position as a element
			writer.writeStartElement(BOOHOO_STATE);
			writer.writeAttribute(COLUMN,
					String.valueOf(territory.getBoohooPosition().x));
			writer.writeAttribute(ROW,
					String.valueOf(territory.getBoohooPosition().y));
			writer.writeAttribute(DIRECTION, territory.getBoohooDirection()
					.name());
			writer.writeEndElement();

			// write the territory
			for (int col = 0; col < territory.getColumnCount(); col++) {
				for (int row = 0; row < territory.getRowCount(); row++) {
					// write an tile element
					Tile tile = territory.getTile(col, row);
					writer.writeStartElement(TILE);
					writer.writeAttribute(COLUMN, String.valueOf(col));
					writer.writeAttribute(ROW, String.valueOf(row));
					writer.writeAttribute(FIREBALLS,
							String.valueOf(tile.numFireballs()));
					// check if this tile has a wall, if yes write an wall
					// element
					if (tile.isWall()) {
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
	 * 
	 * @param file
	 */
	private void loadWithSAX(File file) {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			DefaultHandler handler = new DefaultHandler() {

				private Tile tile;
				private Territory territory;
				private int boohoo_col, boohoo_row;
				private Direction boohoo_dir;
				
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					if (qName.equalsIgnoreCase(TERRITORY)) {
						int columns = Integer.valueOf(attributes.getValue(WIDTH));
						int rows = Integer.valueOf(attributes.getValue(HEIGHT));
						territory = new Territory(columns, rows);
					}

					if (qName.equalsIgnoreCase(TILE)) {
						int col = Integer.valueOf(attributes.getValue(COLUMN));
						int row = Integer.valueOf(attributes.getValue(ROW));
						int fireballs = Integer.valueOf(attributes.getValue(FIREBALLS));
						tile = new Tile(col, row);
						tile.setFireballs(fireballs);
					}

					if (qName.equalsIgnoreCase(WALL)) {
						tile.setWall(Wall.valueOf(attributes.getValue(WALL_TYPE)));
					}
					
					if (qName.equalsIgnoreCase(BOOHOO_STATE)) {
						boohoo_col = Integer.valueOf(attributes.getValue(COLUMN));
						boohoo_row = Integer.valueOf(attributes.getValue(ROW));
						boohoo_dir = Direction.valueOf(attributes.getValue(DIRECTION));
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase(TILE)) {
						territory.setTile(tile.getColumnIndex(), tile.getRowIndex(), tile);
					}
					
					if (qName.equalsIgnoreCase(TERRITORY)) {
						changeTerritory(territory);
					}
					
					if (qName.equalsIgnoreCase(BOOHOO_STATE)) {
						BooHoo boo = manager.getTerritory().getBoohoo();
						territory.setBoohoo(boo);
						territory.setBooHooPosition(new Point(boohoo_col,boohoo_row));
						territory.setBooHooDirection(boohoo_dir);
					}

				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					// nothing to do
				}

			};

			saxParser.parse(file, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Changes the current territory
	 * @param territory
	 */
	private void changeTerritory(Territory territory) {
		manager.setTerritory(territory);
	}

	/**
	 * Loads the territory from 'file' using SAX
	 * 
	 * @param file
	 */
	private void loadWithDOM(File file) {
		Territory territory = null;
		int boohoo_col, boohoo_row;
		Direction boohoo_dir;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			Element element = doc.getDocumentElement();
			
			
			// check if this is a valid territory root node
			if(element.getTagName().equalsIgnoreCase(TERRITORY)) {
				// create a new territory
				int columns = Integer.valueOf(element.getAttribute(WIDTH));
				int rows = Integer.valueOf(element.getAttribute(HEIGHT));
				territory = new Territory(columns, rows);
				
				// get all tile nodes
				NodeList tileNodeList = doc.getElementsByTagName(TILE);
				for(int i=0; i<tileNodeList.getLength(); i++) {
					Element tileElem = (Element) tileNodeList.item(i);
					int col = Integer.valueOf(tileElem.getAttribute(COLUMN));
					int row = Integer.valueOf(tileElem.getAttribute(ROW));
					int fireballs = Integer.valueOf(tileElem.getAttribute(FIREBALLS));
					Tile tile = new Tile(col, row);
					tile.setFireballs(fireballs);
					territory.setTile(col, row, tile);
				}
				
				// get all boo hoo state nodes
				NodeList booHooStateNodeList = doc.getElementsByTagName(BOOHOO_STATE);
				if(booHooStateNodeList.getLength() != 1) {
					throw new SAXException();
				} else {
					Element elem = (Element) booHooStateNodeList.item(0);
					boohoo_col = Integer.parseInt(elem.getAttribute(COLUMN));
					boohoo_row = Integer.parseInt(elem.getAttribute(ROW));
					boohoo_dir = Direction.valueOf(elem.getAttribute(DIRECTION));
				}
				
				// get all wall nodes
				NodeList wallNodeList = doc.getElementsByTagName(WALL);
				for(int i=0; i<wallNodeList.getLength(); i++) {
					Element wallElem = (Element) wallNodeList.item(i);
					Element tileElem = (Element) wallElem.getParentNode();
					int col = Integer.valueOf(tileElem.getAttribute(COLUMN));
					int row = Integer.valueOf(tileElem.getAttribute(ROW));
					Tile tile = territory.getTile(col, row);
					tile.setWall(Wall.valueOf(wallElem.getAttribute(WALL_TYPE)));
				}
				
				// add the boohoo
				BooHoo boo = manager.getTerritory().getBoohoo();
				territory.setBoohoo(boo);
				territory.setBooHooPosition(new Point(boohoo_col,boohoo_row));
				territory.setBooHooDirection(boohoo_dir);

				// if everything went fine, set the new territory
				changeTerritory(territory);
			} else {
				throw new SAXException();
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}

	/**
	 * Loads the territory from 'file' using SAX
	 * 
	 * @param file
	 */
	private void loadWithStAXCursor(File file) {

	}

	/**
	 * Loads the territory from 'file' using SAX
	 * 
	 * @param file
	 */
	private void loadWithStAXIterator(File file) {

	}

}
