package ghostsimulator.controller.tutor;

import ghostsimulator.controller.EntityManager;
import ghostsimulator.util.Resources;

import java.io.StringWriter;
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
			JOptionPane.showMessageDialog(null, Resources.getValue("err.noconnectiontutorservice")+" ("+server+":"+port+")");
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
				EntityManager.getInstance().getXmlSerializationController().loadWithSAX(answer.getTerritory());
				EntityManager.getInstance().getEditorManager().loadEditor(answer.getCode());
				EntityManager.getInstance().getMenubar().sendRequestItem.setEnabled(true);
				EntityManager.getInstance().getMenubar().getAnswerItem.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, Resources.getValue("err.noansweraviable"));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequest() {
		String program = EntityManager.getInstance().getEditorManager().getProgram();
		StringWriter writer = new StringWriter();
		EntityManager.getInstance().getXmlSerializationController().saveWithStAX(writer);
		try {
			currentId = tutorClient.sendRequest(writer.toString(), program);
			EntityManager.getInstance().getMenubar().sendRequestItem.setEnabled(false);
			EntityManager.getInstance().getMenubar().getAnswerItem.setEnabled(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void getRequest() {
		if(tutorServer.hasRequest()) {
			Request request = tutorServer.getLastRequest();
			EntityManager.getInstance().getXmlSerializationController().loadWithSAX(request.getTerritory());
			EntityManager.getInstance().getEditorManager().loadEditor(request.getCode());
			currentId = request.getId();
			EntityManager.getInstance().getMenubar().getRequestItem.setEnabled(false);
			EntityManager.getInstance().getMenubar().sendAnswerItem.setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(null, Resources.getValue("err.norequestaviable"));
		}
	}
	
	public void answerRequest() {
		String program = EntityManager.getInstance().getEditorManager().getProgram();
		StringWriter writer = new StringWriter();
		EntityManager.getInstance().getXmlSerializationController().saveWithStAX(writer);
		tutorServer.answerRequest(currentId, writer.toString(), program);
		EntityManager.getInstance().getMenubar().getRequestItem.setEnabled(true);
		EntityManager.getInstance().getMenubar().sendAnswerItem.setEnabled(false);
	}
}
