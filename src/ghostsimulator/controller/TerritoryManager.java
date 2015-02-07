package ghostsimulator.controller;

import java.io.StringWriter;

import ghostsimulator.model.BooHoo;
import ghostsimulator.model.Territory;
import ghostsimulator.util.BooHooClassLoader;

public class TerritoryManager {

	private EntityManager manager;
	
	public TerritoryManager() {
		this.manager = EntityManager.getInstance();
	}
	
	public void exchangeBooHoo() {
		BooHooClassLoader classLoader = new BooHooClassLoader(BooHooClassLoader.class.getClassLoader());
		try {
			Class<?> clazz = classLoader.loadBooHooClass();
			Territory territory = manager.getTerritory();
			BooHoo boo = (BooHoo) clazz.newInstance();
			territory.exchangeBooHoo(boo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the Territory as a XML String
	 * @return xml
	 */
	public String getTerritoryAsXML() {
		StringWriter writer = new StringWriter();
		manager.getXmlSerializationController().saveWithStAX(writer);
		return writer.toString();
	}
	
	/**
	 * Changes the current territory
	 * @param territory
	 */
	public void changeTerritory(Territory territory) {
		BooHoo oldBoo = manager.getTerritory().getBoohoo();
		territory.exchangeBooHoo(oldBoo);
		manager.setTerritory(territory);
	}
}
