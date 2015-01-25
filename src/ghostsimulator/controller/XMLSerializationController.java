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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLSerializationController implements ActionListener {

	private GhostManager manager;
	private final JFileChooser fc = new JFileChooser();

	// constants for the xml
	public static final String TERRITORY = "territory";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String TILE = "tile";
	public static final String BOOHOO_STATE = "boohoo_state";
	public static final String COLUMN = "col";
	public static final String ROW = "row";
	public static final String DIRECTION = "direction";
	public static final String WALL = "wall";
	public static final String WALL_TYPE = "wall_type";
	public static final String FIREBALLS = "fireballs";

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

	public void saveWithStAX(Writer writer1) {
		if (writer1 == null)
			return;

		// create a new output factory
		XMLOutputFactory factory = XMLOutputFactory.newInstance();

		try {
			XMLStreamWriter writer = factory
					.createXMLStreamWriter(writer1);

			Territory territory = manager.getTerritory();

			// write start document and root node
			writer.writeStartDocument();
			
			// write the dtd
			writer.writeDTD(getDtd());
			
			// write the root tag
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
			writer.writeAttribute(FIREBALLS, String.valueOf(territory.getBoohooNumFireballs()));
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
		}
	}
	
	/**
	 * Saves the territory to 'file' using StAX
	 * 
	 * @param file
	 */
	public void saveWithStAX(File file) {
		try {
			saveWithStAX(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Territory parseXMLWithSAX(InputStream stream) {
		SAXDefaultHandler handler = new SAXDefaultHandler();
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(stream, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler.getTerritory();
	}
	
	/**
	 * Loads the territory from 'file' using SAX
	 * 
	 * @param file
	 */
	private void loadWithSAX(File file) {
		Territory newTerritory;
		try(FileInputStream stream = new FileInputStream(file)) {
			newTerritory = parseXMLWithSAX(stream);
			changeTerritory(newTerritory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void loadWithSAX(String xml) {
		Territory newTerritory;
		try(InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));) {
			newTerritory = parseXMLWithSAX(stream);
			changeTerritory(newTerritory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
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
		int boohoo_col, boohoo_row, boohoo_fireballs;
		Direction boohoo_dir;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(true);
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
					boohoo_fireballs = Integer.parseInt(elem.getAttribute(FIREBALLS));
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
				territory.setBoohooNumFireballs(boohoo_fireballs);
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
	private void loadWithStAXCursor(File file) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_VALIDATING, "true");
		
		XMLStreamReader streamReader = null;
		try(Reader reader = new FileReader(file)) {
			// create the streamreader. has to be closed manually, because
			// the autoclose interface is not implemented
			streamReader = factory.createXMLStreamReader(reader);
			
			Territory territory = null;
			Tile tile = null;
			Direction boohoo_dir = Direction.EAST;
			int boohoo_col = 1, boohoo_row = 1, boohoo_fireballs = 0;
			
			// go through every event
			while(streamReader.hasNext()) {
				// switch by the event type
				switch (streamReader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					String qName = streamReader.getName().getLocalPart();
					
					if (qName.equalsIgnoreCase(TERRITORY)) {
						int columns = Integer.valueOf(streamReader.getAttributeValue(null, WIDTH));
						int rows = Integer.valueOf(streamReader.getAttributeValue(null, HEIGHT));
						territory = new Territory(columns, rows);
					}

					if (qName.equalsIgnoreCase(TILE)) {
						int col = Integer.valueOf(streamReader.getAttributeValue(null, COLUMN));
						int row = Integer.valueOf(streamReader.getAttributeValue(null, ROW));
						int fireballs = Integer.valueOf(streamReader.getAttributeValue(null, FIREBALLS));
						tile = new Tile(col, row);
						tile.setFireballs(fireballs);
					}

					if (qName.equalsIgnoreCase(WALL)) {
						tile.setWall(Wall.valueOf(streamReader.getAttributeValue(null, WALL_TYPE)));
					}
					
					if (qName.equalsIgnoreCase(BOOHOO_STATE)) {
						boohoo_col = Integer.valueOf(streamReader.getAttributeValue(null, COLUMN));
						boohoo_row = Integer.valueOf(streamReader.getAttributeValue(null, ROW));
						boohoo_fireballs = Integer.valueOf(streamReader.getAttributeValue(null, FIREBALLS));
						boohoo_dir = Direction.valueOf(streamReader.getAttributeValue(null, DIRECTION));
					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					qName = streamReader.getName().toString();
					
					if (qName.equalsIgnoreCase(TILE)) {
						territory.setTile(tile.getColumnIndex(), tile.getRowIndex(), tile);
					}
					
					if (qName.equalsIgnoreCase(TERRITORY)) {
						changeTerritory(territory);
					}
					
					if (qName.equalsIgnoreCase(BOOHOO_STATE)) {
						BooHoo boo = manager.getTerritory().getBoohoo();
						territory.setBoohoo(boo);
						territory.setBoohooNumFireballs(boohoo_fireballs);
						territory.setBooHooPosition(new Point(boohoo_col,boohoo_row));
						territory.setBooHooDirection(boohoo_dir);
					}
					break;
				case XMLStreamConstants.END_DOCUMENT:
					streamReader.close();
					break;
				default:
					break;
				}
				// get next event
				streamReader.next();
			}
		    
			// add the boohoo
			BooHoo boo = manager.getTerritory().getBoohoo();
			territory.setBoohoo(boo);
			territory.setBoohooNumFireballs(boohoo_fireballs);
			territory.setBooHooPosition(new Point(boohoo_col,boohoo_row));
			territory.setBooHooDirection(boohoo_dir);

			// if everything went fine, set the new territory
			changeTerritory(territory);
		    
		} catch (XMLStreamException e) {
		    e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			// try to close the reader if not already happened
			try {
				streamReader.close();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the territory from 'file' using SAX
	 * 
	 * @param file
	 */
	private void loadWithStAXIterator(File file) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_VALIDATING, "true");
		
		XMLEventReader eventReader = null;
		try(InputStream is = new FileInputStream(file)) {
			
			// create the eventreader. has to be closed manually, because
			// the autoclose interface is not implemented
			eventReader = factory.createXMLEventReader(is);
			
			Territory territory = null;
			Tile tile = null;
			Direction boohoo_dir = Direction.EAST;
			int boohoo_col = 1, boohoo_row = 1, boohoo_fireballs = 0;
			
			// go through every event
			while(eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				
				if(event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();
				
					if (qName.equalsIgnoreCase(TERRITORY)) {
						int columns = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(WIDTH)).getValue());
						int rows = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(HEIGHT)).getValue());
						territory = new Territory(columns, rows);
					}
					
					if (qName.equalsIgnoreCase(TILE)) {
						int col = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(COLUMN)).getValue());
						int row = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(ROW)).getValue());
						int fireballs = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(FIREBALLS)).getValue());
						tile = new Tile(col, row);
						tile.setFireballs(fireballs);
					}

					if (qName.equalsIgnoreCase(WALL)) {
						tile.setWall(Wall.valueOf(startElement.getAttributeByName(QName.valueOf(WALL_TYPE)).getValue()));
					}
					
					if (qName.equalsIgnoreCase(BOOHOO_STATE)) {
						boohoo_col = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(COLUMN)).getValue());
						boohoo_row = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(ROW)).getValue());
						boohoo_fireballs = Integer.valueOf(startElement.getAttributeByName(QName.valueOf(FIREBALLS)).getValue());
						boohoo_dir = Direction.valueOf(startElement.getAttributeByName(QName.valueOf(DIRECTION)).getValue());
					}
					
				} else if(event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					String qName = endElement.getName().getLocalPart();
					
					if (qName.equalsIgnoreCase(TILE)) {
						territory.setTile(tile.getColumnIndex(), tile.getRowIndex(), tile);
					}
					
					if (qName.equalsIgnoreCase(BOOHOO_STATE)) {
						BooHoo boo = manager.getTerritory().getBoohoo();
						territory.setBoohoo(boo);
						territory.setBoohooNumFireballs(boohoo_fireballs);
						territory.setBooHooPosition(new Point(boohoo_col,boohoo_row));
						territory.setBooHooDirection(boohoo_dir);
					}
				}
			}
		    
			// add the boohoo
			BooHoo boo = manager.getTerritory().getBoohoo();
			territory.setBoohoo(boo);
			territory.setBooHooPosition(new Point(boohoo_col,boohoo_row));
			territory.setBooHooDirection(boohoo_dir);

			// if everything went fine, set the new territory
			changeTerritory(territory);
		    
		} catch (XMLStreamException e) {
		    e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			// try to close the reader if not already happened
			try {
				eventReader.close();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Returns the DTD for the Territory XML-Files as a string.
	 * @return dtd
	 */
	private String getDtd() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<!DOCTYPE "+TERRITORY);
		builder.append("[");
		builder.append("<!ELEMENT "+TERRITORY+" ("+BOOHOO_STATE+","+TILE+"+)>");
		builder.append("<!ELEMENT "+BOOHOO_STATE+" (#PCDATA)>");
		builder.append("<!ELEMENT "+TILE+" ("+WALL+"?)>");
		builder.append("<!ELEMENT "+WALL+" (#PCDATA)>");
		builder.append("<!ATTLIST "+TERRITORY);
		builder.append("   "+WIDTH+"    CDATA    #REQUIRED");
		builder.append("   "+HEIGHT+"    CDATA    #REQUIRED");
		builder.append(">");
		builder.append("<!ATTLIST "+BOOHOO_STATE);
		builder.append("   "+COLUMN+"    CDATA    #REQUIRED");
		builder.append("   "+ROW+"    CDATA    #REQUIRED");
		builder.append("   "+DIRECTION+"    CDATA    #REQUIRED");
		builder.append("   "+FIREBALLS+"    CDATA    #REQUIRED");
		builder.append(">");
		builder.append("<!ATTLIST "+TILE);
		builder.append("   "+COLUMN+"    CDATA    #REQUIRED");
		builder.append("   "+ROW+"    CDATA    #REQUIRED");
		builder.append("   "+FIREBALLS+"    CDATA    #REQUIRED");
		builder.append(">");
		builder.append("<!ATTLIST "+WALL);
		builder.append("   "+WALL_TYPE+"    CDATA    #REQUIRED");
		builder.append(">");
		builder.append("]>");
		
		return builder.toString();
	}
}
