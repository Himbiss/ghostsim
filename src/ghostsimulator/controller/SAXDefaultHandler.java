package ghostsimulator.controller;

import ghostsimulator.model.BooHoo;
import ghostsimulator.model.BooHoo.Direction;
import ghostsimulator.model.Territory;
import ghostsimulator.model.Tile;
import ghostsimulator.model.Tile.Wall;

import java.awt.Point;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class SAXDefaultHandler extends DefaultHandler {
	private Tile tile;
	private Territory territory;
	private int boohoo_col, boohoo_row, boohoo_fireballs;
	private Direction boohoo_dir;
	
	public void startElement(String uri, String localName,
			String qName, Attributes attributes)
			throws SAXException {

		if (qName.equalsIgnoreCase(XMLSerializationController.TERRITORY)) {
			int columns = Integer.valueOf(attributes.getValue(XMLSerializationController.WIDTH));
			int rows = Integer.valueOf(attributes.getValue(XMLSerializationController.HEIGHT));
			territory = new Territory(columns, rows);
		}

		if (qName.equalsIgnoreCase(XMLSerializationController.TILE)) {
			int col = Integer.valueOf(attributes.getValue(XMLSerializationController.COLUMN));
			int row = Integer.valueOf(attributes.getValue(XMLSerializationController.ROW));
			int fireballs = Integer.valueOf(attributes.getValue(XMLSerializationController.FIREBALLS));
			tile = new Tile(col, row);
			tile.setFireballs(fireballs);
		}

		if (qName.equalsIgnoreCase(XMLSerializationController.WALL)) {
			tile.setWall(Wall.valueOf(attributes.getValue(XMLSerializationController.WALL_TYPE)));
		}
		
		if (qName.equalsIgnoreCase(XMLSerializationController.BOOHOO_STATE)) {
			boohoo_col = Integer.valueOf(attributes.getValue(XMLSerializationController.COLUMN));
			boohoo_fireballs = Integer.valueOf(attributes.getValue(XMLSerializationController.FIREBALLS));
			boohoo_row = Integer.valueOf(attributes.getValue(XMLSerializationController.ROW));
			boohoo_dir = Direction.valueOf(attributes.getValue(XMLSerializationController.DIRECTION));
		}
	}

	public void endElement(String uri, String localName,
			String qName) throws SAXException {

		if (qName.equalsIgnoreCase(XMLSerializationController.TILE)) {
			territory.setTile(tile.getColumnIndex(), tile.getRowIndex(), tile);
		}
		
		if (qName.equalsIgnoreCase(XMLSerializationController.BOOHOO_STATE)) {
			BooHoo boo = EntityManager.getInstance().getTerritory().getBoohoo();
			territory.setBoohoo(boo);
			territory.setBoohooNumFireballs(boohoo_fireballs);
			territory.setBooHooPosition(new Point(boohoo_col,boohoo_row));
			territory.setBooHooDirection(boohoo_dir);
		}

	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
		// nothing to do
	}

	public Territory getTerritory() {
		return territory;
	}

};