package ghostsimulator.controller.tutor;

import ghostsimulator.GhostManager;
import ghostsimulator.model.Territory;
import ghostsimulator.util.Resources;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

public class TutorManager {

	private static TutorManager instance;
	private TutorImpl tutorServer;
	private TutorClientI tutorClient;
	private int currentId;
	
	private TutorManager() {
		if(Resources.getSystemProperty("role").equalsIgnoreCase("tutor")) {
			registerAsTutor();
		} else {
			registerAsStudent();
		}
 	}
	
	public static TutorManager getInstance() {
		if(instance == null)
			return (instance = new TutorManager());
		return instance;
	}
	
	private void registerAsTutor() {
		String server = Resources.getSystemProperty("tutorhost");
		int port = Integer.parseInt(Resources.getSystemProperty("tutorport"));
		try {
			LocateRegistry.createRegistry(port);
			System.out.println("RMI-Registry created at port:"+port);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "Error creating the RMI-Registry at port:"+port+"!");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Registering as Tutor: "+server+":"+port);
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			tutorServer = new TutorImpl();
			registry.rebind("Tutor", tutorServer);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void registerAsStudent() {
		String server = Resources.getSystemProperty("tutorhost");
		int port = Integer.parseInt(Resources.getSystemProperty("tutorport"));
		System.out.println("Registering as Student: "+server+":"+port);
		try {
			Registry registry = LocateRegistry.getRegistry(server, port);
			tutorClient = (TutorClientI) registry.lookup("Tutor");
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Konnte keine Verbindung mit dem TutorService ("+server+":"+port+") aufbauen!");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public TutorClientI getClientTutor() {
		return tutorServer;
	}

	public void getAnswer() {
		try {
			if(tutorClient.hasAnswer(currentId)) {
				Answer answer = tutorClient.getAnswer(currentId);
				GhostManager.getInstance().setTerritory(answer.getTerritory());
				GhostManager.getInstance().getEditorManager().loadEditor(answer.getCode());
				GhostManager.getInstance().getMenubar().sendRequestItem.setEnabled(true);
				GhostManager.getInstance().getMenubar().getAnswerItem.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "No Answer aviable!");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequest() {
		String program = GhostManager.getInstance().getEditorManager().getProgram();
		Territory territory = GhostManager.getInstance().getTerritory();
		try {
			currentId = tutorClient.sendRequest(territory, program);
			GhostManager.getInstance().getMenubar().sendRequestItem.setEnabled(false);
			GhostManager.getInstance().getMenubar().getAnswerItem.setEnabled(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void getRequest() {
		if(tutorServer.hasRequest()) {
			Request request = tutorServer.getLastRequest();
			GhostManager.getInstance().setTerritory(request.getTerritory());
			GhostManager.getInstance().getEditorManager().loadEditor(request.getCode());
			currentId = request.getId();
			GhostManager.getInstance().getMenubar().getRequestItem.setEnabled(false);
			GhostManager.getInstance().getMenubar().sendAnswerItem.setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(null, "Keine Anfragen verfügbar!");
		}
	}
	
	public void answerRequest() {
		String program = GhostManager.getInstance().getEditorManager().getProgram();
		Territory territory = GhostManager.getInstance().getTerritory();
		tutorServer.answerRequest(currentId, territory, program);
		GhostManager.getInstance().getMenubar().getRequestItem.setEnabled(true);
		GhostManager.getInstance().getMenubar().sendAnswerItem.setEnabled(false);
	}
}
