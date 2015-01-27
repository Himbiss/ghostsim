package ghostsimulator.controller.tutor;

import ghostsimulator.model.Territory;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TutorClientI extends Remote {
	public int sendRequest(Territory territory,String code) throws RemoteException;
	public boolean hasAnswer(int id) throws RemoteException;
	public Answer getAnswer(int id) throws RemoteException;
}
