package ghostsimulator.controller.tutor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TutorImpl extends UnicastRemoteObject implements TutorClientI, TutorServerI {

	private static final long serialVersionUID = -7196749842951865828L;
	private Queue<Request> requestQueue;
	private Map<Integer,Answer> answerMap;
	private int lastId;
	
	public TutorImpl() throws RemoteException {
		requestQueue = new LinkedList<Request>();
		answerMap = new HashMap<Integer,Answer>();
		lastId = 0;
	}
	
	@Override
	public int sendRequest(String territory, String code) throws RemoteException {
		Request request = new Request((++lastId), territory, code);
		if(requestQueue.add(request))
			return lastId;
		else
			throw new RemoteException("Error inserting request into queue!");
	}

	@Override
	public Answer getAnswer(int id) throws RemoteException {
		return answerMap.get(id);
		
	}

	@Override
	public Request getLastRequest() {
		return requestQueue.poll();
	}

	@Override
	public void answerRequest(int id, String territory, String code) {
		Answer answer = new Answer(id,territory,code);
		answerMap.put(id, answer);
	}

	@Override
	public boolean hasRequest() {
		return !requestQueue.isEmpty();
	}

	@Override
	public boolean hasAnswer(int id) {
		return answerMap.containsKey(id);
	}

}
