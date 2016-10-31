package chat_RMI;

import java.rmi.*;

/**
 *
 * @author David
 */
public interface ChatServerInterface extends Remote {
    
    void register(ChatClientImpl client) throws RemoteException;
    public int sendMessage(String message) throws RemoteException;
    public String getMessage(int idClient) throws RemoteException;
    public String getMessage2(String txt) throws RemoteException;
    public void addCallback(ChatCallbackInterface CallbackObject) throws RemoteException; 	
}
