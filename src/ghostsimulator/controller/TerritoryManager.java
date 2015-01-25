package ghostsimulator.controller;

import java.io.StringWriter;

import ghostsimulator.GhostManager;
import ghostsimulator.model.BooHoo;
import ghostsimulator.model.Territory;
import ghostsimulator.util.BooHooClassLoader;

public class TerritoryManager {

	private GhostManager manager;
	
	public TerritoryManager(GhostManager manager) {
		this.manager = manager;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTerritoryAsXML() {
		StringWriter writer = new StringWriter();
		GhostManager.getInstance().getXmlSerializationController().saveWithStAX(writer);
		return writer.toString();
	}
}
